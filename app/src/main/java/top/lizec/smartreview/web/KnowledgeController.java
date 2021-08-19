package top.lizec.smartreview.web;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.entity.KnowledgeRecord;
import top.lizec.smartreview.response.Result;
import top.lizec.smartreview.service.KnowledgeService;
import top.lizec.smartreview.utils.UserInfoUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeController {

    @Resource
    KnowledgeService knowledgeService;

    @Resource
    UserInfoUtils userInfoUtils;


    @PostMapping("/create")
    public Result<?> create(@RequestBody KnowledgeDto knowledge) {
        Integer id = userInfoUtils.getCurrentUserId();
        knowledgeService.createKnowledge(id, knowledge);
        return Result.success();
    }

    @GetMapping("/queryRecentReview")
    public Result<List<KnowledgeDto>> queryRecentReview() {
        Integer userId = userInfoUtils.getCurrentUserId();
        List<KnowledgeDto> dto = knowledgeService.queryRecentReview(userId);
        return Result.success(dto);
    }

    @GetMapping("/selectAll")
    public Result<List<KnowledgeDto>> selectAll() {
        Integer userId = userInfoUtils.getCurrentUserId();
        List<KnowledgeDto> dto = knowledgeService.selectAll(userId);
        return Result.success(dto);
    }

    @GetMapping("/queryAllRecord")
    public Result<List<KnowledgeRecord>> queryAllRecord() {
        Integer userId = userInfoUtils.getCurrentUserId();
        List<KnowledgeRecord> records = knowledgeService.queryAllRecord(userId);
        return Result.success(records);
    }

    @GetMapping("/selectOne")
    public Result<KnowledgeDto> selectOne(Integer kid) {
        Integer userId = userInfoUtils.getCurrentUserId();
        return knowledgeService.selectOne(userId, kid).map(Result::success).orElseGet(Result::failure);
    }


    @GetMapping("/finishReview")
    public Result<?> finishReview(Integer kid, Integer memoryLevel) {
        Integer userId = userInfoUtils.getCurrentUserId();
        knowledgeService.updateKnowledgeReview(userId, kid, memoryLevel);
        return Result.success();
    }

    @GetMapping("/deleteKnowledge")
    public Result<?> deleteKnowledge(Integer kid) {
        Integer userId = userInfoUtils.getCurrentUserId();
        knowledgeService.deleteKnowledge(userId, kid);
        return Result.success();
    }
}
