package top.lizec.smartreview.web;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import top.lizec.smartreview.dto.AppCount;
import top.lizec.smartreview.response.Result;
import top.lizec.smartreview.service.ExportService;
import top.lizec.smartreview.utils.UserInfoUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Api
@RestController
@RequestMapping("/api/export")
public class ExportController {

    @Resource
    ExportService exportService;

    @Resource
    UserInfoUtils userInfoUtils;

    @GetMapping("queryInfo")
    public Result<List<AppCount>> queryInfo() {
        Integer id = userInfoUtils.getCurrentUserId();

        return Result.success(exportService.queryAppCountInfo(id));
    }

    @GetMapping("exportOne")
    public Result<String> exportOne(Integer tagId) {
        Integer userId = userInfoUtils.getCurrentUserId();

        try {
            Path path = exportService.generateFile(userId, tagId);
            String url = "/download/file?token=" + path.getFileName().toString();
            return Result.success(url);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure("文件导出失败");
        }
    }


    @PostMapping("exportAll")
    public Result<String> exportAll(@RequestBody List<Integer> tagIds) {
        Integer userId = userInfoUtils.getCurrentUserId();
        try {
            Path path = exportService.writeAllKnowledgeWithZip(userId, tagIds);
            String url = "/download/file?token=" + path.getFileName().toString();
            return Result.success(url);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure("文件导出失败");
        }
    }

    public Result<Double> queryExportProgress(String type) {
        Integer id = userInfoUtils.getCurrentUserId();

        return Result.success(exportService.queryExportProgress(id, type));
    }


}
