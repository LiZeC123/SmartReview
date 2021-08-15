package top.lizec.smartreview.schedule;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    /**
     * 将区间[0, maxLength) 分割为sliceLength长度的切片
     * <p>
     * 切片中包含0, 但不包含maxLength, 最后一个切片长度可能不足sliceLength
     *
     * @param maxLength   区间的最大长度
     * @param sliceLength 每个切片的长度
     * @return 将区间按照切片长度进行切片后的结果
     */
    public static List<SliceLocation> genSlice(int maxLength, int sliceLength) {
        if (sliceLength == 0) {
            throw new IllegalArgumentException("sliceLength cannot be zero");
        }

        int fullNum = maxLength / sliceLength;
        List<SliceLocation> list = new ArrayList<>(fullNum + 1);
        for (int i = 0; i < fullNum; i++) {
            list.add(new SliceLocation(i * sliceLength, (i + 1) * sliceLength - 1));
        }

        if (maxLength % sliceLength != 0) {
            list.add(new SliceLocation(fullNum * sliceLength, maxLength - 1));
        }

        return list;
    }


}
