//import java library
import java.text.*;
import java.util.*;

import javax.swing.JOptionPane;

import java.io.Serializable;

/*
 * Class Event is an object that holds information of an event:
 * describe, date, starting time, and ending time of an event.
 * class Event implement Serializable and Comparable
 */
public class Event implements Serializable, Comparable<Event> {
	
	//Event fields
	private static final long serialVersionUID = 1L;
	private String describe;
	private Date day;
	private Date startTime;
	private Date endTime;
	private int invalidInput=0;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	
	Calendar cal = new GregorianCalendar();
	Calendar cal1 = new GregorianCalendar();
	Calendar cal2 = new GregorianCalendar();
	
	/*
	 * Constructor
	 */
	public Event () {	
	}

	/*
	 *Event constructor initializes Event fields 
	 *@param describe 
	 *@param day 
	 *@param startTime 
	 *@param endTime 
	 */
	public Event(String describe, Date day, String startTime, String endTime) { 
		try {
		this.describe = describe;
		this.day = day;
		this.startTime = timeFormat.parse(startTime);
		this.endTime = timeFormat.parse(endTime);
		cal.setTime(this.day);
		cal1.setTime(this.startTime);
		cal2.setTime(this.endTime);
		}
		catch(ParseException e) {
			JOptionPane.showMessageDialog(null, "ERROR! Input Invalid ");
			invalidInput =1;
		}
		
	}
	
	/*
	 * CompareTo method used for sort the event
	 * @param Event e
	 * @return signed integer number
	 */
	 public int compareTo(Event e)
	    {
	        if (cal.get(Calendar.YEAR) != e.getYear())
	        	return cal.get(Calendar.YEAR) - e.getYear();
	        
	        else if (cal.get(Calendar.MONTH) + 1 != e.getMonth())
	        	return cal.get(Calendar.MONTH) + 1 - e.getMonth();
	        
	        else if (cal.get(Calendar.DAY_OF_MONTH) != e.getDay())
	        	return cal.get(Calendar.DAY_OF_MONTH) - e.getDay();
	        
	        else
	        	return startTime.compareTo(e.startTime);        
	    }
	
	 /*
	  * InvalidInput getter
	  * @return invalidInput
	  */
	 public int getInvalidInput() {
		 return invalidInput;
	 }
	 
	/** 
	 * Describe getter
	 * @return the describe
	 */
	public String getDescribe() {
		return describe;
	}
	
	/**
	 * Year getter
	 * @return the year
	 */
	public int getYear() {
		return cal.get(Calendar.YEAR);
	}
	
	/*
	 * Month getter
	 * @return the month
	 */
	public int getMonth() {
		return cal.get(Calendar.MONTH) + 1;
	}

	/**Day getter
	 * @return the day
	 */
	public int getDay() {
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**starting time getter
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/*
	 * ending time getter
	 * @return endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	
	/* 
	 * toString method to print out the Event fields
	 * @return describe
	 * @return date
	 * @return starting time
	 * @return ending time
	 * @Override
	 */
	public String toString() {
		
			return   timeFormat.format(startTime) + " to " + timeFormat.format(endTime)+
					"  --  "+ describe;	
	}
}
