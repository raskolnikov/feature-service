package com.mettle.feature.mapper;

import com.mettle.feature.db.Feature;
import com.mettle.feature.db.Permission;
import com.mettle.feature.response.FeatureDto;
import com.mettle.feature.response.PermissionDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Created by Mehmet Aktas on 2021-12-19
 */

@Mapper(componentModel = "spring")
public interface PermissionMapper {

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	public PermissionDto convertToPermissionDto(Permission permission);
}
