package com.ar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/")
	public ResponseEntity<String> HomeController()
	{
		return new ResponseEntity<String>("welcome to our whatsapp api using spring boot ",HttpStatus.OK);
	}
}
