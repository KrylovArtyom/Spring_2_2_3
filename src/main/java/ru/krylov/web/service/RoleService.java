package ru.krylov.web.service;

import ru.krylov.web.model.Role;

import java.util.List;

public interface RoleService {

	Role getRoleByName(String name);

	Role getRoleById(int id);

	List<Role> allRoles();

	Role getDefaultRole();
}
