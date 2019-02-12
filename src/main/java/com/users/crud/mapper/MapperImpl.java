package com.users.crud.mapper;

import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperImpl<E,T> implements IMapper<E, T> {
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<T> convertListEntityToListDto(List<E> listItems, Class<T> nameClass) {
		return getListDtos(listItems, nameClass);
	}
	
	private List<T> getListDtos(List<E> listItems, Class<T> nameClass){
		List<T> itemsDto = new LinkedList<>();
		for (E item : listItems) {			
			itemsDto.add(convertEntityToDto(item, nameClass));
		}
		return itemsDto;
	}

	@Override
	public T convertEntityToDto(E item, Class<T> nameClass) {
		return modelMapper.map(item, nameClass);
	}
	
}
