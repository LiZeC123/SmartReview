package top.lizec.smartreview.schedule;

import java.util.List;

import top.lizec.smartreview.entity.KnowledgeReviewState;

public interface StateDataMapper {
    /**
     * 在给定的切片区间上加载数据
     *
     * @param location 切片区间
     * @return 此切片上对应的数据
     */
    List<KnowledgeReviewState> loaderBetween(SliceLocation location);


    /**
     * 保存数据
     *
     * @param states 需要保存的数据
     */
    void write(List<KnowledgeReviewState> states);
}
