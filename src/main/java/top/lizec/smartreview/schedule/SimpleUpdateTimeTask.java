package top.lizec.smartreview.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

import javax.annotation.Resource;

import top.lizec.smartreview.entity.KnowledgeReviewState;
import top.lizec.smartreview.entity.Rate;
import top.lizec.smartreview.mapper.ReviewStateDao;

@Component
public class SimpleUpdateTimeTask extends AbstractUpdateTask {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private double[][] rate;

    @Resource
    ReviewStateDao reviewStateDao;

    public SimpleUpdateTimeTask() {
        super(10000, 100);
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
        List<Rate> rates = reviewStateDao.loadSimpleReviewRate();
        this.rate = new double[4][4];
        // 数据库定义的顺序与记忆等级相反, 因此两个维度都反向装入数据
        for (int i = 0; i < 4; i++) {
            this.rate[i][0] = rates.get(3 - i).getD();
            this.rate[i][1] = rates.get(3 - i).getC();
            this.rate[i][2] = rates.get(3 - i).getB();
            this.rate[i][3] = rates.get(3 - i).getA();
        }
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
