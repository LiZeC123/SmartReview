package top.lizec.smartreview.algo.entity;

public enum WordTag {
    HS(0), COCA(1),  CET(2), TOEFL(3), GRE(4);

    int value;

    WordTag(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
