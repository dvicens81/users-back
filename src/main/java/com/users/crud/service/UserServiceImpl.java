package com.users.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.users.crud.dto.UserDTO;
import com.users.crud.entity.User;
import com.users.crud.mapper.IMapper;
import com.users.crud.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {
	
	private IUserRepository userRepository;
	private IMapper<User, UserDTO> mapperEntityToDto;
	private IMapper<UserDTO, User> mapperDtoToEntity;
	
	@Autowired
	public UserServiceImpl(IUserRepository userRepository, IMapper<User, UserDTO> mapperEntityToDto,
							IMapper<UserDTO, User> mapperDtoToEntity) {
		this.userRepository = userRepository;
		this.mapperEntityToDto = mapperEntityToDto;
		this.mapperDtoToEntity = mapperDtoToEntity;
	}

	@Override
	public List<UserDTO> findAllUsers() {
		return mapperEntityToDto.convertListEntityToListDto(userRepository.findAll(), UserDTO.class);
	}

	@Override
	public UserDTO findUserById(long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) return null;
		return mapperEntityToDto.convertEntityToDto(user.get(), UserDTO.class);
	}

	@Override
	public UserDTO saveUser(UserDTO userDTO) {
		User newUser = userRepository.save(mapperDtoToEntity.convertEntityToDto(userDTO, User.class));
		return mapperEntityToDto.convertEntityToDto(newUser, UserDTO.class);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO) {
		return saveUser(userDTO);
	}

	@Override
	public boolean deleteUser(long id) {
		try {
			userRepository.deleteById(id);
			return true;
		}catch(EmptyResultDataAccessException ex) {
			return false;
		}			
	}

}
