package javatools.javabase.enumUtil;

import java.util.HashMap;

public enum ColorEnum {
    NO_PASS(0, "未通过"), PASS(1, "通过");
    private int key;

    private String text;

    ColorEnum(int key, String text) {
        this.key = key;
        this.text = text;
    }

    public int getKey() {
        return key;
    }

    public String getText() {
        return text;
    }

    private static HashMap<Integer,ColorEnum> map = new HashMap<>();
    static {
        for(ColorEnum d : ColorEnum.values()){
            map.put(d.key, d);
        }
    }

    public static ColorEnum parse(Integer index) {
        if(map.containsKey(index)){
            return map.get(index);
        }
        return null;
    }
}
