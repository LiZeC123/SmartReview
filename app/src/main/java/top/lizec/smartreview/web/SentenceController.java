package top.lizec.smartreview.web;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lizec.smartreview.algo.SentenceClient;
import top.lizec.smartreview.algo.entity.EnglishWord;
import top.lizec.smartreview.algo.entity.Sentence;
import top.lizec.smartreview.response.Result;

import javax.annotation.Resource;
import java.util.List;


@Api
@RestController
@RequestMapping("/api/sentence")
public class SentenceController {

    @Resource
    SentenceClient sentenceClient;

    @PostMapping("/toWord")
    public Result<List<EnglishWord>> sentenceToWord(String sentence) {
        return Result.success(sentenceClient.sentenceToWord(new Sentence(sentence)));
    }

}
