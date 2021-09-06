package ru.krylov.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.krylov.web.dao.RoleDAO;
import ru.krylov.web.model.Role;
import ru.krylov.web.service.RoleService;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	private final RoleDAO roleDAO;

	public RoleServiceImpl(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	@Override
	@Transactional(readOnly = true)
	public Role getRoleByName(String name) {
		return roleDAO.getRoleByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Role getRoleById(int id) {
		return roleDAO.getRoleById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> allRoles() {
		return roleDAO.allRoles();
	}

	@Override
	@Transactional(readOnly = true)
	public Role getDefaultRole() {
		return roleDAO.getDefaultRole();
	}
}
