package ru.krylov.web.model;



import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

//	@NotEmpty(message = "Name should not be empty")
//	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters!")
	private String name;

//	@Min(value = 1, message = "Age should not be less than 1")
//	@Max(value = 100, message = "Age should not be greater than 100")
	private byte age;

//	@NotEmpty(message = "Email should not be empty")
//	@Email(message = "Email should be valid")
	private String email;

	private Timestamp createdAt;
	private Timestamp updatedAt;

	public User() {

	}

	public User(String name, int age, String email) {
		this.id = id;
		this.name = name;
		this.age = (byte) age;
		this.email = email;
	}

	public User(String name, byte age, String email, Timestamp createdAt) {
		this.name = name;
		this.age = age;
		this.email = email;
		this.createdAt = createdAt;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getAge() {
		return age;
	}

	public void setAge(byte age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public int hashCode() {
		int result = 37;
		result = 37 * result + id;
		result = 37 * result + ((name == null) ? 0 : name.hashCode());
		result = 37 * result + ((email == null) ? 0 : email.hashCode());
		result = 37 * result + (int) age;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		User other = (User) obj;
		return (id == other.id) &&
				name.equals(other.name) &&
				(age == other.age) &&
				email.equals(other.email);
	}

	@Override
	public String toString() {
		return "User [" +
				"id: " + id +
				", name: " + name +
				", age: " 	+ age +
				", email: " + email +
				']';
	}
}
