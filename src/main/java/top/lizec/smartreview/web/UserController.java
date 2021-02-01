package top.lizec.smartreview.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import top.lizec.smartreview.entity.User;
import top.lizec.smartreview.response.Result;
import top.lizec.smartreview.service.UserService;

@Api
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    UserService userService;


    @PostMapping("login")
    public Result<String> login(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        return userService.login(email, password)
                .map(Result::success)
                .orElseGet(Result::failure);
    }


    @PostMapping("create")
    public Result<?> create(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        return userService.createUser(user)
                .map(Result::success)
                .orElseGet(Result::failure);
    }
}
