package top.lizec.smartreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.lizec.smartreview.entity.Sentence;

public interface SentenceDao extends BaseMapper<Sentence> {

    int insertSentenceRelation(Integer kid, Integer sentenceId);

}
