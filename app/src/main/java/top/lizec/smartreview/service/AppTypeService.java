package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import top.lizec.smartreview.entity.AppType;
import top.lizec.smartreview.mapper.AppTypeDao;

@Service
public class AppTypeService {

    @Resource
    private AppTypeDao appTypeDao;

    public Map<Integer,AppType> getAppTypes() {
        return appTypeDao.selectList(null).stream().collect(Collectors.toMap(AppType::getId, e->e));
    }

}
