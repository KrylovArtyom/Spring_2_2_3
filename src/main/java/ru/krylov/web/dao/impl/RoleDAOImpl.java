package ru.krylov.web.dao.impl;

import org.springframework.stereotype.Repository;
import ru.krylov.web.dao.RoleDAO;
import ru.krylov.web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Role getRoleByName(String name) {
		return entityManager.createQuery("select role from Role role where role.name=:name", Role.class)
				.setParameter("name", name)
				.getSingleResult();
	}

	@Override
	public Role getRoleById(int id) {
		return entityManager.find(Role.class, id);
	}

	@Override
	public List<Role> allRoles() {
		return entityManager.createQuery("select role from Role role", Role.class).getResultList();
	}

	@Override
	public Role getDefaultRole() {
		return getRoleByName("ROLE_USER");
	}
}
