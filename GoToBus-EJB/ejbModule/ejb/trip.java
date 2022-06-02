package ejb;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.*;
import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
@Entity
@LocalBean
@Table
public class trip implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	@ManyToOne
	@JoinColumn(name="stationToId")
	private station toStation;
	@ManyToOne
	@JoinColumn(name="stationFromId")
	private station fromStation;
	private String from_station;
	private String to_station;
	private String departure_time;
	private String arrival_time;
	private int available_seats;
	private String from_date;
	private String to_date;
	private Date fromDate;
	private Date toDate;
	@ManyToMany(mappedBy="usertrips", fetch = FetchType.EAGER)
	private List<user> users ;
	
	
	public List<user> usersGet() {
		return users;
	}
	public void userAdd(user user) {
		users.add(user);
	}
	public void setFrom_date_fromtime(String time) throws ParseException {
		from_date= time.substring(0, 10);
		SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");  
		fromDate = formatter1.parse(from_date);
	}
	public Date fromDateGet() {
		return fromDate;
	}
	public void fromDateSet(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date toDateGet() {
		return toDate;
	}
	public void toDateSet(Date toDate) {
		this.toDate = toDate;
	}
	public void setTo_date_fromtime(String time) throws ParseException {
		to_date= time.substring(0, 10);
		SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");  
		toDate = formatter1.parse(to_date);
	}

	
	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}
	
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	public trip() {
		
	}
	
	
	public station toStationGet() {
		return toStation;
	}
	public void toStationSet(station toStation) {
		this.toStation = toStation;
	}
	public station fromStationGet() {
		return fromStation;
	}
	public void fromStationSet(station fromStation) {
		this.fromStation = fromStation;
	}
	public String getFrom_station() {
		return from_station;
	}
	public void setFrom_station(String from_station) {
		this.from_station = from_station;
	}
	public String getTo_station() {
		return to_station;
	}
	public void setTo_station(String to_station) {
		this.to_station = to_station;
	}
	public String getDeparture_time() {
		return departure_time;
	}
	public void setDeparture_time(String departure_time) {
		this.departure_time = departure_time;
	}
	public String getArrival_time() {
		return arrival_time;
	}
	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
	}
	public int getAvailable_seats() {
		return available_seats;
	}
	public void setAvailable_seats(int available_seats) {
		this.available_seats = available_seats;
	}
	
	
	
	
	
}