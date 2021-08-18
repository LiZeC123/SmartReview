package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lizec.smartreview.entity.Tag;
import top.lizec.smartreview.mapper.KnowledgeDao;
import top.lizec.smartreview.mapper.KnowledgeTagDao;
import top.lizec.smartreview.mapper.TagDao;

import javax.annotation.Resource;

import java.util.List;


/**
 * 处理知识点上标签相关操作的服务类
 */
@Service
public class KnowledgeTagService {
    //TODO: 在服务层就不要有这种中间服务了, 划分到Tag服务中

    @Resource
    KnowledgeDao knowledgeDao;

    @Resource
    TagDao tagDao;

    @Resource
    KnowledgeTagDao knowledgeTagDao;

    @Resource
    TagService tagService;

    /**
     * 在创建知识点时, 将对应的tag插入到KnowledgeTag表中
     * <p>
     * 如果用户不具有此操作的权限, 则抛出NoPermissionException
     *
     * @param tags        一组tag的ID
     * @param creator     tag的创建者
     * @param knowledgeId tag对应的知识点ID
     */
    @Transactional
    public void create(List<Integer> tags, Integer creator, Integer knowledgeId) {

//        List<String> tags = Arrays.stream(tagNames.split(";"))
//                .map(String::strip).collect(Collectors.toList());
//        List<Integer> tagIds = tagDao.selectIdByTagName(tags, creator);

        tagService.checkUserPermission(creator, tags);
        knowledgeTagDao.insertBatch(tags, knowledgeId);
    }

    /**
     * 从所有的Knowledge中删除指定的标签
     *
     * 此操作不检查用户是否具有删除此标签的权限
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
