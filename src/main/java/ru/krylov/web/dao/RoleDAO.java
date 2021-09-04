package ru.krylov.web.dao;

import ru.krylov.web.model.Role;

import java.util.List;

public interface RoleDAO {

	Role getRoleByName(String name);

	Role getRoleById(int id);

	List<Role> allRoles();

	Role getDefaultRole();
}
