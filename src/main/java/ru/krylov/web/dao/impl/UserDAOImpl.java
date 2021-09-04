package ru.krylov.web.dao.impl;

import org.springframework.stereotype.Repository;
import ru.krylov.web.dao.UserDAO;
import ru.krylov.web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<User> allUsers() {
		return entityManager.createQuery("select user from User user", User.class).getResultList();
	}

	@Override
	public void add(User user) {
		entityManager.persist(user);
	}

	@Override
	public void delete(int id) {
		entityManager.remove(getById(id));
	}

	@Override
	public void edit(User user) {
		entityManager.merge(user);
	}

	@Override
	public User getById(int id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public User getByUsername(String username) {
		return entityManager.createQuery("select user from User user where user.username=:us", User.class)
				.setParameter("us", username).getSingleResult();
	}

}
