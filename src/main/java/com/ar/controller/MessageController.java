package com.ar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ar.exception.ChatException;
import com.ar.exception.MessageException;
import com.ar.exception.UserException;
import com.ar.model.Message;
import com.ar.model.User;
import com.ar.request.SendMessageRequest;
import com.ar.response.ApiResponse;
import com.ar.service.MessageService;
import com.ar.service.UserService;

@RestController
 public class MessageController {

	private MessageService messageService;
	private UserService userService;
	
	public MessageController(MessageService messageService, UserService userService) {
		super();
		this.messageService = messageService;
		this.userService = userService;
	}
	@PostMapping("/create")
	public ResponseEntity<Message>sendMessageHandler(@RequestBody SendMessageRequest req, @RequestHeader("Authorization")String jwt) throws UserException, ChatException
	{
		User user =userService.findUserProfile(jwt);
		
		req.setUserId(user.getId());
		Message  message=messageService.sendMessage(req);
		return new ResponseEntity<Message>(message,HttpStatus.OK);
	}
	
	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<Message>>getChatMessageHandler(@PathVariable Integer chatId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException
	{
		User user =userService.findUserProfile(jwt);
		
	 
		List<Message> messages=messageService.getChatsMessage(null, user);
		return new ResponseEntity<List<Message>>(messages,HttpStatus.OK);
	}
	@DeleteMapping("/{messageId}")
	public ResponseEntity<ApiResponse>deleteMessageHandler(@PathVariable Integer messageId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException, MessageException
	{
		User user =userService.findUserProfile(jwt);
		
	 messageService.deleteMessage(messageId, user);
	 ApiResponse res=new ApiResponse("message deleted successfully..",true);
	 
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	
}
