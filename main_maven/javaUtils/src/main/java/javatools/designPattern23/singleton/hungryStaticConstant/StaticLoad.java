package javatools.designPattern23.singleton.hungryStaticConstant;

public class StaticLoad {



    public static int count1;
    public static int count2 = 0;

    private static StaticLoad staticLoad = new StaticLoad();

    private StaticLoad() {
        count1++;
        count2++;
        System.out.println(count1 + " --- " + count2);
    }

    public static StaticLoad getStaticLoadInstance(){
        return staticLoad;
    }
}
