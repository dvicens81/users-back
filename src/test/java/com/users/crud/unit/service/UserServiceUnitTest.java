package com.users.crud.unit.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;

import com.users.crud.dto.UserDTO;
import com.users.crud.entity.User;
import com.users.crud.error.ArgumentNotValidException;
import com.users.crud.mapper.IMapper;
import com.users.crud.repository.IUserRepository;
import com.users.crud.service.IUserService;
import com.users.crud.service.UserServiceImpl;
import com.users.crud.utils.ValidateFields;

public class UserServiceUnitTest {
	
	private IUserRepository userRepository;
	private IUserService userService;
	private IMapper<User, UserDTO> mapperEntityToDto;
	private IMapper<UserDTO, User> mapperDtoToEntity;
	private ValidateFields validate;
	
	private User user1;
	private User user2;
	private User user3;
	
	private UserDTO userDto;
	
	private List<User> lUsers;
	
	@SuppressWarnings("unchecked")
	@BeforeEach
	public void setUp() {
		userRepository = Mockito.mock(IUserRepository.class);
		mapperEntityToDto = Mockito.mock(IMapper.class);
		mapperDtoToEntity = Mockito.mock(IMapper.class);
		validate = Mockito.mock(ValidateFields.class);
		userService = new UserServiceImpl(userRepository,mapperEntityToDto,mapperDtoToEntity,validate);
		user1 = new User();
		user1.setId(1);
		user1.setName("Jaume");
		user2 = new User();
		user2.setId(2);
		user2.setName("Jaume 2");
		user3 = new User();
		user3.setId(3);
		user3.setName("Jaum 3");
		
		userDto = new UserDTO();
		userDto.setId(1);
		userDto.setName("Jaume");
		
		lUsers = new ArrayList<User>();
		lUsers.add(user1);
	}
	
	@Test
	public void findAllUsers() {
		Mockito.when(userRepository.findAll()).thenReturn(lUsers);
		Mockito.when(mapperEntityToDto.convertListEntityToListDto(lUsers, UserDTO.class)).thenReturn(Arrays.asList(userDto));
		Page<UserDTO> listUsers = userService.findAllUsers();
		
		assertNotNull(listUsers);
		assertEquals(Arrays.asList(userDto), listUsers.getContent());
	}
	
	@Test
	public void findUserById() {
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user1));
		Mockito.when(mapperEntityToDto.convertEntityToDto(user1, UserDTO.class)).thenReturn(userDto);
		UserDTO returnUser = userService.findUserById(1);
		
		assertNotNull(returnUser);
		assertEquals(userDto, returnUser);
	}
	
	@Test
	public void saveUser() throws ArgumentNotValidException {
		User u = new User();
		u.setName("Jaume");
		UserDTO uDto = new UserDTO();
		uDto.setName("Jaume");
		Mockito.when(userRepository.save(u)).thenReturn(user2);
		Mockito.when(mapperDtoToEntity.convertEntityToDto(uDto, User.class)).thenReturn(u);
		Mockito.when(mapperEntityToDto.convertEntityToDto(user2, UserDTO.class)).thenReturn(userDto);
		UserDTO returnUser = userService.saveUser(uDto);
		
		assertNotNull(returnUser);
		assertEquals(userDto.getId(), 1);
	}
	
	@Test
	public void updateUser() throws ArgumentNotValidException{
		Mockito.when(userRepository.save(user1)).thenReturn(user1);
		Mockito.when(mapperDtoToEntity.convertEntityToDto(userDto, User.class)).thenReturn(user1);
		Mockito.when(mapperEntityToDto.convertEntityToDto(user1, UserDTO.class)).thenReturn(userDto);
		UserDTO returnUser = userService.saveUser(userDto);
		
		assertNotNull(returnUser);
		assertEquals(userDto.getId(), 1);
	}
	
	@Test
	public void deleteUser() {
		boolean isDeleted = userService.deleteUser(1);
		
		assertEquals(isDeleted, true);
	}
	

}
