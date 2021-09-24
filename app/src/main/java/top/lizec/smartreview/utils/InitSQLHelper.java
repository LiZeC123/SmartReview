package top.lizec.smartreview.utils;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public final class InitSQLHelper {


    /**
     * 数据库转换工具, 将SQLite数据库脚本转换为MySQL数据库脚本
     */
    public static void generateInitMySQL(Path sqliteFile, Path mysqlFile) throws IOException {
        Scanner in = new Scanner(Files.newInputStream(sqliteFile)) ;
        PrintStream out = new PrintStream(Files.newOutputStream(mysqlFile));

        out.println("DROP DATABASE IF EXISTS smart_review;");
        out.println("CREATE DATABASE smart_review DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
        out.println("USE smart_review;");
        out.println();
        while (in.hasNext()) {
            final String line = in.nextLine();
            out.println(line.replace("AUTOINCREMENT", "AUTO_INCREMENT"));
        }

        in.close();
        out.close();
    }

}
