package com.br.chat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.br.chat.model.ChatMessage;
import com.br.chat.model.MessageType;

@Component
public class WebSocketEventListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEventListener.class);

	@Autowired
	private SimpMessageSendingOperations sendingOperations;

	@EventListener
	public void handleWebSocketConnectListener(final SessionConnectedEvent event) {
		LOGGER.info("A user connected");
	}

	@EventListener
	public void handleWebSocketDisconnectListener(final SessionDisconnectEvent event) {
		final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		final String username = (String) headerAccessor.getSessionAttributes().get("username");
		if(username != null) {
			
			LOGGER.info("User Disconnected : " + username);
			
			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setType(MessageType.DISCONNECT);
			chatMessage.setSender(username);
			sendingOperations.convertAndSend("/topic/public", chatMessage);
			
		}


	}
}
