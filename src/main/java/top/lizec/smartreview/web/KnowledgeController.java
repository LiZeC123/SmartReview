package top.lizec.smartreview.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
    public Result<KnowledgeDto> create(@RequestBody KnowledgeDto knowledge) {
        Integer id = userInfoUtils.getCurrentUserId();
        Knowledge k = knowledgeService.createKnowledge(id, knowledge);
        return Result.success(new KnowledgeDto(k));
    }

    @GetMapping("/selectAll")
    public Result<List<KnowledgeDto>> selectAll() {
        Integer id = userInfoUtils.getCurrentUserId();
        List<KnowledgeDto> dto = knowledgeService.selectAll(id).stream()
                .map(KnowledgeDto::new).collect(Collectors.toList());
        return Result.success(dto);
    }

}
