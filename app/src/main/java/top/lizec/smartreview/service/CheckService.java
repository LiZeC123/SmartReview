package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;
import top.lizec.smartreview.exception.NoPermissionException;
import top.lizec.smartreview.mapper.KnowledgeDao;
import top.lizec.smartreview.mapper.TagDao;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CheckService {
    //TODO: 所有检查类接口都需要短时间缓存

    @Resource
    TagDao tagDao;
    @Resource
    private KnowledgeDao knowledgeDao;

    public void checkKnowledgePermission(Integer userId, Integer kid) {

        if (!knowledgeDao.checkUserPermission(userId, kid)) {
            throw new NoPermissionException("用户没有权限执行此操作");
        }
    }

    public void checkTagPermission(Integer userId, Integer tagId) {
        if (!tagDao.checkUserPermission(userId, tagId)) {
            throw new NoPermissionException("用户没有权限执行此操作");
        }
    }

    public void checkTagPermission(Integer userId, List<Integer> tagIds) {
        if (tagIds.isEmpty()) {
            return;
        }

        int count = tagDao.checkUserPermissionBatch(userId, tagIds);
        if (tagIds.size() != count) {
            throw new NoPermissionException("用户没有权限执行此操作");
        }
    }
}
