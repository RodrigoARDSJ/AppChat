package com.br.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.br.chat.model.ChatMessage;

@Controller
public class ChatController {

	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload final ChatMessage chatMessage) {
		return chatMessage;
	}

	@MessageMapping("/chat.addUser")
	@SendTo("/topic/public")
	public ChatMessage newUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccerssor) {
		headerAccerssor.getSessionAttributes().put("username", chatMessage.getSender());
		return chatMessage;

	}
}
