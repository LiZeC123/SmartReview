package top.lizec.smartreview.mapper;

import top.lizec.smartreview.entity.Knowledge;

public interface KnowledgeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Knowledge record);

    int insertSelective(Knowledge record);

    Knowledge selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Knowledge record);

    int updateByPrimaryKey(Knowledge record);
}