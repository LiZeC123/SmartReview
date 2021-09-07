package top.lizec.smartreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.lizec.smartreview.entity.ReviewRecord;
import top.lizec.smartreview.entity.ReviewState;

import java.util.List;

public interface ReviewStateDao extends BaseMapper<ReviewState> {

    void updateReviewState(ReviewState state);

    List<ReviewRecord> selectAllRecord(Integer uid);

}
