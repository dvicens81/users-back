package com.users.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.users.crud.dto.UserDTO;
import com.users.crud.entity.User;
import com.users.crud.error.MethodArgumentNotValid;
import com.users.crud.mapper.IMapper;
import com.users.crud.repository.IUserRepository;
import com.users.crud.utils.ValidateFields;

@Service
public class UserServiceImpl implements IUserService {
	
	private IUserRepository userRepository;
	private IMapper<User, UserDTO> mapperEntityToDto;
	private IMapper<UserDTO, User> mapperDtoToEntity;
	private ValidateFields validate;
	
	@Autowired
	public UserServiceImpl(IUserRepository userRepository, IMapper<User, UserDTO> mapperEntityToDto,
							IMapper<UserDTO, User> mapperDtoToEntity, ValidateFields validate) {
		this.userRepository = userRepository;
		this.mapperEntityToDto = mapperEntityToDto;
		this.mapperDtoToEntity = mapperDtoToEntity;
		this.validate = validate;
	}

	@Override
	public Page<UserDTO> findAllUsers() {		
		List<UserDTO> lUsersMapped = mapperEntityToDto.convertListEntityToListDto(userRepository.findAll(), UserDTO.class);
		return new PageImpl<UserDTO>(lUsersMapped, Pageable.unpaged(), lUsersMapped.size());
	}

	@Override
	public UserDTO findUserById(long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) return null;
		return mapperEntityToDto.convertEntityToDto(user.get(), UserDTO.class);
	}

	@Override
	public UserDTO saveUser(UserDTO userDTO) throws MethodArgumentNotValid {
		//Validate Fields
		validate.validateFields(userDTO);
		//OK
		User newUser = userRepository.save(mapperDtoToEntity.convertEntityToDto(userDTO, User.class));
		return mapperEntityToDto.convertEntityToDto(newUser, UserDTO.class);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO) throws MethodArgumentNotValid {
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
