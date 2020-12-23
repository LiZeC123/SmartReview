package top.lizec.smartreview;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.lizec")
public class SmartReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartReviewApplication.class, args);
    }

}
