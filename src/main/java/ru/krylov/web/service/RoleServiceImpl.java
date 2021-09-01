package ru.krylov.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.krylov.web.dao.RoleDAO;
import ru.krylov.web.model.Role;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	private final RoleDAO roleDAO;

	@Autowired
	public RoleServiceImpl(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	@Override
	public Role getRoleByName(String name) {
		return roleDAO.getRoleByName(name);
	}

	@Override
	public Role getRoleById(Long id) {
		return roleDAO.getRoleById(id);
	}

	@Override
	public List<Role> allRoles() {
		return roleDAO.allRoles();
	}

	@Override
	public Role getDefaultRole() {
		return roleDAO.getDefaultRole();
	}
}
