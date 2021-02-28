package top.lizec.smartreview.utils.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lizec.smartreview.mapper.SimpleReviewDao;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class CacheConfig {

    @Resource
    SimpleReviewDao simpleReviewDao;

    @Bean
    Cache<double[][]> rateCache() {
        return new TimeoutCache<>(this::loadSimpleReviewRate, 2 * 60 * 60);
    }

    private double[][] loadSimpleReviewRate() {
        String paramStr = simpleReviewDao.loadSimpleReviewRate();

        List<Double> rateList = Arrays.stream(paramStr.split("\\|"))
                .map(Double::parseDouble).collect(Collectors.toList());

        double[][] rate = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rate[i][j] = rateList.get(i * 4 + j);
            }
        }
        return rate;
    }

}
