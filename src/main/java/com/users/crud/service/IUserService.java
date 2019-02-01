package com.users.crud.service;

import org.springframework.data.domain.Page;

import com.users.crud.dto.UserDTO;
import com.users.crud.error.ArgumentNotValidException;

public interface IUserService {
	
	Page<UserDTO> findAllUsers();
	UserDTO findUserById(long id);
	UserDTO saveUser(UserDTO userDTO) throws ArgumentNotValidException;
	UserDTO updateUser(UserDTO userDTO) throws ArgumentNotValidException;
	boolean deleteUser(long id);
}
