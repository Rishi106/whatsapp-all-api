package com.ar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ar.exception.UserException;
import com.ar.model.User;
import com.ar.request.UpdateUserRequest;
import com.ar.response.ApiResponse;
import com.ar.service.UserService;

@RestController
 public class UserController {

	private UserService userService;
	
	public UserController(UserService userService)
	{
		this.userService=userService;
	}
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String token) throws UserException
	{
		User user=userService.findUserProfile(token);
		return new ResponseEntity<User> (user, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/query")
	public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String q) throws UserException
	{
		List<User> users=userService.searchUser(q);
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest req, @RequestHeader("Authorization") String token) throws UserException
	{
		User user=userService.findUserProfile(token);
		userService.updateUser(user.getId(), req);
		
		ApiResponse res=new ApiResponse("user update seccessfully",true);
		return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);
		
	}


}

