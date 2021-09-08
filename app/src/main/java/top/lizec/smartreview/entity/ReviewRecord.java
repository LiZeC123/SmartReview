package top.lizec.smartreview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class ReviewRecord {
    @TableId(type = IdType.AUTO)
    Integer id;
    String appType;
    String title;
    Integer reviewCount;
    Integer intervalTime;
    Long nextReviewTime;
}
