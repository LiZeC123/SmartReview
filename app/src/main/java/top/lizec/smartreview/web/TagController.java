package top.lizec.smartreview.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lizec.smartreview.entity.Tag;
import top.lizec.smartreview.response.Result;
import top.lizec.smartreview.service.TagService;
import top.lizec.smartreview.utils.UserInfoUtils;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Resource
    TagService tagService;

    @Resource
    UserInfoUtils userInfoUtils;

    @GetMapping("/create")
    public Result<?> create(String tagName) {
        Integer userId = userInfoUtils.getCurrentUserId();
        tagService.create(tagName, userId);
        return Result.success();
    }

    @GetMapping("/delete")
    public Result<?> delete(Integer tagId) {
        Integer userId = userInfoUtils.getCurrentUserId();
        tagService.remove(tagId, userId);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result<List<Tag>> selectAll() {
        Integer userId = userInfoUtils.getCurrentUserId();
        return Result.success(tagService.select(userId));
    }

    @GetMapping("selectAppType")
    public Result<List<Tag>> selectAppType() {
        return Result.success(tagService.selectAppType());
    }
}
