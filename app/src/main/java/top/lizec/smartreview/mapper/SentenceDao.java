package top.lizec.smartreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.lizec.smartreview.entity.Sentence;

import java.util.List;

public interface SentenceDao extends BaseMapper<Sentence> {

    int insertSentenceRelation(Integer kid, Integer sentenceId);

    List<Sentence> selectSentenceByKnowledge(Integer kid);
}
