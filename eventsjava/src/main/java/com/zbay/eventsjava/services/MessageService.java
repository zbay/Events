package com.zbay.eventsjava.services;

import org.springframework.stereotype.Service;

import com.zbay.eventsjava.models.Message;
import com.zbay.eventsjava.repositories.MessageRepository;

@Service
public class MessageService {
	private MessageRepository messageRepository;
	
	public MessageService(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	public void leaveMessage(Message message) {
		this.messageRepository.save(message);
	}
}
