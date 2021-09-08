package top.lizec.smartreview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ReviewState {
    @TableId(type = IdType.AUTO)
    Integer knowledgeId;
    Integer reviewCount;
    Integer memoryLevel;
    Integer intervalTime;
    Date nextReviewTime;

    public void increaseCount() {
        this.reviewCount++;
    }
}
