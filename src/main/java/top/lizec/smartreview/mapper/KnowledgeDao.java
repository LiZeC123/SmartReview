package top.lizec.smartreview.mapper;

import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.entity.KnowledgeRecord;

import java.util.List;

public interface KnowledgeDao {
    String selectTag(Integer id);

    Knowledge updateTag(Integer id, String tag);

    Long insert(Knowledge knowledge);

    List<Knowledge> selectAll(Integer userId);

    List<Knowledge> queryRecentReview(Integer userId);

    List<KnowledgeRecord> queryAllRecord(Integer userId);
}