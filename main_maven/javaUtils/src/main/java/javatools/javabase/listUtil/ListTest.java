package javatools.javabase.listUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ListTest {

	public static void main(String[] args) {
		/*List<Tt> ll = new ArrayList<>();
		Tt t1 = new Tt();
		t1.setT(1);
		t1.setB("a");
		Tt t2 = new Tt();
		t2.setT(3);
		t2.setB("c");
		Tt t3 = new Tt();
		t3.setT(2);
		t3.setB("b");
		
		ll.add(t1);
		ll.add(t2);
		ll.add(t3);
		
		Collections.sort(ll, (a , b) -> new Integer(a.getT()).compareTo(b.getT()));
		
		for (int i = 0; i < ll.size(); i++) {
			System.out.println(ll.get(i).getT());
		}
		
		List<String> tt = new ArrayList<>();
		tt.add("abd");
		tt.add("yui");
		System.out.println(tt.contains("abd"));*/
		
		
		List<String> list = new ArrayList<>();
		list.add("a");list.add("b");list.add("c");
		StringJoiner roleJoiner = new StringJoiner(",");	//需要的间隔符
		list.forEach(roleJoiner::add);
		System.out.println(roleJoiner.toString());
		
	}
	
}
