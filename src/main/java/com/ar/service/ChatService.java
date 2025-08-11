package com.ar.service;

import java.util.List;

import com.ar.exception.ChatException;
import com.ar.exception.UserException;
import com.ar.model.Chat;
import com.ar.model.User;

public interface ChatService 
{

	public Chat createChat(User reqUser, Integer userId2) throws UserException;
	
	public Chat findChatById(Integer chatId)throws ChatException;
	
	public List<Chat> findAllChatByUserId(Integer userId)throws UserException;
	
	
	public Chat createGroup(GroupChatRequest req, User reqUesr)throws UserException;
	
	public Chat addUserToGroup(Integer userId, Integer chatId,User reqUser)throws UserException, ChatException;
	
	public Chat renameGroup(Integer chatId, String groupName,User reqUser )throws UserException, ChatException;


	public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser)throws UserException, ChatException;

	public void deleteChat(Integer chatId, Integer useId)throws UserException, ChatException;
	
}
