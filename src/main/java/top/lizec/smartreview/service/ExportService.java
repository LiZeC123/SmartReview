package top.lizec.smartreview.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.lizec.smartreview.dto.AppCount;
import top.lizec.smartreview.entity.Knowledge;
import top.lizec.smartreview.mapper.ExportDao;
import top.lizec.smartreview.mapper.KnowledgeDao;
import top.lizec.smartreview.mapper.TagDao;
import top.lizec.smartreview.utils.FileUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Log4j2
@Service
public class ExportService {
    private static final String PREFIX = "EXPORT_SERVICE_";

    @Resource
    ExportDao exportDao;

    @Resource
    TagDao tagDao;

    @Resource
    KnowledgeDao knowledgeDao;

    @Resource
    RedisTemplate<String, Double> redisTemplate;

    @Resource
    FileUtils fileUtils;

    public List<AppCount> queryAppCountInfo(Integer userId) {
        return exportDao.queryAppCountInfo(userId);
    }

    //public String generate


    public Path generateFile(Integer tagId) throws IOException {
        List<Integer> knowledgeIds = tagDao.getKnowledgeIdByTag(tagId);

        String tagName = tagDao.getTagName(tagId);

        List<Knowledge> knowledgeList = knowledgeDao.selectByIds(knowledgeIds);


        Path tempFile = fileUtils.createTempFile(".md");


        try (OutputStream out = Files.newOutputStream(tempFile)) {
            out.write(String.format("%s\n===================\n", tagName).getBytes(StandardCharsets.UTF_8));
            for (Knowledge knowledge : knowledgeList) {
                out.write(knowledge.toMarkdown().getBytes(StandardCharsets.UTF_8));
            }
        }

        return tempFile;
    }

    public Path generateFileQuietly(Integer tagId) {
        try {
            return generateFile(tagId);
        } catch (IOException e) {
            log.warn("文件创建失败");
            e.printStackTrace();
            return null;
        }
    }


    public Path writeAllKnowledgeWithZip(List<Integer> tagIds) throws IOException {
        final List<Path> srcFiles = tagIds.stream()
                .map(this::generateFileQuietly)
                .filter(Objects::nonNull).
                        collect(Collectors.toList());

        Path archive = fileUtils.createTempFile(".zip");
        final OutputStream outputStream = Files.newOutputStream(archive);
        try (ZipOutputStream zipOut = new ZipOutputStream(outputStream)) {
            for (int i = 0; i < srcFiles.size(); i++) {
                final String tagName = tagDao.getTagName(tagIds.get(i));
                final InputStream fis = Files.newInputStream(srcFiles.get(i));
                ZipEntry zipEntry = new ZipEntry(tagName + ".md");
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024 * 1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return archive;
    }

    public Double queryExportProgress(Integer userId, String type) {
        return redisTemplate.opsForValue().get(String.format("%s-%d-%s", PREFIX, userId, type));
    }
}
