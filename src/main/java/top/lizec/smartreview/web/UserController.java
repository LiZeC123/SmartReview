package top.lizec.smartreview.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import top.lizec.smartreview.entity.User;
import top.lizec.smartreview.service.UserServer;

@Api
@RestController
@RequestMapping("/user/user")
public class UserController {

    @Resource
    UserServer userServer;


    @PostMapping("create")
    public String create(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        if (userServer.createUser(user).isPresent()) {
            return "用户创建成功";
        } else {
            return "用户创建失败";
        }
    }
}
