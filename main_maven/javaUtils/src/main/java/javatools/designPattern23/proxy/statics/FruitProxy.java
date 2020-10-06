package javatools.designPattern23.proxy.statics;

public class FruitProxy implements Fruit {
	
	private Fruit fruit;
	
	public FruitProxy(Fruit fruit){
		this.fruit = fruit;
	}
	
	public void fruit() {
		System.out.println("I am apple proxy person !");
		fruit.fruit();
	}

}
