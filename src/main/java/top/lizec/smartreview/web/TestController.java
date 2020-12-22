package top.lizec.smartreview.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("hello")
    public String hello(String email, String password) {
        return "Hello" + email;
    }
}
