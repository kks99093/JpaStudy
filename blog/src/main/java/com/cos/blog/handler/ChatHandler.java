package com.cos.blog.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

//채팅에 접속한 사용자 세션을 관리하고, 모든 사용자에게 채팅 메세지를 전달합니다.
@Component // Bean생성
public class ChatHandler extends TextWebSocketHandler {
	// (<"bang_id", 방ID>, <"session", 세션>) - (<"bang_id", 방ID>, <"session", 세션>) - (<"bang_id", 방ID>, <"session", 세션>) 형태 
	// 그럼 여기에 그냥 <"username",유저ID>해서 한개 더 보내면 되는거 아닌가?
	private List<Map<String, Object>> sessionList = new ArrayList<Map<String, Object>>();
	
	
	//왜 소켓 연결됐을때 처리할 내용을 TextMessage에다가 넣어놓은 거지?
	
	// 클라이언트가 서버로 메세지 전송 처리
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
									//여기서 session은 각각 사용자를 의미하는듯
		super.handleTextMessage(session, message);
        
		// JSON --> Map으로 변환
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> mapReceive = objectMapper.readValue(message.getPayload(), Map.class);
		//mapReceive에는 요청한 사람의 정보가 담겨있는건가? getPayload몇개의 메세지가 와있는지 확인?
		
		switch (mapReceive.get("cmd")) { //키값이 cmd로 된 벨류 ex) Map('cmd','value')
			// 사용자가 입장했을때 보낼 메세지부분
			case "CMD_ENTER":
				// 세션 리스트에 저장
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("bang_id", mapReceive.get("bang_id"));
				map.put("session", session);
				sessionList.add(map); //방번호와 사용자정보를 입력한다음 sessionList(사용자 목록)에다가 넣음
				// 같은 채팅방에 입장 메세지 전송
				for (int i = 0; i < sessionList.size(); i++) {
					Map<String, Object> mapSessionList = sessionList.get(i); // 세션리스트의 사용자 1~ ...까지 조회
					String bang_id = (String) mapSessionList.get("bang_id"); // 세션리스트의 사용자 방 id를 가져옴
					WebSocketSession sess = (WebSocketSession) mapSessionList.get("session"); // 세션리스트의 사용자정보를 가져옴
					
					if(bang_id.equals(mapReceive.get("bang_id"))) { //요청한사람의 방id와 조회한사람의 방id가 같다면
						Map<String, String> mapToSend = new HashMap<String, String>(); // Map객체 생성
						mapToSend.put("bang_id", bang_id);
						mapToSend.put("cmd", "CMD_ENTER");
						mapToSend.put("msg", session.getId() +  "님이 입장 했습니다.");
						//map에다가 값을 넣어줌
						String jsonStr = objectMapper.writeValueAsString(mapToSend);
						//map에 넣은것을 json으로 변환
						sess.sendMessage(new TextMessage(jsonStr));
						//값을 반환? 보내줌
					}
				}
				break;
				
			// CLIENT 메세지
			case "CMD_MSG_SEND":
				// 사용자 채팅을 보내는 부분
				for (int i = 0; i < sessionList.size(); i++) {
					Map<String, Object> mapSessionList = sessionList.get(i); // 세션리스트의 사용자 1~ ...까지 조회
					String bang_id = (String) mapSessionList.get("bang_id"); // 세션리스트의 사용자 방 id를 가져옴
					WebSocketSession sess = (WebSocketSession) mapSessionList.get("session"); // 세션리스트의 사용자정보를 가져옴
		
					if (bang_id.equals(mapReceive.get("bang_id"))) { //요청한사람의 방id와 조회한사람의 방id가 같다면
						Map<String, String> mapToSend = new HashMap<String, String>(); // Map객체 생성
						mapToSend.put("bang_id", bang_id);
						mapToSend.put("cmd", "CMD_MSG_SEND");
						mapToSend.put("msg", session.getId() + " : " + mapReceive.get("msg"));
						//map에다가 값을 넣어줌
						String jsonStr = objectMapper.writeValueAsString(mapToSend);
						//map에 넣은것을 json으로 변환
						sess.sendMessage(new TextMessage(jsonStr));
						//값을 반환? 보내줌
					}
					//윗부분과 다른거는 SessionList에 추가해주냐 안해주냐 차이
				}
				break;
		}
	}

	// 클라이언트가 연결을 끊음 처리
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		super.afterConnectionClosed(session, status);
        
		ObjectMapper objectMapper = new ObjectMapper();
		String now_bang_id = "";
		
		// 사용자 세션을 리스트에서 제거
		for (int i = 0; i < sessionList.size(); i++) {
			Map<String, Object> map = sessionList.get(i);
			String bang_id = (String) map.get("bang_id");
			WebSocketSession sess = (WebSocketSession) map.get("session");
			
			if(session.equals(sess)) { //세션리스트를 한바퀴돌면서 세션정보가 같은사람이 있으면 
				now_bang_id = bang_id; // 방id를 now_bang_id에다가 저장하고
				sessionList.remove(map); // 세션목록에서 해당 사용자세션을 지움
				break;
			}	
		}
		
		// 같은 채팅방에 퇴장 메세지 전송 
		for (int i = 0; i < sessionList.size(); i++) {
			Map<String, Object> mapSessionList = sessionList.get(i);
			String bang_id = (String) mapSessionList.get("bang_id");
			WebSocketSession sess = (WebSocketSession) mapSessionList.get("session");

			if (bang_id.equals(now_bang_id)) {
				Map<String, String> mapToSend = new HashMap<String, String>();
				mapToSend.put("bang_id", bang_id);
				mapToSend.put("cmd", "CMD_EXIT");
				mapToSend.put("msg", session.getId() + "님이 퇴장 했습니다.");

				String jsonStr = objectMapper.writeValueAsString(mapToSend);
				sess.sendMessage(new TextMessage(jsonStr));
			}
		}
	}
}
