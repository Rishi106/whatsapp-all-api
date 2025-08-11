package com.ar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ar.exception.ChatException;
import com.ar.exception.UserException;
import com.ar.model.Chat;
import com.ar.model.User;
import com.ar.repository.ChatRepository;

@Service
public class ChatServiceImplemantation  implements ChatService{

	private ChatRepository chatRepository;
	private UserService userService;
	

	public ChatServiceImplemantation(ChatRepository chatRepository,UserService userService) {
		super();
		this.chatRepository = chatRepository;
		this.userService=userService;
	}

	@Override
	public Chat createChat(User reqUser, Integer userId2) throws UserException {
		 
		User user=userService.findUserById(userId2);
		Chat isChatExist=chatRepository.findSingleChatByUserIds(user, reqUser);
		if(isChatExist != null)
		{
			return isChatExist;
		}
		Chat chat=new Chat();
		chat.setCreatedBy(reqUser);
		chat.getUsers().add(user);
		chat.getUsers().add(reqUser);
		chat.setGroup(false);
		
		return chat;
	}

	@Override
	public Chat findChatById(Integer chatId) throws com.ar.exception.ChatException {
		Optional<Chat> chat=chatRepository.findById(chatId);
		if(chat.isPresent()) {
			return chat.get();
		}
		throw new ChatException("Chat not found with id "+chatId);
	}

	@Override
	public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
		 User user =userService.findUserById(userId);
		 List<Chat> chats=chatRepository.findChatByUserId(user.getId());
		return chats;
	}

	@Override
	public Chat createGroup(GroupChatRequest req, User reqUesr) throws UserException {
		 Chat group=new Chat();
		 group.setGroup(true);
		 group.setChat_image(req.getChat_image());
		 group.setChat_name(req.getChat_name());
		 group.setCreatedBy(reqUesr);
		 group.getAdmins().add(reqUesr);
		 for(Integer userId: req.getUserIds())
		 {
			 User user=userService.findUserById(userId);
			 group.getUsers().add(user);
		 }
		 return group;
	}

	@Override
	public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, com.ar.exception.ChatException {
		 
		Optional<Chat> opt=chatRepository.findById(chatId);
		
		User user=userService.findUserById(userId);
		
		if(opt.isPresent())
		{
			Chat chat=opt.get();
			if(chat.getAdmins().contains(reqUser))
			
				{
				opt.get().getUsers().add(user);
				return chatRepository.save(chat);
				}
			else
			{
				throw new UserException("You are not admin");
			}
		}
		throw new ChatException("chat not found with id "+chatId);
	 
	}

	@Override
	public Chat renameGroup(Integer chatId, String groupName, User reqUser)
			throws UserException, com.ar.exception.ChatException {
		Optional<Chat> opt=chatRepository.findById(chatId);
		if(opt.isPresent())
		{
			Chat chat=opt.get();
			if(chat.getUsers().contains(reqUser))
			{
				chat.setChat_name(groupName);
				return chatRepository.save(chat);
			}
			throw new UserException("you are not member of this group ");
		}
		
		throw new ChatException("chat not found with id "+chatId);
	}

	@Override
	public Chat removeFromGroup(Integer chatId, Integer userId,  User reqUser)
			throws UserException, com.ar.exception.ChatException {
Optional<Chat> opt=chatRepository.findById(chatId);
		
		User user=userService.findUserById(userId);
		
		if(opt.isPresent())
		{
			Chat chat=opt.get();
			if(chat.getAdmins().contains(reqUser))
			
				{
				opt.get().getUsers().remove(user);
				return chatRepository.save(chat);
				}
			else if(chat.getUsers().contains(reqUser))
			{
				if(user.getId().equals(reqUser.getId()))
				{
					opt.get().getUsers().remove(user);
					return chatRepository.save(chat);
				}
			}
			
				throw new UserException("You can't removed another user ");
			
		}
		throw new ChatException("chat not found with id "+chatId);
	 
		 
	}

	@Override
	public void deleteChat(Integer chatId, Integer useId) throws UserException, com.ar.exception.ChatException {
		Optional<Chat> opt=chatRepository.findById(chatId);
		if(opt.isPresent())
		{
			Chat chat=opt.get();
			chatRepository.deleteById(chat.getId());
		}
	}
	

}
