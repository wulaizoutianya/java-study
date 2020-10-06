package javatools.designPattern23.proxy.statics;

public class Test {
	
	public static void main(String[] args) {	//	��̬����
		Fruit apple = new Apple();
		FruitProxy fp = new FruitProxy(apple);
		fp.fruit();
	}
	
	/*public static void main(String[] args) {	//��̬����
		final Apple apple = new Apple();
		Fruit fruit = (Fruit)Proxy.newProxyInstance(apple.getClass().getClassLoader(), 
				apple.getClass().getInterfaces(), new InvocationHandler(){
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						System.out.println("dynamic before");
						Object retObj = method.invoke(apple, args);
						System.out.println("dynamic before");
						return retObj;
					}
		});
		fruit.fruit();
	}*/
	
}
