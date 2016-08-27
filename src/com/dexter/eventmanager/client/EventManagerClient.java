/**
 * 
 */
package com.dexter.eventmanager.client;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.dexter.eventmanager.dao.EventManagerDAO;
import com.dexter.eventmanager.dao.impl.EventManagerDAOImpl;
import com.dexter.eventmanager.util.KeyboardUtil;

/**
 * @author Dexter Fernandes
 *
 */
public class EventManagerClient {

	SessionFactory sessionFactory;
	
	public void setup(){
		
		EventManagerDAO eventManagerDAO = new EventManagerDAOImpl();
		sessionFactory = eventManagerDAO.getSessionFactoryInstance();
	}
	
	private static void registerAnEmployee(Session session){
		
		int userInput = 0;
		String userInputMID = null;
		
		EventManagerDAO eventManagerDAO = new EventManagerDAOImpl();
		
		int size = eventManagerDAO.getAllEvents(session);
		
		if(size > 0){
			userInput = KeyboardUtil.getInt("Enter the Event Id for which you want to register:");
			userInputMID = KeyboardUtil.getString("Please enter the Employee's MID: ");
			eventManagerDAO.registerEmployeeForEvent(session, userInput, userInputMID);
		}
		
	}
	
	private static void getListOfEmployees(Session session){
		
		EventManagerDAO eventManagerDAO = new EventManagerDAOImpl();
		
		eventManagerDAO.getAllEmployees(session);
		
	}
	
	private static void addEvent(Session session){
		
		EventManagerDAO eventManagerDAO = new EventManagerDAOImpl();
		
		eventManagerDAO.saveEvent(session);
		
	}
	
	private static int showMenu(){
		
		System.out.println("\nEVENT REGISTRATION SYSTEM");
		System.out.println("1. Register employee for Events");
		System.out.println("2. Display All Employees");
		System.out.println("3. Add an Event");
		
		return KeyboardUtil.getInt("Please choose the action: ");
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		EventManagerClient eventManagerService = new EventManagerClient();
		eventManagerService.setup();
		
		int input = 0;
		String continueKey = null;
		
		do{
			
			input = showMenu();
			
			Session session = eventManagerService.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			switch (input) {
			case 1: registerAnEmployee(session);
					break;
					
			case 2: getListOfEmployees(session);
					break;
					
			case 3: addEvent(session);
					break;
				
			default:System.out.println("Wrong option selected");
					break;
			}
			
			transaction.commit();
			session.close();
			
			continueKey = KeyboardUtil.getString("\nDo you want to perform any more actions (Yes/No): ");
			
		}while(continueKey.equalsIgnoreCase("Y") || continueKey.equalsIgnoreCase("Yes"));
		
	}

}
