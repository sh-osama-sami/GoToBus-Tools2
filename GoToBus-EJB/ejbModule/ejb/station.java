package ejb;

import java.io.Serializable;
import java.util.Set;

import javax.ejb.*;
import javax.ejb.Stateless;
import javax.persistence.*;


@Stateless
@Entity
@LocalBean
@Table
public class station implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	private String name;
	private double longitude;
    private double latitude;
    @OneToMany(mappedBy="fromStation", fetch=FetchType.EAGER)
	private Set<trip> fromStationsTrip ;
	@OneToMany(mappedBy="toStation", fetch=FetchType.EAGER)
	private Set<trip> toStationsTrip ;
    
    
	public int getId() {
		return Id;
	}

	public Set<trip> getToStationsTrip() {
		return toStationsTrip;
	}

	public void setToStationsTrip(Set<trip> toStationsTrip) {
		this.toStationsTrip = toStationsTrip;
	}

	public void setId(int id) {
		Id = id;
	}
	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public station() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	
	
	
	

}