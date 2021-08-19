package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import top.lizec.smartreview.dto.TagDto;
import top.lizec.smartreview.entity.KnowledgeTag;
import top.lizec.smartreview.entity.Tag;
import top.lizec.smartreview.exception.NoPermissionException;
import top.lizec.smartreview.mapper.TagDao;

import static java.util.stream.Collectors.*;

@Service
public class TagService {

    @Resource
    TagDao tagDao;

    @Resource
    KnowledgeTagService knowledgeTagService;

    @Transactional
    public void create(String name, Integer creator) {
        Tag tag = buildTagObject(name, creator);
        tagDao.insert(tag);
    }

    @Transactional
    public void remove(Integer tagId, Integer creator) {
        checkUserPermission(creator, tagId);
        Tag tag = new Tag();
        tag.setId(tagId);
        tagDao.delete(tag);
        knowledgeTagService.remove(tag);
    }

    public List<Tag> select(Integer creator) {
        return tagDao.selectAll(creator);
    }

    public List<KnowledgeTag> selectTagByKnowledge(Integer id) {
        return tagDao.selectTagByKnowledge(Collections.singletonList(id));
    }

    public Map<Integer, List<KnowledgeTag>> selectTagByKnowledge(List<Integer> kids) {
        if(kids.isEmpty()) {
            return Collections.emptyMap();
        }

        return tagDao.selectTagByKnowledge(kids).stream()
                .collect(groupingBy(KnowledgeTag::getKnowledgeId));
    }

    public void createKnowledgeTag(List<TagDto> tags, Integer uid, Integer kid) {
        checkUserPermission(uid, tags.stream().map(TagDto::getId).collect(toList()));

        List<KnowledgeTag> kts = tags.stream().map(tag -> new KnowledgeTag(tag,uid, kid)).collect(toList());
        tagDao.insertKnowledgeTag(kts);
    }

    private Tag buildTagObject(String name, Integer creator) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setCreator(creator);
        return tag;
    }

    public List<Tag> selectAppType() {
        return tagDao.selectAppType();
    }

    public void checkUserPermission(Integer userId, Integer tagId) {
        if (!tagDao.checkUserPermission(userId, tagId)) {
            throw new NoPermissionException("用户没有权限执行此操作");
        }
    }

    public void checkUserPermission(Integer userId, List<Integer> tagIds) {
        int count = tagDao.checkUserPermissionBatch(userId, tagIds);
        if (tagIds.size() != count) {
            throw new NoPermissionException("用户没有权限执行此操作");
        }
    }
}
