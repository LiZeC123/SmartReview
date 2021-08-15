package top.lizec.smartreview.utils.cache;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

public class TimeoutCache<T> implements Cache<T> {
    private final Supplier<T> defaultGet;
    private final long timeoutSecond;
    private T value;
    private Instant failTime;

    public TimeoutCache(Supplier<T> defaultGet, long timeoutSecond) {
        this.defaultGet = defaultGet;
        this.timeoutSecond = timeoutSecond;

        update();
    }


    @Override
    public T getValue() {
        if (Instant.now().isAfter(failTime)) {
            update();
        }
        return value;
    }

    private void update() {
        this.value = defaultGet.get();
        this.failTime = Instant.now().plus(Duration.ofSeconds(timeoutSecond));
    }
}
