package com.users.crud.integration.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.crud.dto.UserDTO;
import com.users.crud.service.IUserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations= {"classpath:application-test.properties"})
public class UserControllerIntegrationTest {
	
	@Autowired
	private WebApplicationContext wap;
	private MockMvc mockMvc;
	
	private List<UserDTO> lUsers;
	private UserDTO user1;
	private UserDTO user2;
	private UserDTO user3;
	private ObjectMapper mapper;

	
	@MockBean
	private IUserService userService;
	
	@BeforeEach
	public void setUp() {
		mapper = new ObjectMapper();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
		user1 = new UserDTO();
		user1.setId(23);
		user1.setName("Carol");
		user1.setEmail("c@c.es");
		user2 = new UserDTO();
		user2.setId(27);
		user1.setName("Ramón");
		user2.setEmail("c@c.es");
		user3 = new UserDTO();
		user3.setId(30);
		user3.setName("Óscar");
		user3.setEmail("c@c.es");
		
		lUsers = new ArrayList<UserDTO>();
		lUsers.add(user1);
		lUsers.add(user2);		
	}
	
	@Test
	public void findAllUsers() throws Exception {
		when(userService.findAllUsers()).thenReturn(new PageImpl<UserDTO>(lUsers));
		
		mockMvc.perform(get("/api/crud/users").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content.[0].id", is(23)));
	}
	
	@Test
	public void findUserById() throws Exception {
		when(userService.findUserById(23)).thenReturn(user1);
		
		mockMvc.perform(get("/api/crud/users/23").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(23)));
	}
	
	@Test
	public void findUserByIdButNotFound() throws Exception {
		when(userService.findUserById(2)).thenReturn(null);
		
		mockMvc.perform(get("/api/crud/users/23").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void saveUser() throws Exception {
		UserDTO user = new UserDTO();
		user.setName("Óscar");
		user.setEmail("c@c.es");		
	
		when(userService.saveUser(user)).thenReturn(user3);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/crud/users")
				.accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void updateUser() throws Exception {
		UserDTO user = new UserDTO();
		user.setId(30);
		user.setName("Óscar 2");
		user.setEmail("c@c.es");
		when(userService.saveUser(user)).thenReturn(user);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/crud/users/30")
				.accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void deleteUserById() throws Exception {
		when(userService.deleteUser(2)).thenReturn(true);
		
		mockMvc.perform(delete("/api/crud/users/2").contentType(MediaType.APPLICATION_JSON))		
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteUserByIdButNotFound() throws Exception {
		when(userService.deleteUser(22)).thenReturn(false);
		
		mockMvc.perform(delete("/api/crud/users/22").contentType(MediaType.APPLICATION_JSON))		
			.andExpect(status().isNotFound());
	}

}
