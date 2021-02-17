package top.lizec.smartreview.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import top.lizec.smartreview.response.Result;
import top.lizec.smartreview.service.TagService;
import top.lizec.smartreview.utils.UserInfoUtils;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Resource
    TagService tagService;

    @Resource
    UserInfoUtils userInfoUtils;

    @PostMapping("/create")
    public Result<?> create(String tagName) {
        Integer userId = userInfoUtils.getCurrentUserId();
        tagService.create(tagName, userId);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result<?> delete(String tagName) {
        Integer userId = userInfoUtils.getCurrentUserId();
        tagService.remove(tagName, userId);
        return Result.success();
    }
}
