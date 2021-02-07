package top.lizec.smartreview.mapper;

import java.util.List;

import top.lizec.smartreview.entity.Knowledge;

public interface KnowledgeDao {
    Knowledge insert(Knowledge knowledge);

    List<Knowledge> selectAll(Integer userId);
}