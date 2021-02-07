package top.lizec.smartreview.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;
import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.entity.UserDetail;
import top.lizec.smartreview.response.Result;
import top.lizec.smartreview.service.KnowledgeService;

@Api
@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeController {

    @Resource
    KnowledgeService knowledgeService;


    @PostMapping("/create")
    public Result<Knowledge> create(@ApiIgnore Authentication au, KnowledgeDto knowledge) {
        Integer id = ((UserDetail) au.getPrincipal()).getUserId();
        return Result.success(knowledgeService.createKnowledge(id, knowledge));
    }

    @GetMapping("/selectAll")
    public Result<List<Knowledge>> selectAll(@ApiIgnore Authentication au) {
        UserDetail userDetail = (UserDetail) au.getDetails();
        Integer id = userDetail.getUserId();
        return Result.success(knowledgeService.selectAll(id));
    }

}
