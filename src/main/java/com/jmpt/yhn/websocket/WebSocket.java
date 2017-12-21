package com.jmpt.yhn.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by yhn on 2017/8/31.
 */
@Component
@ServerEndpoint("/websocket")
@Slf4j
public class WebSocket {
    private Session session;
    private static  CopyOnWriteArraySet<WebSocket> webSocketSet  = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onopen(Session session){
        this.session = session;
        webSocketSet.add(this);
        log.info("【websocket消息】有了新的连接，总数为：{}",webSocketSet.size());
    }
    @OnClose
    public void onclose(){
        webSocketSet.remove(this);
        log.info("【websocket消息】连接断开，总数为：{}",webSocketSet.size());
    }
    @OnMessage
    public void onMessage(String message){
        log.info("【websocket消息】收到客户端发来的消息:{}",message);
    }
    public void sendMessage(String message){
        for(WebSocket webSocket:webSocketSet){
                log.info("【websocket消息】广播消息，message={}",message);
                try {
                    webSocket.session.getBasicRemote().sendText(message);
                }catch(Exception e){
                    e.printStackTrace();
                }
        }
    }
}

