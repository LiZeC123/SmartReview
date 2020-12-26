package top.lizec.smartreview.web;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lizec.smartreview.entity.User;
import top.lizec.smartreview.service.UserServer;

import javax.annotation.Resource;

@Api
@RestController
@RequestMapping("/user/api")
public class UserController {

    @Resource
    UserServer userServer;

    @GetMapping("hello")
    public String hello() {
        return "Hello, user";
    }


    @PostMapping("create")
    public String create(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        userServer.createUser(user);

        return "Finish";
    }
}
