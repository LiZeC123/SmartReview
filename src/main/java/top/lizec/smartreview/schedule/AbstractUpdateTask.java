package top.lizec.smartreview.schedule;

import java.util.List;
import java.util.stream.Collectors;

import top.lizec.smartreview.entity.KnowledgeReviewState;

abstract class AbstractUpdateTask implements StateUpdater, StateDataMapper {
    private final int READ_EPOCH;
    private final int WRITE_EPOCH;

    public AbstractUpdateTask(int READ_EPOCH, int WRITE_EPOCH) {
        this.READ_EPOCH = READ_EPOCH;
        this.WRITE_EPOCH = WRITE_EPOCH;
    }

    public void batchUpdate(int maxCount) {
        ListUtils.genSlice(maxCount, READ_EPOCH).stream()
                .map(this::loaderBetween)
                .forEach(this::updateAndWrite);
    }

    public void updateAndWrite(List<KnowledgeReviewState> states) {
        List<KnowledgeReviewState> newStates = states.stream()
                .map(this::update).collect(Collectors.toList());

        ListUtils.genSlice(states.size(), WRITE_EPOCH).stream()
                .map(location -> newStates.subList(location.begin, location.end))
                .forEach(this::write);
    }
}
