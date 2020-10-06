package javatools.designPattern23.proxy.cglib;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class DynamicProxy implements MethodInterceptor{
	
	// ���������ʵ��
	private Object obj;
	
	// ���������ߵ�ʵ��������̬������Ĺ��캯����
	public DynamicProxy(Object obj){
		this.obj = obj;
	}
	
	public Object getProxyInstance(){
		Enhancer en = new Enhancer();	//1.������
		en.setSuperclass(obj.getClass());	//2.���ø���
		en.setCallback(this);	//3.���ûص�����
		return en.create();		//4.��������(�������)
	}

	public Object intercept(Object arg0, Method method, Object[] arg2,
			MethodProxy arg3) throws Throwable {
		//before ��doSomething();
		System.out.println("cglibִ��֮ǰ���Ĵ���");
		Object returnObj = method.invoke(obj, arg2);
		//after : doSomething();
		System.out.println("cglibִ��֮�� ���Ĵ���");
		return returnObj;
	}
	

}
