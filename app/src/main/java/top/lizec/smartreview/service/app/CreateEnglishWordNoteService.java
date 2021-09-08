package top.lizec.smartreview.service.app;

import org.springframework.stereotype.Service;
import top.lizec.smartreview.algo.SentenceClient;
import top.lizec.smartreview.algo.entity.EnglishWord;
import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.dto.LinkDto;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.service.LinkService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreateEnglishWordNoteService implements CreateKnowledgeService {
    @Resource
    private SentenceClient sentenceClient;
    @Resource
    private LinkService linkService;

    @Override
    public List<Knowledge> parseKnowledgeDto(KnowledgeDto dto) {
        final List<EnglishWord> englishWords = sentenceClient.queryWordDifficulty(dto.getWords());

        List<Knowledge> knowledges = new ArrayList<>(englishWords.size());
        for (EnglishWord words : englishWords) {
            Knowledge k = new Knowledge();
            k.setAppType(1);
            k.setTitle(words.getWord());
            k.setContent("");
            k.setDifficulty(words.getDifficulty());
            knowledges.add(k);
        }

        return knowledges;
    }

    @Override
    public void afterInsertKnowledge(Knowledge k, KnowledgeDto dto) {
        linkService.insertLink(k.getId(), "初阶韦氏词典", "https://www.learnersdictionary.com/definition/" + k.getTitle());
        linkService.insertLink(k.getId(), "高阶韦氏词典", "https://www.merriam-webster.com/dictionary/" + k.getTitle());
        linkService.insertLink(k.getId(), "必应搜索图片", "https://cn.bing.com/images/search?q=" + k.getTitle());
        linkService.insertLink(k.getId(), "必应搜索词典", "https://cn.bing.com/dict/search?q=" + k.getTitle());

        for (LinkDto linkDto : dto.getLink()) {
            linkService.insertLink(k.getId(), linkDto.getName(), linkDto.getUrl());
        }
    }
}
