package top.lizec.smartreview;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@SpringBootApplication
@MapperScan("top.lizec")
public class SmartReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartReviewApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Producer captcha() {
        Properties properties = new Properties();
        properties.setProperty("kaptcha.img.width","150");
        properties.setProperty("kaptcha.img.height","150");
        properties.setProperty("kaptcha.textproducer.char.string","ABCDEFGHJKLMNPQRSTUVWXYZabcefeghijkmnpqrstuvwxyz23456789");
        properties.setProperty("kaptcha.textproducer.char.length","5");

        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
