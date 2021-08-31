package top.lizec.smartreview.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import top.lizec.smartreview.algo.SentenceClient;
import top.lizec.smartreview.algo.entity.EnglishWord;


@Api
@RestController
@RequestMapping("/api/sentence")
public class SentenceController {

    @Resource
    SentenceClient sentenceClient;

    @PostMapping("/toWord")
    public List<EnglishWord> sentenceToWord(String sentence) {
        return sentenceClient.sentenceToWord(sentence);
    }

}
