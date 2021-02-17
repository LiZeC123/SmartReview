package top.lizec.smartreview.mapper;

import java.util.List;

public interface KnowledgeTagDao {

    Long insertBatch(List<Integer> tagIds, Integer knowledgeId);

    List<Integer> selectKnowledgeIdByTag(Integer tagId);

}
