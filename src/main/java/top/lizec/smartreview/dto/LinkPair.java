package top.lizec.smartreview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkPair {
    private String name;
    private String url;


    /**
     * 将一个类似如下格式的字符串分割为名称和URL两个部分 初阶韦氏词典 --> https://www.learnersdictionary.com/definition/
     *
     * @param line 需要分割的字符串
     */
    public LinkPair(String line) {
        String[] s = line.split(" --> ");
        name = s[0];
        url = s[1];
    }
}
