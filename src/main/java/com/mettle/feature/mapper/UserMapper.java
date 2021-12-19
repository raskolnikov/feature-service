package com.mettle.feature.mapper;

import com.mettle.feature.db.User;
import com.mettle.feature.response.UserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Created by Mehmet Aktas on 2021-12-19
 */

@Mapper(componentModel = "spring")
public interface UserMapper {

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	public UserDto convertToUserDto(User user);
}
