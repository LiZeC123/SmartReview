package top.lizec.smartreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.lizec.smartreview.entity.KnowledgeTag;
import top.lizec.smartreview.entity.KnowledgeTagKey;
import top.lizec.smartreview.entity.Tag;

import java.util.List;

public interface TagDao extends BaseMapper<Tag> {




    Long insertKnowledgeTag(List<KnowledgeTagKey> knowledgeTags);

    Long deleteKnowledgeTag(Integer tagId);

    List<Tag> selectKnowledgeTag(Integer kid);

    List<Integer> selectIdByTagName(List<String> nameList, Integer creator);

    List<Integer> getKnowledgeIdByTag(Integer tagId);

    String getTagName(Integer tagId);

    List<Tag> selectAppType();

    List<KnowledgeTag> selectTagByKnowledge(List<Integer> kids);

    boolean checkUserPermission(Integer userId, Integer tagId);

    int checkUserPermissionBatch(Integer userId, List<Integer> tagIds);
}
