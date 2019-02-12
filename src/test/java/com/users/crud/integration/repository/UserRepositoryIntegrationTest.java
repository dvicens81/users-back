package com.users.crud.integration.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.users.crud.entity.User;
import com.users.crud.repository.IUserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations= {"classpath:application-test.properties"})
public class UserRepositoryIntegrationTest {
	
	@Autowired
	private IUserRepository userRepository;
		
	
	@Test
	public void findAllUsers() {
		List<User> users = userRepository.findAll();
		assertNotNull(users);
		assertEquals(users.isEmpty(), false);
	}
	
	@Test	
	public void findUserById() {
		Optional<User> searchUser = userRepository.findById(1l);
		if (searchUser.isPresent())
			assertNotNull(searchUser.get());
		else
			assertEquals(searchUser.isPresent(), false);
	}
	
	@Test
	public void updateUser() {
		//First Save user
		User user = new User();
		user.setName("User");
		user.setEmail("c@c.es");
		User newUser = userRepository.save(user);
		assertNotNull(newUser);
		assertNotSame(newUser.getId(), 0);
		newUser.setName("Update User");
		newUser = userRepository.save(newUser);
		assertEquals(newUser.getName(), "Update User");
	}
	
	@Test
	public void saveUser() {
		User user = new User();
		user.setName("Testing 3");
		user.setEmail("c@c.es");
		User newUser = userRepository.save(user);
		assertNotNull(newUser);
		assertNotSame(newUser.getId(), 0);
	}
	
	@Test
	public void deleteUser() {
		//First Save user
		User user = new User();
		user.setName("Delete User");
		user.setEmail("c@c.es");
		User newUser = userRepository.save(user);
		assertNotNull(newUser);
		assertNotSame(newUser.getId(), 0);
		//delete user
		userRepository.deleteById(newUser.getId());
		//search user
		Optional<User> userById = userRepository.findById(newUser.getId());
		assertEquals(userById.isPresent(), false);
	}
}
