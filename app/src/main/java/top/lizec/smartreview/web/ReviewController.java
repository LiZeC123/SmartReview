package top.lizec.smartreview.web;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import top.lizec.smartreview.dto.ReviewResult;
import top.lizec.smartreview.entity.ReviewRecord;
import top.lizec.smartreview.response.Result;
import top.lizec.smartreview.service.ReviewService;
import top.lizec.smartreview.utils.UserInfoUtils;

import javax.annotation.Resource;
import java.util.List;

@Api
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Resource
    UserInfoUtils userInfoUtils;

    @Resource
    ReviewService reviewService;

    @GetMapping("/queryAllRecord")
    public Result<List<ReviewRecord>> queryAllRecord() {
        Integer userId = userInfoUtils.getCurrentUserId();
        List<ReviewRecord> records = reviewService.queryAllRecord(userId);
        return Result.success(records);
    }

    @PostMapping("/finishReview")
    public Result<?> finishReview(@RequestBody ReviewResult result) {
        Integer userId = userInfoUtils.getCurrentUserId();
        reviewService.updateKnowledgeReview(userId, result.getKid(), result.getMemoryLevel());
        return Result.success();
    }
}
