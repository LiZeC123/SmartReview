package top.lizec.smartreview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewState {
    @TableId(type = IdType.AUTO)
    Integer knowledgeId;
    Integer reviewCount;
    Integer memoryLevel;
    Integer intervalTime;
    LocalDateTime nextReviewTime;

    public void increaseCount() {
        this.reviewCount++;
    }
}
