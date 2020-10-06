package javatools.ioUtil.file;

import java.io.File;
import java.io.IOException;

public class Test {
	
	/**
     * @param args the command line arguments
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        String lsSrcFile = "E:\\file\\real\\11.jpg";
        String lsDstDir = "E:\\file\\tmp";
        String lsDstFile = "E:\\file\\tmp\\dst.jpg";
        File loDstFile = new File(lsDstDir);
        if (!loDstFile.exists()) {
            loDstFile.mkdir();
        }
 
        long liStartTime = System.currentTimeMillis();
        FileSplitter loSplitter = new FileSplitter(lsSrcFile, lsDstDir, "test_cut");
        String[] laPartFiles = loSplitter.start();
        while (loSplitter.isBusy()) {
            Thread.sleep(200);
        }
        FileUnifier loUnifier = new FileUnifier(laPartFiles, lsDstFile);
        loUnifier.start();
        while (loUnifier.isBusy()) {
            Thread.sleep(200);
        }
        System.out.println("done, spend " + (System.currentTimeMillis() - liStartTime) + " milsecs");
    }
	
}
