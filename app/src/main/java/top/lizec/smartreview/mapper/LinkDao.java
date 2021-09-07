package top.lizec.smartreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.lizec.smartreview.dto.LinkDto;
import top.lizec.smartreview.entity.Link;

import java.util.List;

public interface LinkDao extends BaseMapper<Link> {
    List<Link> selectKnowledgeLink(Integer kid);
}