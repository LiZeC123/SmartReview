package top.lizec.smartreview.algo;

import feign.Headers;
import feign.RequestLine;
import top.lizec.smartreview.algo.entity.EnglishWord;
import top.lizec.smartreview.algo.entity.Sentence;

import java.util.List;

public interface SentenceClient {

    @RequestLine("POST /sentenceToWord")
    @Headers("Content-Type: application/json")
    List<EnglishWord> sentenceToWord(Sentence sentence);

    @RequestLine("POST /queryWordDifficulty")
    @Headers("Content-Type: application/json")
    List<EnglishWord> queryWordDifficulty(List<String> words);
}
