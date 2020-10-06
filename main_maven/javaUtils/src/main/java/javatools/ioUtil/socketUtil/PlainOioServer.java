package javatools.ioUtil.socketUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlainOioServer {

    public void serve(int port) throws IOException {
        final ServerSocket socket = new ServerSocket(port);     //1
        try {
            for (; ; ) {
                System.out.println(getdate() + "  等待客户端连接...");
                final Socket clientSocket = socket.accept();    //2
                System.out.println("Accepted connection from " + clientSocket);
                new Thread(() -> {          //3
                    OutputStream out;
                    try {
                        out = clientSocket.getOutputStream();
                        out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));                            //4
                        out.flush();
                        clientSocket.close();                //5
                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            clientSocket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();                                        //6
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getdate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }

    public static void main(String[] args) {
        try {
            new PlainOioServer().serve(1234);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
