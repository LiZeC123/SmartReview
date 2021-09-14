package top.lizec.smartreview.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.entity.Sentence;
import top.lizec.smartreview.mapper.SentenceDao;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SentenceService extends ServiceImpl<SentenceDao, Sentence> {

    @Resource
    private SentenceDao sentenceDao;

    @Transactional
    public void insertNewSentence(Knowledge k, Sentence sentence) {
        sentenceDao.insert(sentence);
        sentenceDao.insertSentenceRelation(k.getId(), sentence.getId());
    }

    public List<Sentence> selectSentenceById(Integer kid) {
        return sentenceDao.selectSentenceByKnowledge(kid);
    }

}
