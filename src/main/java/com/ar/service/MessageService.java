package com.ar.service;

import java.util.List;

import com.ar.exception.ChatException;
import com.ar.exception.MessageException;
import com.ar.exception.UserException;
import com.ar.model.Message;
import com.ar.model.User;
import com.ar.request.SendMessageRequest;

public interface MessageService {
	
	public Message sendMessage(SendMessageRequest req )throws UserException, ChatException;
	
	public List<Message> getChatsMessage(Integer chatId, User reqUser )throws  UserException,ChatException;
	
	public Message findMessageById(Integer messageId )throws MessageException;
	
	public void deleteMessage(Integer messageId, User reqUser )throws MessageException;

}
