package ejb;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Stateless
@Entity
@LocalBean
@Table
public class notification implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name="userId")
	private user users;
	private String message;
	private String notification_datetime;
	public notification () {
		super();
	}
	
	
	public void setUsers(user users) {
		this.users = users;
	}


	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNotification_datetime() {
		return notification_datetime;
	}
	public void setNotification_datetime(String notification_datetime) {
		this.notification_datetime = notification_datetime;
	}
}