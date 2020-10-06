package javatools.designPattern23.proxy.dynamic;

import java.lang.reflect.Proxy;

public class Test {
	
	public static void main(String[] args) {
		try {
			Fruit fruit = (Fruit) Proxy.newProxyInstance(Apple.class.getClassLoader(), Apple.class.getInterfaces(), 
					new DynamicProxy(Apple.class.newInstance()));
			fruit.fruit();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
}
