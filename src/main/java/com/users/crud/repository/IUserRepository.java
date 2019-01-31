package com.users.crud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.users.crud.entity.User;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {

	List<User> findAll();
	Optional<User> findById(long id);
	@SuppressWarnings("unchecked")
	User save (User user);
	void deleteById(long id) throws EmptyResultDataAccessException;
	
}
