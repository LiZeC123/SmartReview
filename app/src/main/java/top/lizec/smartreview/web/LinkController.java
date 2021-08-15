package top.lizec.smartreview.web;

import io.swagger.annotations.Api;
import org.jsoup.Jsoup;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lizec.smartreview.response.Result;

import java.io.IOException;

@Api
@RestController
@RequestMapping("/api/link")
public class LinkController {


    @GetMapping("getLinkTitle")
    public Result<String> getLinkTitle(String link) {
        if (!StringUtils.hasText(link)) {
            return Result.failure("链接为空");
        }

        if (!link.startsWith("http")) {
            return Result.failure("输入内容不是链接");
        }

        try {
            return Result.success(Jsoup.connect(link).get().title());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure("页面解析失败");
        }
    }

}
