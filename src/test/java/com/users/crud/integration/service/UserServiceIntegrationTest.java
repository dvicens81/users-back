package com.users.crud.integration.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.users.crud.dto.UserDTO;
import com.users.crud.service.IUserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations= {"classpath:application-test.properties"})
public class UserServiceIntegrationTest {
	
	@Autowired
	private IUserService userService;
	
	@Test
	public void findAll() {
		Page<UserDTO> userDTO = userService.findAllUsers();
		assertNotNull(userDTO);
	}

	@Test
	public void save() {		
		UserDTO userDTO = new UserDTO();
		userDTO.setName("DTO USER");
		userDTO.setEmail("d@d.es");
		UserDTO newUserDto = userService.saveUser(userDTO);
		assertNotEquals(newUserDto.getId(), 0);
	}
	
	@Test
	public void update() {
		//First-> Save User
		UserDTO userDTO = new UserDTO();
		userDTO.setName("DTO USER");
		userDTO.setEmail("d@d.es");
		UserDTO newUserDto = userService.saveUser(userDTO);
		assertNotEquals(newUserDto.getId(), 0);
		//NOW UPDATE THE USER
		newUserDto.setName("DTO USER UPDATED");
		UserDTO updateUserDto = userService.updateUser(newUserDto);
		assertEquals(updateUserDto.getName(), "DTO USER UPDATED");
	}
	
	@Test
	public void findUserById(){
		//First-> Save User
		UserDTO userDTO = new UserDTO();
		userDTO.setName("USER BY ID");
		userDTO.setEmail("d@d.es");
		UserDTO newUserDto = userService.saveUser(userDTO);
		assertNotEquals(newUserDto.getId(), 0);
		//NOW SEARCH THE USER
		UserDTO userFindById = userService.findUserById(newUserDto.getId());
		assertNotNull(userFindById);
	}
	
	@Test
	public void findUserByIdButNotFound(){
		//First-> Save User
		UserDTO userDTO = new UserDTO();
		userDTO.setName("USER BY ID");
		userDTO.setEmail("d@d.es");
		UserDTO newUserDto = userService.saveUser(userDTO);
		assertNotEquals(newUserDto.getId(), 0);
		//NOW SEARCH THE USER
		UserDTO userFindById = userService.findUserById(newUserDto.getId());
		assertNotNull(userFindById);
		//DELETE USER
		userService.deleteUser(newUserDto.getId());
		assertThrows(NoSuchElementException.class, () -> {
			userService.findUserById(newUserDto.getId());
	    });	
	}
	
	@Test
	public void deleteUser() {
		//First-> Save User
		UserDTO userDTO = new UserDTO();
		userDTO.setName("USER BY ID");
		userDTO.setEmail("d@d.es");
		UserDTO newUserDto = userService.saveUser(userDTO);
		assertNotEquals(newUserDto.getId(), 0);
		userService.deleteUser(newUserDto.getId());
		//FIND USER DELETED
		assertThrows(NoSuchElementException.class, () -> {
			userService.findUserById(newUserDto.getId());
	    });
	}
}
