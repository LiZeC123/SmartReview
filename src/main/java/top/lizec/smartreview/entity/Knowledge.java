package top.lizec.smartreview.entity;

import java.sql.Date;

import lombok.Data;


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
}