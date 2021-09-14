package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.entity.Sentence;
import top.lizec.smartreview.mapper.SentenceDao;

import javax.annotation.Resource;

@Service
public class SentenceService {

    @Resource
    SentenceDao sentenceDao;

    @Transactional
    public void insertNewSentence(Knowledge k, Sentence sentence) {
        sentenceDao.insert(sentence);
        sentenceDao.insertSentenceRelation(k.getId(), sentence.getId());
    }

}
