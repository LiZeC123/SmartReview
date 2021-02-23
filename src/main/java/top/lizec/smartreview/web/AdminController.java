package top.lizec.smartreview.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import top.lizec.smartreview.response.Result;
import top.lizec.smartreview.schedule.SimpleUpdateTimeTask;

@Api
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    SimpleUpdateTimeTask simpleUpdateTimeTask;


    @GetMapping("updateReview")
    public Result<?> updateReview() {
        simpleUpdateTimeTask.updateKnowledgeReviewTime();
        return Result.success();
    }
}
