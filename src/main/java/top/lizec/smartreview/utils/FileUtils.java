package top.lizec.smartreview.utils;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class FileUtils {
    private static final Path tempFolder = Path.of("tmpFile");

    public FileUtils() throws IOException {
        if (Files.notExists(tempFolder)) {
            Files.createDirectories(tempFolder);
        }
    }


    public Path createTempFile(String fileType) throws IOException {
        return Files.createFile(Path.of(tempFolder.toString(), UUID.randomUUID().toString() + fileType));
    }

    public Path getFilePath(String filename) {
        return Path.of(tempFolder.toString(), filename);
    }

}
