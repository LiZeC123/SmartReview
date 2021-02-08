package top.lizec.smartreview.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.response.Result;
import top.lizec.smartreview.service.KnowledgeService;
import top.lizec.smartreview.utils.UserInfoUtils;

@Api
@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeController {

    @Resource
    KnowledgeService knowledgeService;

    @Resource
    UserInfoUtils userInfoUtils;


    @PostMapping("/create")
    public Result<Knowledge> create(@RequestBody KnowledgeDto knowledge) {
        Integer id = userInfoUtils.getCurrentUserId();
        return Result.success(knowledgeService.createKnowledge(id, knowledge));
    }

    @GetMapping("/selectAll")
    public Result<List<Knowledge>> selectAll() {
        Integer id = userInfoUtils.getCurrentUserId();
        return Result.success(knowledgeService.selectAll(id));
    }

}
