package top.lizec.smartreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.lizec.smartreview.entity.Knowledge;

import java.time.LocalDateTime;
import java.util.List;

public interface KnowledgeDao extends BaseMapper<Knowledge> {

    List<Knowledge> queryRecentReview(Integer userId, LocalDateTime deadline);

    Boolean checkUserPermission(Integer userId, Integer kid);
}