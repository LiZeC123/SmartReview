package top.lizec.smartreview.config;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lizec.smartreview.algo.SentenceClient;

@Configuration
public class FeignConfig {

    @Bean
    public SentenceClient sentenceClient() {
        return Feign.builder()
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .target(SentenceClient.class, "http://algo:5000");
    }
}
