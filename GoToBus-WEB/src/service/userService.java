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
	
	@POST
	@Path("adduser")
	public void addUser(user u ) {
		entityManager.persist(u);
	}
	
	@GET
	@Path("getuser/{Id}")
	public user sayHello(@PathParam("Id") int Id)
	{
		return entityManager.find(user.class, Id);
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
	@GET
	@Path("getstationname/{Name}")
	public station getStationByName(@PathParam("Name") String name)
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
	
	@GET
	@Path("gettrip/{Id}")
	public trip getTrip(@PathParam("Id") int Id)
	{
		return entityManager.find(trip.class, Id);
	}

	
	
	
}

	




