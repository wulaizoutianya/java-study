package javatools.designPattern23.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxy implements InvocationHandler{
	
	// ���������ʵ��
	Object obj;
	
	// ���������ߵ�ʵ��������̬������Ĺ��캯����
	public DynamicProxy(Object obj){
		this.obj = obj;
	}
	
	//����InvocationHandler�ӿ��е�invoke()����,����Ҫ���ǣ���̬����ģʽ����ʹ�������ڲ��ı�ԭ�����еĴ���ṹ������£���ԭ���ġ���ʵ������������չ��
	//��ǿ�书�ܣ����ҿ��Դﵽ���Ʊ�����������Ϊ�������before��after�������ǿ��Խ�����������������չ���ˡ�
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		//before ��doSomething();
		System.out.println("ִ��֮ǰ���Ĵ���");
		Object returnObj = method.invoke(this.obj, args);
		//after : doSomething();
		System.out.println("ִ��֮�� ���Ĵ���");
		return returnObj;
	}
	
	

}
