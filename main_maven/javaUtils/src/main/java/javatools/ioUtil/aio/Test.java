package javatools.ioUtil.aio;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

public class Test {
	
	@SuppressWarnings("resource")
	public static void mappedBuffer() {  
        try {
        	long lg1 = new Date().getTime();
        	FileChannel read = new FileInputStream("E://test//1.txt").getChannel();  
            FileChannel writer = new RandomAccessFile("E://test//1.txt","rw").getChannel();  
            long i = 0;  
            long size = read.size()/30;  
            ByteBuffer bb,cc = null;  
            while(i < read.size() && (read.size() - i) > size){  
                bb = read.map(FileChannel.MapMode.READ_ONLY, i, size);  
                cc = writer.map(FileChannel.MapMode.READ_WRITE, i, size);  
                cc.put(bb);  
                i += size;  
                bb.clear();  
                cc.clear();  
            }  
            bb = read.map(FileChannel.MapMode.READ_ONLY, i, read.size()-i);  
            cc.put(bb);  
            bb.clear();  
            cc.clear();  
            read.close();  
            writer.close();
            long lg2 = new Date().getTime();
			System.out.println(lg2 - lg1);
		} catch (IOException e) {
			e.printStackTrace();
		}
    } 
	
	public static void main(String[] args) {
		mappedBuffer();
	}
	
}
