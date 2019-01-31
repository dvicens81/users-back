package com.users.crud.service;

import java.util.List;

import com.users.crud.dto.UserDTO;

public interface IUserService {
	
	List<UserDTO> findAllUsers();
	UserDTO findUserById(long id);
	UserDTO saveUser(UserDTO userDTO);
	UserDTO updateUser(UserDTO userDTO);
	boolean deleteUser(long id);
}
