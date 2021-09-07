package top.lizec.smartreview.service.interval;

import org.springframework.stereotype.Service;

import top.lizec.smartreview.entity.ReviewDetail;
import top.lizec.smartreview.service.UpdateReviewTimeServer;

@Service
public class SimpleUpdateReviewTimeServer implements UpdateReviewTimeServer {
    public static final double[][] rate = new double[][]{
            {0.5, 1.0, 2.4, 4.5},
            {0.4, 0.7, 2.2, 4.0},
            {0.3, 0.6, 2.0, 3.5},
            {0.2, 0.5, 1.5, 3.0}
    };


    @Override
    public int nextReviewTime(ReviewDetail detail) {
        int totalTime = detail.getIntervalTime() + detail.getElapsedTime();
        double thisRate = rate[detail.getCurrentLevel()][detail.getLastLevel()];
        return (int) (totalTime * thisRate);
    }
}
