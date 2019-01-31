package com.users.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.users.crud.dto.UserDTO;
import com.users.crud.error.MethodArgumentNotValid;
import com.users.crud.service.IUserService;

@RequestMapping("/api/crud")
@RestController
public class UserController {
	
	private IUserService userService;
	
	@Autowired
	public UserController(IUserService userService){
		this.userService = userService;
	}
	
	@GetMapping(value="/users")
	public ResponseEntity<Page<UserDTO>> findAllUsers(){
		return new ResponseEntity<Page<UserDTO>>(userService.findAllUsers(), HttpStatus.OK);
	}
	
	@GetMapping(value="/users/{id}")
	public ResponseEntity<UserDTO> findUserById(@PathVariable long id){
		HttpStatus statusCode = HttpStatus.OK;
		UserDTO userDTO = userService.findUserById(id);
		if (userDTO == null) statusCode = HttpStatus.NOT_FOUND;
		return new ResponseEntity<UserDTO>(userDTO, statusCode);
	}
	
	@PostMapping(value="/users")
	@ExceptionHandler(MethodArgumentNotValid.class)
	public ResponseEntity<?> saveUser(@RequestBody UserDTO userDto){		
		return new ResponseEntity<UserDTO>(userService.saveUser(userDto), HttpStatus.OK);	
		
	}
	
	@PutMapping(value="/users/{id}")
	@ExceptionHandler(MethodArgumentNotValid.class)
	public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UserDTO userDto){
		if (userDto.getId() == 0) userDto.setId(id);		
		return new ResponseEntity<UserDTO>(userService.updateUser(userDto), HttpStatus.OK);
		
	}
	
	@DeleteMapping(value="/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable long id){
		HttpStatus statusCode = HttpStatus.OK;
		if(!userService.deleteUser(id)) statusCode = HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(statusCode);
	}

}
