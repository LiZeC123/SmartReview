package top.lizec.smartreview.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.lizec.smartreview.aspect.ServiceLog;
import top.lizec.smartreview.entity.LevelDetail;
import top.lizec.smartreview.mapper.ReviewDetailDao;
import top.lizec.smartreview.mapper.ReviewStateDao;
import top.lizec.smartreview.mapper.SimpleReviewDao;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SimpleUpdateTimeTask {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private double[][] rate;

    @Resource
    ReviewStateDao reviewStateDao;

    @Resource
    ReviewDetailDao reviewDetailDao;

    @Resource
    SimpleReviewDao simpleReviewDao;

    public SimpleUpdateTimeTask() {

    }

    @ServiceLog("更新复习参数间任务")
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateParameter() {

        // 当前没有数据, 则不更新
        List<LevelDetail> currentLevelDetail = reviewDetailDao.queryYesterdayLevelDetail();
        if (currentLevelDetail.size() == 0) {
            logger.warn("没有足够的训练数据");
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
                .mapToObj(this::toNonNegativeString)
                .collect(Collectors.joining("|"));
    }

    private String toNonNegativeString(double v) {
        if (v >= 0.01) {
            return String.format("%.3f", v);
        } else {
            return "0.01";
        }
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
}
