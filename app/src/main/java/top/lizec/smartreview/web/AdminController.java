package top.lizec.smartreview.web;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lizec.smartreview.response.Result;
import top.lizec.smartreview.schedule.SimpleUpdateTimeTask;

import javax.annotation.Resource;

@Api
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    SimpleUpdateTimeTask simpleUpdateTimeTask;


    @GetMapping("updateParameter")
    public Result<?> updateParameter() {
        simpleUpdateTimeTask.updateParameter();
        return Result.success();
    }
}
