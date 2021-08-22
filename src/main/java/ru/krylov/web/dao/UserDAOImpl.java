package ru.krylov.web.dao;

import org.springframework.stereotype.Repository;
import ru.krylov.web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<User> allUsers() {
		return entityManager.createQuery(new String("FROM User"), User.class).getResultList();
	}

	@Override
	public void add(User user) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		User newUser = new User(user.getName(), user.getAge(), user.getEmail(), timestamp);
		entityManager.merge(newUser);
	}

	@Override
	public void delete(int id) {
		entityManager.remove(getById(id));
	}

	@Override
	public void edit(int id, User user) {
		User updateUser = entityManager.find(User.class, id);
		updateUser.setName(user.getName());
		updateUser.setAge(user.getAge());
		updateUser.setEmail(user.getEmail());

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		updateUser.setUpdatedAt(timestamp);
	}

	@Override
	public User getById(int id) {
		return entityManager.find(User.class, id);
	}
}
