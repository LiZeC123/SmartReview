package top.lizec.smartreview.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DQ {

    public static <T,R>  List<R> select(List<T> data, Function<T, R> selector) {
        return data.stream().map(selector).collect(Collectors.toList());
    }
}
