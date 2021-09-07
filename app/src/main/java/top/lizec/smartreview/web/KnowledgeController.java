package top.lizec.smartreview.web;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.dto.KnowledgeVO;
import top.lizec.smartreview.response.Result;
import top.lizec.smartreview.service.KnowledgeService;
import top.lizec.smartreview.utils.UserInfoUtils;

import javax.annotation.Resource;
import java.util.List;

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
    public Result<List<KnowledgeVO>> queryRecentReview() {
        Integer userId = userInfoUtils.getCurrentUserId();
        List<KnowledgeVO> dto = knowledgeService.queryRecentReview(userId);
        return Result.success(dto);
    }


    @GetMapping("/queryDetail")
    public Result<KnowledgeVO> queryDetail(Integer kid) {
        Integer userId = userInfoUtils.getCurrentUserId();
        return Result.success(knowledgeService.queryDetail(userId, kid));
    }


    @GetMapping("/deleteKnowledge")
    public Result<?> deleteKnowledge(Integer kid) {
        Integer userId = userInfoUtils.getCurrentUserId();
        knowledgeService.deleteKnowledge(userId, kid);
        return Result.success();
    }
}
