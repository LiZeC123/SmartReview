package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import top.lizec.smartreview.mapper.KnowledgeTagDao;
import top.lizec.smartreview.mapper.TagDao;


/**
 * 处理知识点上标签相关操作的服务类
 */
@Service
public class KnowledgeTagService {

    @Resource
    TagDao tagDao;

    @Resource
    KnowledgeTagDao knowledgeTagDao;

    @Transactional
    public void create(String tag, Integer creator, Integer knowledgeId) {
        List<String> tags = Arrays.stream(tag.split(","))
                .map(String::strip).collect(Collectors.toList());
        List<Integer> tagIds = tagDao.selectIdByTagName(tags, creator);
        knowledgeTagDao.insertBatch(tagIds, knowledgeId);
    }

    public void update() {

    }

    public void remove() {

    }


}
