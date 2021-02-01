package top.lizec.smartreview.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import top.lizec.smartreview.entity.User;
import top.lizec.smartreview.response.Result;

@Api
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("hello")
    public String hello(String email, String password) {
        return "Hello" + email;
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
