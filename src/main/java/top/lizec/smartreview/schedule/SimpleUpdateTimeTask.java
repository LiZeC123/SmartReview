package top.lizec.smartreview.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import top.lizec.smartreview.entity.KnowledgeReviewState;
import top.lizec.smartreview.entity.Rate;
import top.lizec.smartreview.mapper.ReviewStateDao;

@Component
public class SimpleUpdateTimeTask {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    ReviewStateDao reviewStateDao;
    private double[][] rate;

    @Scheduled(cron = "0 0 2 * * ?")
    public void updateKnowledgeReviewTime() {
        logger.info("开始更新任务");

        initReviewIntervalRate();
        final int READ_EPOCH = 10000;
        final int WRITE_EPOCH = 100;

        int maxCount = reviewStateDao.getKnowledgeCount();

        maxCount = maxCount + (READ_EPOCH - maxCount % READ_EPOCH);
        for (int i = 0; i < maxCount; i += READ_EPOCH) {
            List<KnowledgeReviewState> states = reviewStateDao
                    .selectNeedUpdateBetween(i, i + READ_EPOCH - 1);

            List<KnowledgeReviewState> updatedStates = states.stream()
                    .map(this::updateState).collect(Collectors.toList());

            int size = updatedStates.size();
            size = size + (WRITE_EPOCH - size % WRITE_EPOCH);
            for (int j = 0; j < size; j += WRITE_EPOCH) {
                List<KnowledgeReviewState> sub = updatedStates.subList(j, Math.min(updatedStates.size(), j + WRITE_EPOCH - 1));
                reviewStateDao.updateReviewTimeInfo(sub);
            }
        }


        logger.info("更新任务完成");
    }


    private KnowledgeReviewState updateState(KnowledgeReviewState state) {
        int interval = (int) (state.getCurrentInterval() * rate[state.getLastLevel()][state.getCurrentLevel()]);
        state.setCurrentInterval(interval);
        state.setNextReviewTime(state.getFinishedTime().plus(Duration.ofHours(interval)));
        return state;
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

}
