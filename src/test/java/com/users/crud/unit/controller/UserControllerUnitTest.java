package com.users.crud.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.users.crud.controller.UserController;
import com.users.crud.dto.UserDTO;
import com.users.crud.service.IUserService;

public class UserControllerUnitTest {
	
	private UserController userController;
	private IUserService userService;
	
	private Page<UserDTO> items;
	private UserDTO user1;
	private UserDTO user2;
	private UserDTO user3;
		
	
	@BeforeEach
	public void setUp() {
		userService = Mockito.mock(IUserService.class);
		userController = new UserController(userService);
		user1 = new UserDTO();
		user1.setId(23);
		user1.setName("Carol");
		user2 = new UserDTO();
		user2.setId(27);
		user1.setName("Ramón");
		user3 = new UserDTO();
		user3.setId(30);
		user3.setName("Óscar");
		
		items = new PageImpl<UserDTO>(Arrays.asList(user1, user2, user3), Pageable.unpaged(), 3);
	}
	
	@Test
	public void findAllUsers() {
		
		Mockito.when(userService.findAllUsers()).thenReturn(items);
		ResponseEntity<Page<UserDTO>> httpResponse = userController.findAllUsers();

        assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(items, httpResponse.getBody());
	}
	
	@Test
	public void findUserById() {
		long idUser = 27;
		Mockito.when(userService.findUserById(idUser)).thenReturn(user2);
		ResponseEntity<UserDTO> httpResponse = userController.findUserById(idUser);

        assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(user2, httpResponse.getBody());
	}
	
	@Test
	public void saveUser() {
		UserDTO userWithoutId = new UserDTO();
		userWithoutId.setName("Ramón");
		Mockito.when(userService.saveUser(userWithoutId)).thenReturn(user3);
		ResponseEntity<?> httpResponse = userController.saveUser(userWithoutId);

        assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(user3, httpResponse.getBody());
	}
	
	@Test
	public void updateUser() {
		UserDTO userUpdated = new UserDTO();
		userUpdated.setId(30);
		userUpdated.setName("Oscar updated");
		Mockito.when(userService.saveUser(user3)).thenReturn(userUpdated);
		ResponseEntity<?> httpResponse = userController.saveUser(user3);

        assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(userUpdated, httpResponse.getBody());
	}
	
	@Test
	public void deleteUser() {
		
		ResponseEntity<?> httpResponse = userController.deleteUser(3);

        assertEquals(httpResponse.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void deleteUserNotFound() {
		doThrow(EmptyResultDataAccessException.class).when(userService).deleteUser(1);
		assertThrows(EmptyResultDataAccessException.class, () -> {
			userController.deleteUser(1);
	    });
	}

}
