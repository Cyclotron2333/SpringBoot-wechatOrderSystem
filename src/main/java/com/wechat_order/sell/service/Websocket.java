package com.wechat_order.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * websocket的具体实现类
 */

@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class Websocket {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArrayList<Websocket> websocketSet = new CopyOnWriteArrayList<>();

    /**
     * 连接建立成功调用的方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        websocketSet.add(this);
        log.info("【websocket消息】有新的连接，总数：{}", websocketSet.size());
    }

    @OnClose
    public void onClose(Session session){
        websocketSet.remove(this);
        log.info("【websocket消息】连接断开，总数：{}", websocketSet.size());
    }

    @OnMessage
    public void onMessage(String message){
        log.info("【websocket消息】受到客户端发来的消息：{}", message);
    }

    public void sendMessage(String message){
        for (Websocket websocket : websocketSet){
            log.info("【websocket消息】广播消息：{}", message);
            try {
                websocket.session.getBasicRemote().sendText(message);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
