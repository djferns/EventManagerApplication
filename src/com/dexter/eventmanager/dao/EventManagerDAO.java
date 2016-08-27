/**
 * 
 */
package com.dexter.eventmanager.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.dexter.eventmanager.entity.Employee;
import com.dexter.eventmanager.entity.Event;

/**
 * @author Dexter Fernandes
 *
 */
public interface EventManagerDAO {
	
	public SessionFactory getSessionFactoryInstance();

	public Event findEventFromId(Session session, int eventId);
	
	public void saveEvent(Session session);
	
	public Employee findEmployee(Session session, String empId);
	
	public int getAllEvents(Session session);
	
	public Employee saveEmployee(Session session, String empId);
	
	public void registerEmployeeForEvent(Session session, int userInputEventId, String userInputMID);
	
	public void getAllEmployees(Session session);
	
}
