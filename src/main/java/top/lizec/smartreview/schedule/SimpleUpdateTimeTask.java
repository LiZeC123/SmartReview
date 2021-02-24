package top.lizec.smartreview.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import top.lizec.smartreview.entity.KnowledgeReviewState;
import top.lizec.smartreview.entity.LevelDetail;
import top.lizec.smartreview.mapper.ReviewStateDao;
import top.lizec.smartreview.mapper.SimpleReviewDao;

@Component
public class SimpleUpdateTimeTask extends AbstractUpdateTask {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private double[][] rate;

    @Resource
    ReviewStateDao reviewStateDao;

    @Resource
    SimpleReviewDao simpleReviewDao;

    public SimpleUpdateTimeTask() {
        super(10000, 100);
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void updateParameter() {
        // 当前没有数据, 则不更新
        List<LevelDetail> currentLevelDetail = reviewStateDao.queryYesterdayLevelDetail();
        if (currentLevelDetail.size() == 0) {
            return;
        }

        initReviewIntervalRate();
        List<LevelDetail> totalLevelDetail = reviewStateDao.queryTotalLevelDetail();

        double[] total = calculateRate(totalLevelDetail);
        double[] current = calculateRate(currentLevelDetail);
        double[] diffs = calculateDiff(current);

        for (int i = 0; i < diffs.length; i++) {
            step(i, diffs[i], total);
        }

        simpleReviewDao.updateParameter(toParam());
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void updateKnowledgeReviewTime() {
        logger.info("开始更新任务");
        initReviewIntervalRate();

        // count值表示右边界, 因此是实际最大值+1
        int maxCount = reviewStateDao.getKnowledgeCount() + 1;
        batchUpdate(maxCount);

        logger.info("更新任务完成");
    }


    private void initReviewIntervalRate() {
        String paramStr = simpleReviewDao.loadSimpleReviewRate();

        List<Double> rate = Arrays.stream(paramStr.split("\\|"))
                .map(Double::parseDouble).collect(Collectors.toList());

        this.rate = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.rate[i][j] = rate.get(i * 4 + j);
            }
        }
    }

    private String toParam() {
        return Arrays.stream(this.rate).flatMapToDouble(Arrays::stream)
                .mapToObj(r -> String.format("%.2f", r))
                .collect(Collectors.joining("|"));
    }

    private void step(int level, double diff, double[] totalRate) {
        for (int i = 0; i < totalRate.length; i++) {
            if (i < level) {
                this.rate[i][level] -= diff * totalRate[i];
                this.rate[level][i] -= diff * totalRate[i];
            } else if (i > level) {
                this.rate[i][level] += diff * totalRate[i];
                this.rate[level][i] += diff * totalRate[i];
            }
        }
    }

    private double[] calculateDiff(double[] current) {
        double[] expected = new double[]{0.2, 0.1, 0.4, 0.3};
        double[] diff = new double[expected.length];

        for (int i = 0; i < expected.length; i++) {
            diff[i] = expected[i] - current[i];
        }

        return diff;
    }

    private double[] calculateRate(List<LevelDetail> levelDetail) {
        double[] rate = new double[4];
        double count = 0;
        for (LevelDetail detail : levelDetail) {
            rate[detail.getLevel()] = detail.getCount();
            count += detail.getCount();
        }

        for (int i = 0; i < rate.length; i++) {
            rate[i] = rate[i] / count;
        }

        return rate;
    }

    /**
     * 在给定的切片区间上加载数据
     *
     * @param location 切片区间
     * @return 此切片上对应的数据
     */
    @Override
    public List<KnowledgeReviewState> loaderBetween(SliceLocation location) {
        return reviewStateDao.selectNeedUpdateBetween(location.getBegin(), location.getEnd());
    }

    /**
     * 更新状态
     *
     * @param state 更新前的状态
     * @return 根据策略更新后的状态
     */
    @Override
    public KnowledgeReviewState update(KnowledgeReviewState state) {
        int interval = (int) (state.getCurrentInterval() * rate[state.getLastLevel()][state.getCurrentLevel()]);
        state.setCurrentInterval(interval);
        state.setNextReviewTime(state.getFinishedTime().plus(Duration.ofHours(interval)));
        return state;
    }

    /**
     * 保存数据
     *
     * @param states 需要保存的数据
     */
    @Override
    public void write(List<KnowledgeReviewState> states) {
        reviewStateDao.updateReviewTimeInfo(states);
    }
}
