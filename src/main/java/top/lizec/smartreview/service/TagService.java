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

    public void create(String name) {

    }

    @Transactional
    public void modify(String oldName, String newName, Integer userId) {
        Tag tag = new Tag();
        tag.setName(oldName);
        tag.setCreator(userId);
        tagDao.selectOne(tag);

        tag.setName(newName);
        tagDao.update(tag);
    }

    public void remove(String name) {

    }


    /*
     * create
     * modify
     * remove
     *
     *
     *
     * */


}
