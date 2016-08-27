/**
 * 
 */
package com.dexter.eventmanager.dao.impl;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.dexter.eventmanager.dao.EventManagerDAO;
import com.dexter.eventmanager.entity.Employee;
import com.dexter.eventmanager.entity.Event;
import com.dexter.eventmanager.util.KeyboardUtil;

/**
 * @author Dexter Fernandes
 *
 */
public class EventManagerDAOImpl implements EventManagerDAO {
	
	public SessionFactory getSessionFactoryInstance(){
		
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
		srBuilder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
		
		return configuration.buildSessionFactory(serviceRegistry);
		
	}

	/* (non-Javadoc)
	 * @see com.dexter.eventmanager.dao.EventManagerDAO#findEventFromId(org.hibernate.Session, int)
	 */
	public Event findEventFromId(Session session, int eventId) {

		Query query = session.createQuery("from Event e where e.id = :eventId");
		query.setParameter("eventId", eventId);
		
		Event event = (Event) query.uniqueResult();
		
		return event;
		
	}

	/* (non-Javadoc)
	 * @see com.dexter.eventmanager.dao.EventManagerDAO#saveEvent(org.hibernate.Session, java.util.Scanner)
	 */
	public void saveEvent(Session session) {
		
		Event event = new Event();
		
		System.out.println("Enter the details below:");
		
		event.setEventName(KeyboardUtil.getString("Event Name: "));
		event.setEventDescription(KeyboardUtil.getString("Description: "));
		
		session.save(event);
		
	}

	/* (non-Javadoc)
	 * @see com.dexter.eventmanager.dao.EventManagerDAO#findEmployee(org.hibernate.Session, java.lang.String)
	 */
	public Employee findEmployee(Session session, String empId) {

		Query query = session.createQuery("from Employee e where e.empId = :employeeId");
		query.setParameter("employeeId", empId);
		
		Employee employee = (Employee) query.uniqueResult();
		
		return employee;
		
	}

	/* (non-Javadoc)
	 * @see com.dexter.eventmanager.dao.EventManagerDAO#getAllEvents(org.hibernate.Session)
	 */
	public int getAllEvents(Session session) {

		Query query = session.createQuery("from Event");
		
		Collection<Event> events = query.list();

		if(events.size() > 0){
			System.out.println("The list of available events are below:");
			for(Event event : events){
				System.out.println("\nEVENT ID: " + event.getId());
				System.out.println("EVENT Name: " + event.getEventName());
				System.out.println("EVENT Description: " + event.getEventDescription());
			}
		}else{
			System.out.println("There are no events available");			
		}
		
		return events.size();
		
	}

	/* (non-Javadoc)
	 * @see com.dexter.eventmanager.dao.EventManagerDAO#saveEmployee(org.hibernate.Session, java.lang.String, java.util.Scanner)
	 */
	public Employee saveEmployee(Session session, String empId) {

		Employee employee = findEmployee(session, empId);
		
		if(employee == null){
			Employee newEmployee = new Employee();
			
			System.out.println("Please enter the Employee details:");
			
			newEmployee.setEmpId(empId);
			newEmployee.setName(KeyboardUtil.getString("Name: "));
			newEmployee.setJoinDate(KeyboardUtil.getDate("Date of Joining (MM/DD/YYYY): "));
			newEmployee.setEmailId(KeyboardUtil.getString("Email Id: "));
			
			session.save(newEmployee);
			
			employee = newEmployee;
			
		}
		
		return employee;
		
	}

	/* (non-Javadoc)
	 * @see com.dexter.eventmanager.dao.EventManagerDAO#registerEmployeeForEvent(org.hibernate.Session, int, java.lang.String, java.util.Scanner)
	 */
	public void registerEmployeeForEvent(Session session, int userInputEventId, String userInputMID) {

		Employee employee = saveEmployee(session, userInputMID);
		Event event = findEventFromId(session, userInputEventId);
		
		event.getEmployees().add(employee);
		employee.getEvents().add(event);
		
		System.out.println("\n" + employee.getName() + " has been registered for " + event.getEventName() + "\n");

	}

	/* (non-Javadoc)
	 * @see com.dexter.eventmanager.dao.EventManagerDAO#getAllEmployees(org.hibernate.Session)
	 */
	public void getAllEmployees(Session session) {

		Query query = session.createQuery("from Employee");

		Collection<Employee> employees = query.list();

		if(employees.size() > 0){
			for(Employee employee : employees){
				System.out.println("\nMID: " + employee.getEmpId());
				System.out.println("Name: " + employee.getName());
				System.out.println("Date Of Joining: " + KeyboardUtil.formatDateToString(employee.getJoinDate()));
				System.out.println("Email Id: " + employee.getEmailId());
			}
		}else{
			System.out.println("\nThere are no Employee records");
		}

	}

}
