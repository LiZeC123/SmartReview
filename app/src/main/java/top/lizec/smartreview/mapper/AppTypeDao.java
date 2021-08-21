package top.lizec.smartreview.mapper;

import java.util.List;

import top.lizec.smartreview.entity.AppType;

public interface AppTypeDao {
    AppType selectOne(Integer id);

    List<AppType> selectAll();
}
