package top.lizec.smartreview.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import top.lizec.smartreview.entity.User;
import top.lizec.smartreview.entity.UserDetail;
import top.lizec.smartreview.response.Result;

@Api
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("hello")
    public String hello() {
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) au.getDetails();
        return "Hello, " + userDetail.getUserShowName();
    }

    @GetMapping("/user")
    public User user() {
        return new User();
    }

    @GetMapping("/ruser")
    public Result<User> ruser() {
        return Result.success(new User());
    }


}
