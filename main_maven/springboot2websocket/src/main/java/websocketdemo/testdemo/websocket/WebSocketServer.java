package websocketdemo.testdemo.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    //新：使用map对象，便于根据userId来获取对应的WebSocket   ConcurrentHashMap 替换 CopyOnWriteArraySet
    //private static ConcurrentHashMap<String,WebSocketServer> websocketList = new ConcurrentHashMap<>();
    private Session session;        //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private String sid = "";        //接收sid

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {         //连接建立成功调用的方法
        this.session = session;
        if (!judgeIsExistSession(sid)) {
            webSocketSet.add(this);     //加入set中
            addOnlineCount();           //在线数加1
            System.out.println("有新的连接加入:" + sid + ",当前在线人数为" + getOnlineCount());
        } else {
            System.out.println("旧的连接再次进入:" + sid + ",当前在线人数为" + getOnlineCount());
        }
        this.sid = sid;
        try {
            sendMessage("connect is ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose() {         //连接关闭调用的方法
        webSocketSet.remove(this);      //从set中删除
        subOnlineCount();       //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("收到来自窗口" + sid + "的信息:" + message);
        for (WebSocketServer item : webSocketSet) {         //群发消息
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session 参数
     * @param error   参数
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    //实现服务器主动推送
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    //群发自定义消息
    public static void sendInfo(String message, @PathParam("sid") String sid) {
        System.out.println("推送消息到窗口" + sid + "，推送内容:" + message);
        for (WebSocketServer item : webSocketSet) {
            try {
                if (sid == null) {      //这里可以设定只推送给这个sid的，为null则全部推送
                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean judgeIsExistSession(String sid) {
        boolean retFlag = false;
        for (WebSocketServer item : webSocketSet) {
            if (item.sid.equals(sid)) {
                retFlag = true;
                break;
            }
        }
        return retFlag;
    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
