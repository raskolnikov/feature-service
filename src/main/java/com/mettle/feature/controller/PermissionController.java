package com.mettle.feature.controller;

import com.mettle.feature.db.Feature;
import com.mettle.feature.db.Permission;
import com.mettle.feature.mapper.PermissionMapper;
import com.mettle.feature.request.NewPermissionRequest;
import com.mettle.feature.response.FeatureDto;
import com.mettle.feature.response.PermissionDto;
import com.mettle.feature.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mehmet Aktas on 2021-12-19
 * <p>
 * This API Controller will be responsible for accepting permission CRUD or related operations
 */

@RestController
@RequestMapping("/api/v1/admin/permissions")
public class PermissionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);

	private final PermissionService permissionService;
	private final PermissionMapper permissionMapper;

	@Autowired
	public PermissionController(PermissionService permissionService, PermissionMapper permissionMapper) {
		this.permissionService = permissionService;
		this.permissionMapper = permissionMapper;
	}


	/**
	 * This API method will return permissions.
	 * <p>
	 * Returning list will contain all permissions
	 *
	 * @param offset
	 * 		starting point of list
	 * @param limit
	 * 		size of returning list
	 * @return transfer list
	 */
	@GetMapping(value = "/", produces = "application/json")
	public List<PermissionDto> getPermissions(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
			@RequestParam(value = "limit", defaultValue = "25") Integer limit) {

		Page<Permission> featureList = permissionService.getPermissions(offset, limit);

		return featureList.get().map(permissionMapper::convertToPermissionDto).collect(Collectors.toList());

	}


	/**
	 * This API method will create new permission
	 *
	 * @param newPermissionRequest
	 * 		new permission details will be in here
	 * @return created permission detail
	 */
	@PostMapping(value = "/", consumes = "application/json", produces = "application/json")
	public PermissionDto createPermission(@Valid @RequestBody NewPermissionRequest newPermissionRequest) {

		Permission permission = permissionService.createPermission(newPermissionRequest);

		return permissionMapper.convertToPermissionDto(permission);

	}

	/**
	 * This API method will retrieve permission detail for given @param id
	 *
	 * @param id
	 * 		permission id
	 * @return permission details
	 */
	@GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
	public PermissionDto getPermission(@PathVariable("id") long id) {

		Permission permission = permissionService.getPermission(id);

		return permissionMapper.convertToPermissionDto(permission);

	}

	/**
	 * This API method will delete permission for given @param id
	 *
	 * @param id
	 * 		permission id
	 * @return permission details
	 */
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public String deletePermission(@PathVariable("id") long id) {

		permissionService.deletePermission(id);

		return String.format("Permission %s has been deleted", id);
	}

}
