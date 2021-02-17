package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import top.lizec.smartreview.entity.Tag;
import top.lizec.smartreview.mapper.KnowledgeDao;
import top.lizec.smartreview.mapper.KnowledgeTagDao;
import top.lizec.smartreview.mapper.TagDao;


/**
 * 处理知识点上标签相关操作的服务类
 */
@Service
public class KnowledgeTagService {

    @Resource
    KnowledgeDao knowledgeDao;

    @Resource
    TagDao tagDao;

    @Resource
    KnowledgeTagDao knowledgeTagDao;

    /**
     * 在创建知识点时, 将字符串形式的tag拆分插入到KnowledgeTag表中
     *
     * @param tagNames    字符串形式的tag
     * @param creator     tag的创建者
     * @param knowledgeId tag对应的知识点ID
     */
    @Transactional
    public void create(String tagNames, Integer creator, Integer knowledgeId) {
        List<String> tags = Arrays.stream(tagNames.split(","))
                .map(String::strip).collect(Collectors.toList());
        List<Integer> tagIds = tagDao.selectIdByTagName(tags, creator);
        knowledgeTagDao.insertBatch(tagIds, knowledgeId);
    }

    /**
     * 从所有的Knowledge中删除指定的标签
     *
     * @param tag 需要删除的标签
     */
    @Transactional
    public void remove(Tag tag) {
        List<Integer> knowledgeIds = knowledgeTagDao.selectKnowledgeIdByTag(tag.getId());

        for (Integer knowledgeId : knowledgeIds) {
            String tagStr = knowledgeDao.selectTag(knowledgeId);
            knowledgeDao.updateTag(knowledgeId, tagStr.replace(tag.getName(), ""));
        }
    }
}
