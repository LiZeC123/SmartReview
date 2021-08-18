package top.lizec.smartreview.entity;

public enum AppType {
    BASE("基本类型"),
    EnglishWordBook("英语单词本"),
    LeetCodeNote("力扣笔记");

    private final String name;

    AppType( String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public AppType getTypeByCode(String code) {
        return AppType.valueOf(code);
    }
}
