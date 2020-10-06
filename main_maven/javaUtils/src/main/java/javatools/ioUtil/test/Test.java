package javatools.ioUtil.test;

import java.io.FileInputStream;
import java.io.InputStream;

public class Test {
	
	public static void main(String[] args) {
		try {
			InputStream is = new FileInputStream("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
