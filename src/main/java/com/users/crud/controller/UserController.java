package com.users.crud.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.users.crud.dto.UserDTO;
import com.users.crud.service.IUserService;

@RequestMapping("/api/crud")
@RestController
public class UserController{
	
	private IUserService userService;
	
	@Autowired
	public UserController(IUserService userService){
		this.userService = userService;
	}
	
	@GetMapping(value="/users")
	public ResponseEntity<Page<UserDTO>> findAllUsers(){
		return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
	}
	
	@GetMapping(value="/users/{id}")
	public ResponseEntity<UserDTO> findUserById(@PathVariable long id){
		return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
	}
	
	@PostMapping(value="/users")
	public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO userDto){		
		return new ResponseEntity<>(userService.saveUser(userDto), HttpStatus.OK);
	}
	
	@PutMapping(value="/users/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable long id, @Valid @RequestBody UserDTO userDto){
		if (userDto.getId() == 0) userDto.setId(id);		
		return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/users/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable long id){
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
