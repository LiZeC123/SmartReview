package top.lizec.smartreview.response;

import lombok.Data;

@Data
public class Result<T> {
    private final Boolean success;
    private final String msg;
    private final T data;

    public Result(Boolean success, String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return success("Success", null);
    }

    public static <T> Result<T> success(T data) {
        return success("Success", data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(true, msg, data);
    }

    public static <T> Result<T> failure() {
        return failure("Failure", null);
    }

    public static <T> Result<T> failure(String msg) {
        return failure(msg, null);
    }

    public static <T> Result<T> failure(String msg, T data) {
        return new Result<>(false, msg, data);
    }


    public String toJSON() {
        return "{" +
                "success:" + success +
                ", msg:'" + msg + '\'' +
                ", data:" + data +
                '}';
    }


    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }


}
