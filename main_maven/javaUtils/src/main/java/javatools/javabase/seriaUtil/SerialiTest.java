package javatools.javabase.seriaUtil;

import java.io.*;

public class SerialiTest implements Serializable {
    private String name;
    private int age;
    private boolean flag;
    public SerialiTest(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "name : " + name + ", age : " + age + ", flag : " + flag;
    }

    public static void main(String[] args) {
        SerialiTest st = new SerialiTest("ta", 38);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("F:\\file\\s.txt"));
            oos.writeObject(st);
            oos.close();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("F:\\file\\s.txt"));
            SerialiTest ss = (SerialiTest)ois.readObject();
            System.out.println(ss.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
