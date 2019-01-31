package com.users.crud.mapper;

import java.util.List;

public interface IMapper<E, T> {

	List<T> convertListEntityToListDto(List<E> listItems, Class<T> nameClass);
	T convertEntityToDto(E item, Class<T> nameClass);
}
