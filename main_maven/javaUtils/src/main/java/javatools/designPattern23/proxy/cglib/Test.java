package javatools.designPattern23.proxy.cglib;

public class Test {
	
	public static void main(String[] args) {
		try {
			Apple a,b,c;
			a = new Apple();
			b = a;
			c = new Apple();
			System.out.println((a == b) + " -- " + (b == c));
			
			String aa = new String("null");
			String bb = aa;
			String cc = new String("null");
			cc.intern();
			System.out.println((aa == bb) + " -- " + (aa == cc));
			
			Apple apple = (Apple) new DynamicProxy(Apple.class.newInstance()).getProxyInstance();
			apple.fruit();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
}
