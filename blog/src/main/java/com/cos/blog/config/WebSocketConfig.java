package com.cos.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.cos.blog.handler.ChatHandler;

@Configuration //해당 클래스가 bean설정 할거라는것
@EnableWebSocket // 웹소켓 활성화
public class WebSocketConfig implements WebSocketConfigurer{

	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// "/chat"로 들어오는 요청을 chatHandler에서 처리함
		registry.addHandler(new ChatHandler(), "/chat").setAllowedOrigins("/*").withSockJS();
		//withSockJS()는 소켓지원이 안되는곳에서 대체방안??같은거
			
	} 
	

}
