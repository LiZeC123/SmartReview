package top.lizec.smartreview.dto;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;

import java.util.List;

@Data
public class LinkDto {
    private static final Gson gson = new Gson();

    private String name;
    private String url;

    public static String toJson(List<LinkDto> lists) {
        return gson.toJson(lists);
    }

    public static LinkDto fromJson(String json) {
        return gson.fromJson(json, LinkDto.class);
    }

    public static List<LinkDto> fromListJson(String json) {
        return gson.fromJson(json, new TypeToken<List<LinkDto>>() {}.getType());
    }

    public String toJson() {
        return gson.toJson(this);
    }

}
