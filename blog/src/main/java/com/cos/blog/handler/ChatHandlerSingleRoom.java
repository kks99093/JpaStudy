package com.cos.blog.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

//채팅에 접속한 사용자 세션을 관리하고, 모든 사용자에게 채팅 메세지를 전달합니다.
@Component // Bean생성
public class ChatHandlerSingleRoom extends TextWebSocketHandler {
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
			// 웹소켓이 연결되면 호출되는 함수
		
		// 채팅방에 접속한 사용자 세션(정보)을 리스트에 추가함
		sessionList.add(session);
		
		// 모든 세션(사용자)에 채팅 전달
		for (int i = 0; i < sessionList.size(); i++) {
			WebSocketSession s = sessionList.get(i);
			s.sendMessage(new TextMessage(session.getId() + "님이 입장 했습니다."));
			//이부분이 evt에다가 값을 넣어주는 부분이구나
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 메세지를 처리하는 메서드
		// 모든 세션(사용자)에 채팅 전달
		for (int i = 0; i < sessionList.size(); i++) {
			WebSocketSession s = sessionList.get(i);
			s.sendMessage(new TextMessage(session.getId() + " : " + message.getPayload()));
		}
	}


	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 웹소켓 연결이 종료되면 호출되는 함수
		// 채팅방에서 퇴장한 사용자 세션(정보)을 리스트에서 제거
		sessionList.remove(session);

		// 모든 세션(사용자)에 채팅 전달
		for (int i = 0; i < sessionList.size(); i++) {
			WebSocketSession s = sessionList.get(i);
			s.sendMessage(new TextMessage(session.getId() + "님이 퇴장 했습니다."));
		}

	}
}
