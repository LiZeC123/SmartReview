package top.lizec.smartreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.lizec.smartreview.entity.Knowledge;

import java.util.Date;
import java.util.List;

public interface KnowledgeDao extends BaseMapper<Knowledge> {

    List<Knowledge> queryRecentReview(Integer userId, Date deadline);

    List<Knowledge> queryList(Integer userId);

    Boolean checkUserPermission(Integer userId, Integer kid);
}