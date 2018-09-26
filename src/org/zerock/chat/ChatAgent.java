package org.zerock.chat;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import lombok.extern.log4j.Log4j;

@ServerEndpoint("/chat")
@Log4j
public class ChatAgent {
	
	private Session session;
	private String userIP;
	
	@OnOpen
	public void open(Session session) {
		
		log.info("a user connected...............");
		log.info(session);
		this.session = session;
		
//		userIP = session.getUserProperties()
//		.get("javax.websocket.endpoint.remoteAddress").toString();
		
		log.info(this);
		ChatManager.INSTANCE.broadcast(userIP + "a user connected......");
		ChatManager.INSTANCE.addAgent(this);
		
	}
	
	@OnMessage
	public void onMessage(String msg)throws Throwable{
		log.info("message............." + msg);
		
		ChatManager.INSTANCE.broadcast(userIP + " : " + msg);
		
	}
	
	@OnClose
	public void close() {
		log.info("a user disconnected...............");
		ChatManager.INSTANCE.broadcast("a user disconnected......");
	}

	public void sendMsg(String msg)throws Exception {
		
		session.getBasicRemote().sendText(msg);
	}
	

	
}
