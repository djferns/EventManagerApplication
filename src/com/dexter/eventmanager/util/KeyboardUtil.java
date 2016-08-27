/**
 * 
 */
package com.dexter.eventmanager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @author Dexter Fernandes
 *
 */
public class KeyboardUtil {

	public static String getString(String msg) {

		Scanner scanner= new Scanner(System.in);
		System.out.print(msg);
		return scanner.nextLine();

	}

	public static int getInt(String msg){

		Scanner scanner= new Scanner(System.in);
		System.out.print(msg);
		int n = scanner.nextInt();
		return n;

	}

	public static Date getDate(String msg){

		Scanner scanner= new Scanner(System.in);
		System.out.print(msg);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();

		try {
			date = sdf.parse(scanner.nextLine());
		} catch (ParseException e) {
			System.out.println("Exception message = " + e.getMessage());
			e.printStackTrace();
		}

		return date;

	}
	
	public static String formatDateToString(Date date){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		return sdf.format(date);
		
	}
	
}
