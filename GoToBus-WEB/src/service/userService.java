package service;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.*;
import javax.inject.Inject;
import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ejb.notification;
import ejb.station;
import ejb.trip;
import ejb.user;

import javax.ws.rs.*;

@Stateless
@Path("service")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class userService  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "hello" )
	private EntityManager entityManager;
	@Inject
	user userService = null;
	@POST
	@Path("login")
	public void login(loginInput i ) {
		user u = getUserByName(i.getUsername());
		if(u != null)
		{
			if(u.getPassword().equals(i.getPassword()))
			{
				userService = u;
			}
			else 
			{
				throw new WebApplicationException(Response.Status.BAD_REQUEST);
			}
		}
		else 
		{
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
	}
	
	public user getUserByName(String name)
	{ 	try {
			return entityManager.createQuery(
				  "SELECT u from user u WHERE u.username = :name", user.class).
				  setParameter("name", name).getSingleResult();
			}
		catch(Exception e){
			return null;
		}
	}
	
	@POST
	@Path("user")
	public String addUser(user u ) {
		entityManager.persist(u);
		return "";
	}
	
	
	public user getUserById(@PathParam("Id") int Id)
	{
		return entityManager.find(user.class, Id);
	}
	
	
	@GET
	@Path("notifications/{Id}")
	public List<notification> viewNotifications(@PathParam("Id") int Id)
	{
		try {
		user u= entityManager.find(user.class, Id);
		return u.notificationsGet();
		}
		catch (Exception e){
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
	@POST
	@Path("station")
	public String addstation(station u ) {
		try {
		entityManager.persist(u);
		return "";
	
		}
		catch(Exception e) {
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
	@GET
	@Path("station/{Id}")
	public station getStation(@PathParam("Id") int Id)
	{
		try {
		return entityManager.find(station.class, Id);
	
		}
		catch(Exception e)
		{
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	public station getStationByName(String name)
	{
		return entityManager.createQuery(
				  "SELECT u from station u WHERE u.name = :name", station.class).
				  setParameter("name", name).getSingleResult();
	}

	@POST
	@Path("trip")
	public String addTrip(trip t ) throws ParseException  {
	if(userService.getRole().equals("admin")) {
		String fromStationName= t.getFrom_station();
		String toStationName = t.getTo_station();
		station fromStation = getStationByName(fromStationName);
		station toStation = getStationByName(toStationName);
		t.fromStationSet(fromStation);
		t.toStationSet(toStation);
		t.setFrom_date_fromtime(t.getDeparture_time());
		t.setTo_date_fromtime(t.getArrival_time());
		entityManager.persist(t);
		return "";
	}
	else 
	{
		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
	}
		
	}
	
	public List<trip> listAllTrips() {
		List<trip> trips =null;
		Query query = entityManager.createQuery ("SELECT t FROM trip t");
		trips = query.getResultList();
	return trips;
		
	}
	
	
	public trip getTrip(int Id)
	{
		return entityManager.find(trip.class, Id);
	}
	
	
	@GET
	@Path("searchtrips")
	public List<trip> getAllTrips(searchTripInput input) throws ParseException {
			try {
			station fromStation = entityManager.find(station.class , input.from_station);
			station toStation = entityManager.find(station.class , input.to_station);
			SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy"); 
			Date fromDate = formatter1.parse(input.from_date);
			Date toDate = formatter1.parse(input.to_date);
			List<trip> finalTrips = new ArrayList<trip>();
			List<trip> trips = listAllTrips();
			for(trip t:trips)
			{
				if(fromStation  == t.fromStationGet() && toStation == t.toStationGet() && fromDate.before(t.fromDateGet())  && (t.fromDateGet()).before(toDate)) 
					finalTrips.add(t);
			}
			return finalTrips;
			}
			catch(Exception e)
			{
				throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
			}
			
	}
	@POST
	@Path("booktrip")
	public String bookTrip(bookInput input) {
		try {
		if (!userService.equals(null)) {
		trip t = entityManager.find(trip.class , input.trip_id);
		user u = entityManager.find(user.class , input.user_id);
		if (t.getAvailable_seats() > 0)
		{
			u.tripAdd(t);
			t.userAdd(u);
			int seats = t.getAvailable_seats();
			seats = seats-1;
			t.setAvailable_seats(seats);
			notification n= new notification();
			n.setMessage("You have booked trip from "+t.getFrom_station()+" to "+t.getTo_station()+" successfully");
    		String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    		String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    		n.setNotification_datetime( date +" "+ time );
			u.notificationAdd(n);
			n.setUsers(u);
			entityManager.persist(n);
			entityManager.merge(t);
			entityManager.merge(u);
			return "";
			
		}
		else  {
			notification n= new notification();
			 n.setMessage("Sorry, Trip"+t.getFrom_station()+"to"+t.getTo_station()+"r have no available seats");
			 String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			 String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
			 n.setNotification_datetime( date +" "+ time );
			 u.notificationAdd(n);
			 n.setUsers(u);
			 entityManager.persist(n);
			 entityManager.merge(t);
			 entityManager.merge(u);
				return "";
		}
			
		}
		else 
		{
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
		}
		catch (Exception e){
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
		
			
	}
	
	

	
}