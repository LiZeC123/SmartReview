package top.lizec.smartreview.entity;

import lombok.Data;

import java.sql.Date;


@Data
public class Knowledge {
    private Integer id;
    private String appType;
    private String title;
    private String content;
    private Integer creator;
    private String link;
    private String tag;
    private Date createTime;
    private Date modifiedTime;


    public String toMarkdown() {
        return "\n### " + title +
                "\n正文:\n" +
                content + "\n" +
                "\n扩展资源:\n" +
                link + "\n" +
                "\n标签:\n" +
                tag + "\n"
                + "\n";
    }
}