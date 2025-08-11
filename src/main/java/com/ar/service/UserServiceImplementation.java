package com.ar.service;

import java.util.List;
import java.util.Optional;

import com.ar.config.TokenProvider;
import com.ar.exception.UserException;
import com.ar.model.User;
import com.ar.repository.UserRepository;
import com.ar.request.UpdateUserRequest;

public class UserServiceImplementation implements UserService{
	
	
	private UserRepository userRepository;
	private TokenProvider tokenProvider;
	
	public UserServiceImplementation(UserRepository userRepository, TokenProvider tokenProvider)
	{
		this.userRepository=userRepository;
		this.tokenProvider= tokenProvider;
	}

	@Override
	public User findUserById(Integer id) throws UserException {
		Optional<User>  opt=userRepository.findById(id);
		if(opt.isPresent())
		{
		return opt.get();
		}
		throw new UserException("User not found with id " +id);
	}

	@Override
	public User findUserProfile(String jwt) throws UserException {
	 
		String email=tokenProvider.getEmailFormToken(jwt);
		
		if(email == null)
		{
			throw   new UserException("recieved invalid token----");
		}
		User user=userRepository.findByEmail(email);
		
		if(user ==null)
		{
			throw new UserException("user not found with email"+email);
		}
		return user;
	}

	@Override
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
		 User user =findUserById(userId);
		 
		 if(req.getFull_name() != null )
		 {
			 user.setFull_name(req.getFull_name());
		 }
		 if(req.getProfile_picture() != null)
		 {
			 user.setProfile_picture(req.getProfile_picture());
		 }
		 
		return userRepository.save(user);
	}

	@Override
	public List<User> searchUser(String query) {
	 List<User> users=userRepository.searchUser(query);
		return users;
	}

}
