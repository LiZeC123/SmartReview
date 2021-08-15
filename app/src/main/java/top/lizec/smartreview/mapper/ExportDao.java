package top.lizec.smartreview.mapper;

import top.lizec.smartreview.dto.AppCount;

import java.util.List;

public interface ExportDao {
    List<AppCount> queryAppCountInfo(Integer userId);

}
