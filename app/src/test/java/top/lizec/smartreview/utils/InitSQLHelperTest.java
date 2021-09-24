package top.lizec.smartreview.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class InitSQLHelperTest {

    @Test
    void generateInitMySQL() throws IOException {
        Path sqlite = Path.of("sql", "init_sqlite.sql");
        Path mysql = Path.of("sql", "init_mysql.sql");
        InitSQLHelper.generateInitMySQL(sqlite, mysql);
    }
}