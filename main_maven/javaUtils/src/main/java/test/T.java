package test;

import java.util.HashMap;
import java.util.Map;

public class T {

    public static void main(String[] args) {
        /*P p = new P(1, "a");
        Map<P, String> m = new HashMap<>();
        m.put(p, "test");
        System.out.println(m.get(new P(1, "a")));
        System.out.println(new P(1, "a").hashCode());
        System.out.println(new P(1, "a").hashCode());
        System.out.println("*****************");
        System.out.println(2 >> 1);*/

        int[] a = {1, 2, 3, 4, 5, 6};
        System.out.println(sequentialSearch2(a, 4));
    }

    public static int sequentialSearch2(int[] a, int key) {
        if (a[0] == key) {
            return 0;
        }
        int index = a.length - 1;
        a[0] = key;// 将下标为0的数组元素设置为哨兵
        while (a[index] != key) {
            index--;
        }
        return index == 0 ? -1 : index;
    }
}
