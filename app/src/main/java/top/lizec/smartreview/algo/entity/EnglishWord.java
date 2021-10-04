package top.lizec.smartreview.algo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnglishWord implements Comparable<EnglishWord> {
    private String word;
    private String tag;
    private Integer difficulty;

    @Override
    public int compareTo(EnglishWord o) {
        // 难度得分越大越靠前, 因此取负数
        return -this.difficulty.compareTo(o.difficulty);
    }
}
