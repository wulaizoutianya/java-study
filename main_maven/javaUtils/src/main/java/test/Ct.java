package test;

import java.io.Serializable;
import java.util.LinkedList;

public class Ct {

    public static void main(String[] args) {
        /*LinkedList<String> tt = new LinkedList<>();
        tt.add("aa00");
        System.out.println(tt+ " **** " + tt.size());
        String s = tt.removeFirst();
        System.out.println(s+ " ---- " + tt.size());*/
        byte a = 1;
        a+=1;
        byte c = 1;
        System.out.println(getType(a));
        byte b = 127;
    }

    public static String getType(Object o) {
        return o.getClass().toString();
    }
}

class A implements Serializable {

}