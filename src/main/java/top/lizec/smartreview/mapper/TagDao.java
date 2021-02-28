package top.lizec.smartreview.mapper;

import top.lizec.smartreview.entity.Tag;

import java.util.List;

public interface TagDao {

    Tag selectOne(Tag tag);

    List<Tag> selectAll(Integer creator);

    Long insert(Tag tag);

    void update(Tag tag);

    void delete(Tag tag);

    List<Integer> selectIdByTagName(List<String> nameList, Integer creator);

    List<Integer> getKnowledgeIdByTag(Integer tagId);

    String getTagName(Integer tagId);
}
