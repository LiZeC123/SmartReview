package top.lizec.smartreview.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import top.lizec.smartreview.service.TagService;
import top.lizec.smartreview.utils.UserInfoUtils;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Resource
    TagService tagService;

    @Resource
    UserInfoUtils userInfoUtils;


}
