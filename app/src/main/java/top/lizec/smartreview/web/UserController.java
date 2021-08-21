package top.lizec.smartreview.web;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lizec.smartreview.entity.User;
import top.lizec.smartreview.response.Result;
import top.lizec.smartreview.service.UserService;
import top.lizec.smartreview.utils.UserInfoUtils;

import javax.annotation.Resource;

@Api
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    UserInfoUtils userInfoUtils;


    @PostMapping("login")
    public Result<String> login(@RequestBody User user) {
        return userService.login(user.getEmail(), user.getPassword())
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


    /**
     * 根据密码明文创建加密后的密文
     *
     * @param password 密码明文
     * @return 加密后的密文
     */
    @GetMapping("createPassword")
    public Result<String> createPassword(String password) {
        return Result.success(userService.createPassword(password));
    }


    @GetMapping("getCurrentUserName")
    public Result<String> getCurrentUserName() {
        final Integer userId = userInfoUtils.getCurrentUserId();
        return Result.success(userService.getUserName(userId));
    }
}
