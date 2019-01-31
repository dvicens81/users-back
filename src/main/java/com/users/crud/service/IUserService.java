package com.users.crud.service;

import org.springframework.data.domain.Page;

import com.users.crud.dto.UserDTO;
import com.users.crud.error.MethodArgumentNotValid;

public interface IUserService {
	
	Page<UserDTO> findAllUsers();
	UserDTO findUserById(long id);
	UserDTO saveUser(UserDTO userDTO) throws MethodArgumentNotValid;
	UserDTO updateUser(UserDTO userDTO) throws MethodArgumentNotValid;
	boolean deleteUser(long id);
}
