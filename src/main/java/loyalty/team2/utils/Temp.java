package loyalty.team2.utils;

import java.time.LocalDate;

public class Temp {
	public static void main(String[] args) {
		 String dateString1 = "2023-01-01";
	        String dateString2 = "2023-12-31";

	        // Parse the date strings to LocalDate objects
	        LocalDate date1 = LocalDate.parse(dateString1);
	        LocalDate date2 = LocalDate.parse(dateString2);

	        System.out.println(date2.getDayOfMonth());
	        
	        // Compare the two dates
//	        if (date1.isBefore(date2)) {
//	            System.out.println(dateString1 + " is before " + dateString2);
//	        } else if (date1.isAfter(date2)) {
//	            System.out.println(dateString1 + " is after " + dateString2);
//	        } else {
//	            System.out.println(dateString1 + " is equal to " + dateString2);
//	        }

	        // Calculate the difference between the two dates
//	        long daysDifference = Math.abs(date1.until(date2).getDays());
//	        System.out.println("The difference in days is: " + daysDifference);
	}
}
