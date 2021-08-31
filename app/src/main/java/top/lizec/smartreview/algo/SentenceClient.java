package top.lizec.smartreview.algo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import top.lizec.smartreview.algo.entity.EnglishWord;

@FeignClient(name = "SentenceClient",url = "localhost:5000")
public interface SentenceClient {

    @PostMapping("/sentenceToWord")
    List<EnglishWord> sentenceToWord(String sentence);
}
