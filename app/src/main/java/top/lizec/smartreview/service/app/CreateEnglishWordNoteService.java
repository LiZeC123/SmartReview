package top.lizec.smartreview.service.app;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import top.lizec.smartreview.algo.SentenceClient;
import top.lizec.smartreview.algo.entity.EnglishWord;
import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.entity.Link;
import top.lizec.smartreview.entity.Sentence;
import top.lizec.smartreview.service.LinkService;
import top.lizec.smartreview.service.SentenceService;

@Service
public class CreateEnglishWordNoteService implements CreateKnowledgeService {
    private static final int APP_TYPE = 1;
    @Resource
    private SentenceClient sentenceClient;
    @Resource
    private LinkService linkService;
    @Resource
    private SentenceService sentenceService;

    @Override
    public List<Knowledge> parseKnowledgeDto(KnowledgeDto dto) {
        final List<EnglishWord> englishWords = sentenceClient.queryWordDifficulty(dto.getWords());

        List<Knowledge> knowledges = new ArrayList<>(englishWords.size());
        for (EnglishWord words : englishWords) {
            Knowledge k = new Knowledge();
            k.setAppType(APP_TYPE);
            k.setTitle(words.getWord());
            k.setContent("");
            k.setDifficulty(words.getDifficulty());
            knowledges.add(k);
        }

        return knowledges;
    }

    @Override
    public void afterInsertKnowledge(Knowledge k, KnowledgeDto dto) {
        List<Link> links = new ArrayList<>(4);
        links.add(new Link(k.getId(), "初阶韦氏词典", "https://www.learnersdictionary.com/definition/" + k.getTitle()));
        links.add(new Link(k.getId(), "高阶韦氏词典", "https://www.merriam-webster.com/dictionary/" + k.getTitle()));
        links.add(new Link(k.getId(), "必应搜索图片", "https://cn.bing.com/images/search?q=" + k.getTitle()));
        links.add(new Link(k.getId(), "必应搜索词典", "https://cn.bing.com/dict/search?q=" + k.getTitle()));
        linkService.insertLinkBatch(links);

        sentenceService.insertNewSentence(k, new Sentence(dto.getContent()));
    }
}
