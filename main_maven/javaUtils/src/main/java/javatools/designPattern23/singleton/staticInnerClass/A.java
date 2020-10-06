package javatools.designPattern23.singleton.staticInnerClass;

public class A {
	
	private A(){}
	
	private static class B {
		private static final A a = new A();
	}
	
	public A getInstance(){
		return B.a;
	}
	
}
