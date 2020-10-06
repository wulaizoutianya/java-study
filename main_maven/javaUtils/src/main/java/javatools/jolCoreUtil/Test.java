package javatools.jolCoreUtil;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

public class Test {

    public static void main(String[] args) {
        //System.out.println(ClassLayout.parseInstance(new NullClass()).toPrintable());

        /**
         * 我们可以看到TestNotNull的类占用空间是24字节，其中头部占用12字节，变量a是int类型，占用4字节,变量nullObject是引用，占用了4字节，
         * 最后填充了4个字节，总共是24个字节，与我们之前的预测一致。但是，因为我们实例化了NullObject,这个对象会存在于内存中，
         * 所以我们还需要加上这个对象的内存占用16字节，那总共就是24bytes+16bytes=40bytes
         */
        //打印实例的内存布局
        System.out.println(ClassLayout.parseInstance(new NotNullClass()).toPrintable() + " -- 打印实例的内存布局");
        //打印对象的所有相关内存占用
        System.out.println(GraphLayout.parseInstance(new NotNullClass()).toPrintable() + " -- 打印对象的所有相关内存占用");
        //打印对象的所有内存结果并统计
        System.out.println(GraphLayout.parseInstance(new NotNullClass()).toFootprint() + " -- 打印对象的所有内存结果并统计");
    }
}
