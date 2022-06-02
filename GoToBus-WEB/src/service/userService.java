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
	@Path("addstation")
	public void addstation(station u ) {
		entityManager.persist(u);
	}
	
	@GET
	@Path("getstation/{Id}")
	public station getStation(@PathParam("Id") int Id)
	{
		return entityManager.find(station.class, Id);
	}
	@GET
	@Path("getstationname/{Name}")
	public station getStationByName(@PathParam("Name") String name)
	{
		return entityManager.createQuery(
				  "SELECT u from station u WHERE u.name = :name", station.class).
				  setParameter("name", name).getSingleResult();
	}
}

	




