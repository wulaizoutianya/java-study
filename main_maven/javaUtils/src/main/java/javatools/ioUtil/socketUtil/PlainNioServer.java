package javatools.ioUtil.socketUtil;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class PlainNioServer {

    public void serve(int port) throws Exception {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        ServerSocket ss = serverChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        ss.bind(address);                                                   //1
        Selector selector = Selector.open();                                //2
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);           //3
        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
        for (; ; ) {
            try {
                selector.select();                                          //4
            } catch (Exception ex) {
                ex.printStackTrace();
                break;
            }
            Set<SelectionKey> readyKeys = selector.selectedKeys();          //5
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) {                               //6
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        System.out.println("等待客户端连接");
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());     //7
                        System.out.println("Accepted connection from " + client);
                    }
                    if (key.isWritable()) {                                 //8
                        System.out.println("等待客户端写入");
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        while (buffer.hasRemaining()) {
                            System.out.println(" ----123");
                            if (client.write(buffer) == 0) {                //9
                                break;
                            }
                        }
                        client.close();                                     //10
                    }
                } catch (Exception ex) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (Exception cex) {
                        // 在关闭时忽略
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            new PlainNioServer().serve(1234);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
