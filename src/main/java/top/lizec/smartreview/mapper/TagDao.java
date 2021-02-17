package top.lizec.smartreview.mapper;

import java.util.List;

import top.lizec.smartreview.entity.Tag;

public interface TagDao {

    Tag selectOne(Tag tag);

    List<Tag> selectAll(Integer creator);

    Long insert(Tag tag);

    void update(Tag tag);

    void delete(Tag tag);

    List<Integer> selectIdByTagName(List<String> nameList, Integer creator);

}
