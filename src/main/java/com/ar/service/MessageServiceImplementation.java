package com.ar.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ar.exception.ChatException;
import com.ar.exception.MessageException;
import com.ar.exception.UserException;
import com.ar.model.Chat;
import com.ar.model.Message;
import com.ar.model.User;
import com.ar.repository.MessageRepository;
import com.ar.request.SendMessageRequest;

@Service
public class MessageServiceImplementation implements MessageService
{
	private MessageRepository messageRepository;
	private UserService userService;
	private ChatService chatService;
	
	

	public MessageServiceImplementation(MessageRepository messageRepository, UserService userService,
			ChatService chatService) {
		super();
		this.messageRepository = messageRepository;
		this.userService = userService;
		this.chatService = chatService;
	}

	@Override
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
		 User user =userService.findUserById(req.getUserId());
		 Chat chat=chatService.findChatById(req.getChatId());
		 
		 Message message=new Message();
		 message.setChat(chat);
		 message.setUser(user);
		 message.setContent(req.getContent());
		 message.setTimestamp(LocalDateTime.now());
		return message;
	}

	@Override
	public List<Message>getChatsMessage(Integer chatId, User reqUser) throws ChatException, UserException {
		 Chat chat =chatService.findChatById(chatId);
		 if(!chat.getUsers().contains(reqUser))
		 {
			 throw new UserException("you are not releted to this chat "+chat.getId());
		 }
		 List<Message> messages=messageRepository.findByChatId(chat.getId());
		 return messages;
 	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageException {
		 Optional<Message> opt=messageRepository.findById(messageId);
		 
		 if(opt.isPresent())
		 {
			 return opt.get();
		 }
		throw new MessageException("Message not found with id "+messageId);
	}

	@Override
	public void deleteMessage(Integer messageId, User reqUser) throws MessageException {
	
		Message message=findMessageById(messageId);
		
		if(message.getUser().getId().equals(reqUser.getId()))
		{
			messageRepository.deleteById(messageId);
		}
		
	}

}
