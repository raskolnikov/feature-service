package com.mettle.feature.service;

import com.mettle.feature.db.Feature;
import com.mettle.feature.db.Permission;
import com.mettle.feature.db.User;
import com.mettle.feature.db.enums.Status;
import com.mettle.feature.exception.NotFoundException;
import com.mettle.feature.repository.PermissionRepository;
import com.mettle.feature.request.NewPermissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Mehmet Aktas on 2021-12-19
 * <p>
 * This service class will be doing creation permissions and retrieving permission detail.
 */

@Service
public class PermissionService {

	private final PermissionRepository permissionRepository;

	@Autowired
	public PermissionService(PermissionRepository permissionRepository) {

		this.permissionRepository = permissionRepository;
	}

	/**
	 * This method will create new permission.
	 *
	 * @param newPermissionRequest
	 * 		new permission details will be in here
	 * @return created permission detail
	 */
	@Transactional
	public Permission createPermission(NewPermissionRequest newPermissionRequest) {

		Permission permission = new Permission();

		permission.setName(newPermissionRequest.getName());
		permission.setDescription(newPermissionRequest.getDescription());
		permission.setStatus(newPermissionRequest.getStatus());

		User user = new User();
		user.setId(newPermissionRequest.getUserId());
		permission.setUser(user);

		Feature feature = new Feature();
		feature.setId(newPermissionRequest.getFeatureId());

		permission.setFeature(feature);

		return permissionRepository.save(permission);

	}

	/**
	 * This method will return permission details for given id
	 *
	 * @param id
	 * 		permission id
	 * @return permission object will return
	 */
	public Permission getPermission(Long id) {

		return permissionRepository.findById(id).orElseThrow(() -> new NotFoundException("Permission not found", id));

	}


	/**
	 * This method will mark permission as deleted
	 *
	 * @param id
	 * 		permission id
	 */
	@Transactional
	public void deletePermission(long id) {

		permissionRepository.updateStatus(id, Status.DELETED);

	}

	/**
	 * This  method will retrieve permissions for given filtering params.
	 * <p>
	 *
	 * @param offset
	 * 		starting point of list
	 * @param limit
	 * 		size of returning list
	 * @return transfer list
	 */
	public Page<Permission> getPermissions(Integer offset, Integer limit) {

		return permissionRepository.findAll(PageRequest.of(offset, limit, Sort.by("createdAt").descending()));

	}

}
