package top.lizec.smartreview.web;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lizec.smartreview.algo.SentenceAnalyzer;
import top.lizec.smartreview.algo.entity.EnglishWord;
import top.lizec.smartreview.response.Result;

import javax.annotation.Resource;
import java.util.List;


@Api
@RestController
@RequestMapping("/api/sentence")
public class SentenceController {
    @Resource
    SentenceAnalyzer sentenceAnalyzer;

    @PostMapping("/toWord")
    public Result<List<EnglishWord>> sentenceToWord(String sentence) {
        return Result.success(sentenceAnalyzer.sentenceToWord(sentence));
    }

}
