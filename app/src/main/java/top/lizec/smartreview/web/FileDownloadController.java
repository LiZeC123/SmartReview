package top.lizec.smartreview.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import top.lizec.smartreview.utils.FileUtils;

@Api
@Controller
@RequestMapping("/download")
public class FileDownloadController {

    @Resource
    FileUtils fileUtils;


    @GetMapping("file")
    public void downloadFileByToken(String token, HttpServletResponse response) {
        response.setContentType("application/octet-stream;charset=utf-8");

        Path path = fileUtils.getFilePath(token);
        try (ServletOutputStream outputStream = response.getOutputStream()) {

            // 为了避免乱码问题, 还是不要使用中文文件名了
            response.setHeader("Content-Disposition", "attachment;filename=" + path.getFileName());

            outputStream.write(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
