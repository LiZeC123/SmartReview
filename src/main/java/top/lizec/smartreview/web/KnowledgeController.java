package top.lizec.smartreview.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;
import top.lizec.smartreview.dto.KnowledgeDto;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.entity.UserDetail;
import top.lizec.smartreview.service.KnowledgeService;

@Api
@RestController
@RequestMapping("user/knowledge")
public class KnowledgeController {

    @Resource
    KnowledgeService knowledgeService;


    @PostMapping("/create")
    public Knowledge create(@ApiIgnore Authentication au, KnowledgeDto knowledge) {
        Integer id = ((UserDetail) au.getPrincipal()).getUserId();
        return knowledgeService.createKnowledge(id, knowledge);
    }

}
