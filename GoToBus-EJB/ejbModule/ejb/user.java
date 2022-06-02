package ejb;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.*;
import javax.ejb.Stateless;
import javax.persistence.*;


@Stateless
@Entity
@LocalBean
@Table
public class user implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	private String username;
	private String password;
	private String role;
	private String full_name;
	@OneToMany(mappedBy="users")
	private List<notification> notifications;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="UserXTrip",
			joinColumns=@JoinColumn(name="userId"),
			inverseJoinColumns=@JoinColumn(name="Id"))
	private List<trip> usertrips;
	
	public List<trip> usertripsget() {
		return usertrips;
	}
	
	public void tripAdd(trip t) {
		usertrips.add(t);
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	public List<notification> notificationsGet() {
		return notifications;
	}
	
	public void notificationAdd(notification n ) {
		notifications.add(n);
	}
	
	public user() {
		
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public void setUsername(String name) {
		this.username = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword(){
		return password;
	}
}