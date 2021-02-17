package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import top.lizec.smartreview.entity.Tag;
import top.lizec.smartreview.mapper.TagDao;

@Service
public class TagService {

    @Resource
    TagDao tagDao;

    @Resource
    KnowledgeTagService knowledgeTagService;

    public void create(String name, Integer creator) {
        Tag tag = buildTagObject(name, creator);
        tagDao.insert(tag);
    }

    @Transactional
    public void remove(String name, Integer creator) {
        Tag tag = buildTagObject(name, creator);
        tagDao.delete(tag);
        knowledgeTagService.remove(tag);
    }

    private Tag buildTagObject(String name, Integer creator) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setCreator(creator);
        return tag;
    }
}