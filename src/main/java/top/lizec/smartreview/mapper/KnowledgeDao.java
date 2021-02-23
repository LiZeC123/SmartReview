package top.lizec.smartreview.mapper;

import java.util.List;

import top.lizec.smartreview.entity.Knowledge;

public interface KnowledgeDao {
    String selectTag(Integer id);

    Knowledge updateTag(Integer id, String tag);

    Long insert(Knowledge knowledge);

    List<Knowledge> selectAll(Integer userId);

    List<Knowledge> queryRecentReview(Integer userId);
}