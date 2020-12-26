package top.lizec.smartreview.web;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/user/api")
public class UserController {

    @RequestMapping("hello")
    public String hello() {
        return "Hello, user";
    }
}
