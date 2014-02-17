package ai.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Iger
 *
 */
@Entity(name="users")
public class User implements Serializable {

	public static final int MAX_FAILED_ATTEMPTS = 5;

	@Id
	@Column(name="username")
	private String userName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "salt")
	private String salt;
	
	@Column(name = "failed_attempts")
	private int failedAttempts;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "country")
	private String country;

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getFailedAttempts() {
		return failedAttempts;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Increases failedAttempts count
	 */
	public void failedAttempt() {
		failedAttempts++;
	}

	/**
	 * 
	 */
	public void clearFailedAttempts() {
		failedAttempts = 0;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return
	 */
	public String getSalt() {
		return salt;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	protected void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

}
