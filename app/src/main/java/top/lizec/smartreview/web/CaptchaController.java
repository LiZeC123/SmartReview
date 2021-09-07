package top.lizec.smartreview.web;

import com.google.code.kaptcha.Producer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;

@Api
@Controller
public class CaptchaController {
    @Resource
    private Producer producer;

    @GetMapping("/captcha.jpg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        String capText = producer.createText();
        request.getSession().setAttribute("captcha", capText);
        BufferedImage bi = producer.createImage(capText);

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            ImageIO.write(bi, "jpg", outputStream);
            outputStream.flush();
        }
    }

}
