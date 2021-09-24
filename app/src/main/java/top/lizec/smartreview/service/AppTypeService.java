package top.lizec.smartreview.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import top.lizec.smartreview.entity.AppType;

@Service
public class AppTypeService {

    private static final Map<Integer, AppType> appTypeMap;

    static {
        appTypeMap = new HashMap<>();
        appTypeMap.put(1, new AppType(1, "基本记录", "BaseKnowledge"));
        appTypeMap.put(2, new AppType(2, "英语单词本", "EnglishWordBook"));
        appTypeMap.put(3, new AppType(3, "力扣题解", "LeetCodeNote"));
    }

    public Map<Integer,AppType> getAppTypes() {
        return appTypeMap;
    }

}
