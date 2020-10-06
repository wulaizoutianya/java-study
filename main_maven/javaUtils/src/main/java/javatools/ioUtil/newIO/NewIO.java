package javatools.ioUtil.newIO;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

public class NewIO {
	
	@SuppressWarnings("resource")
	public static void newIOReadFile(){
        try {
        	long lg1 = new Date().getTime();
        	FileChannel read = new RandomAccessFile("E://test//1.txt","r").getChannel();
            FileChannel writer = new RandomAccessFile("E://test//2.txt","rw").getChannel();
            ByteBuffer bb = ByteBuffer.allocate(14*1024*1024);
            while(read.read(bb) != -1){
                bb.flip();
                writer.write(bb);
                bb.clear();
            }  
            read.close();
            writer.close();
            long lg2 = new Date().getTime();
			System.out.println(lg2 - lg1);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public static void randomReadFile(){
        try {
        	long lg1 = new Date().getTime();
        	RandomAccessFile read = new RandomAccessFile("E://file//1.txt","r");  
            RandomAccessFile writer = new RandomAccessFile("E://file//2.txt","rw");  
            byte[] b = new byte[2*1024*1024];  
            while(read.read(b) != -1){  
                writer.write(b);  
            }  
            writer.close();  
            read.close();
            long lg2 = new Date().getTime();
			System.out.println(lg2 - lg1);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public static void main(String[] args) {
		randomReadFile();
	}
	
}
