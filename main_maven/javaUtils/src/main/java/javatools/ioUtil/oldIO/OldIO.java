package javatools.ioUtil.oldIO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class OldIO {
	

	public static void oldIOReadFile(){  
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader("E://test//1.txt"));  
		    PrintWriter pw = new PrintWriter("E://test//2.txt");  
		    char[] c = new char[100*1024*1024];
		    long lg1 = new Date().getTime();
		    for(;;){  
		        if(br.read(c)!=-1){  
		            pw.print(c);  
		        }else{  
		            break;  
		        }  
		    }  
		    pw.close();
		    br.close();
		    long lg2 = new Date().getTime();
			System.out.println(lg2 - lg1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeToFile(){  
	    PrintWriter pw;
	    long lg1 = new Date().getTime();
		try {
			pw = new PrintWriter("E://test//1.txt");
			for(int i = 0; i < 50000000; i++){  
		    	pw.print("cxlkjvsd夺震肚腩埒有");
		    }  
		    pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		long lg2 = System.currentTimeMillis();
		System.out.println(lg2 - lg1);
	}
	
	public static void main(String[] args) {
		oldIOReadFile();
	}
	
}
