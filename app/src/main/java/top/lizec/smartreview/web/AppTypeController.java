package top.lizec.smartreview.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import top.lizec.smartreview.entity.AppType;
import top.lizec.smartreview.response.Result;
import top.lizec.smartreview.service.AppTypeService;

@Api
@RestController
@RequestMapping("/api/appType")
public class AppTypeController {

    @Resource
    private AppTypeService appTypeService;

    @GetMapping("/getAllTypes")
    public Result<Map<Integer, AppType>> getAllTypes() {
        return Result.success(appTypeService.getAppTypes());
    }

}
