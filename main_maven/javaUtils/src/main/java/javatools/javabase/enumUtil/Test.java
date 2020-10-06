package javatools.javabase.enumUtil;

public class Test {

    public static void main(String[] args) {
        ColorEnum tt = ColorEnum.NO_PASS;
        System.out.println(tt.getText() + " ___ " + ColorEnum.parse(1));
        for (ColorEnum c : ColorEnum.values()) {
            System.out.println(c.getKey());
        }
    }
}
