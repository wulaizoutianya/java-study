package javatools.ioUtil.oldIO;

import java.io.File;
import java.io.FileInputStream;

public class Test {
	
	public static void main(String[] args) {
		try {
			FileInputStream is = new FileInputStream(new File("E://file//real//11.jpg"));
			byte[] fileBuff = new byte[512 * 1024];
			is.close();
			System.out.println(fileBuff.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
