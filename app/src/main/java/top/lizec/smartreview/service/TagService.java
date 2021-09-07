package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import top.lizec.smartreview.entity.KnowledgeTagKey;
import top.lizec.smartreview.entity.Tag;
import top.lizec.smartreview.mapper.TagDao;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class TagService {

    @Resource
    TagDao tagDao;

    @Resource
    CheckService checkService;

    @Transactional
    public void create(String name, Integer creator) {
        Tag tag = new Tag(name, creator);
        tagDao.insert(tag);
    }

    @Transactional
    public void remove(Integer tagId, Integer creator) {
        checkService.checkTagPermission(creator, tagId);
        Tag tag = new Tag(tagId);
        tagDao.deleteById(tag);
        tagDao.deleteKnowledgeTag(tagId);
    }

    public List<Tag> selectUserTag(Integer creator) {
        return tagDao.selectByMap(Map.of("creator", creator));
    }

    public List<Tag> selectKnowledgeTag(Integer kid) {
        return tagDao.selectKnowledgeTag(kid);
    }


    @Transactional
    public void createKnowledgeTag(List<Integer> tags, Integer uid, Integer kid) {
        if(tags.isEmpty()) {
            return;
        }

        checkService.checkTagPermission(uid, tags);
        final List<KnowledgeTagKey> keys = tags.stream().map(tag -> new KnowledgeTagKey(kid, tag)).collect(toList());
        tagDao.insertKnowledgeTag(keys);
    }

    public List<Tag> selectAppType() {
        return tagDao.selectAppType();
    }


}
