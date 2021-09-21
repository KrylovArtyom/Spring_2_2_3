package ru.krylov.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.krylov.web.model.Role;
import ru.krylov.web.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/admin/roles")
public class AdminRoleRestController {

	private final RoleService roleService;

	public AdminRoleRestController(RoleService roleService) {
		this.roleService = roleService;
	}

	@GetMapping
	public ResponseEntity<List<Role>> getAll() {
		return new ResponseEntity<>(roleService.allRoles(), HttpStatus.OK);
	}

	@GetMapping("/{roleId}")
	public ResponseEntity<Role> getRole(@PathVariable Integer roleId) {

		if (roleId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Role user = roleService.getRoleById(roleId);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(roleService.getRoleById(roleId), HttpStatus.OK);
	}
}
