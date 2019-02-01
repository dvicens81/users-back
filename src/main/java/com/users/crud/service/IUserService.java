package com.users.crud.service;

import org.springframework.data.domain.Page;

import com.users.crud.dto.UserDTO;

public interface IUserService {
	
	Page<UserDTO> findAllUsers();
	UserDTO findUserById(long id);
	UserDTO saveUser(UserDTO userDTO);
	UserDTO updateUser(UserDTO userDTO);
	boolean deleteUser(long id);
}
