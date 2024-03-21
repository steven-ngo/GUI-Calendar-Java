import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * Model_Cal class is responsible for managing the data of the application.
 */
public class Model_Cal {
	
	// Model_Cal fields
	private ArrayList<Event> events;
	private Calendar date;
	private ArrayList<ChangeListener> listeners;
	
	/*
	 * Model_Cal constructor creates Model_Cal fields and populate the events ArratList by deserialize
	 */
	public Model_Cal() {
		events= new ArrayList<Event>();
		listeners = new ArrayList<ChangeListener>();
		Deserialize();
		date = new GregorianCalendar();
	}
	
	/*
	 * add ChangeListener into the listener ArrayList
	 * @param ChangeListener c
	 */
	public void addChangeListener(ChangeListener c) {
		listeners.add(c);
	}
	
	/*
	 * Deserialize method is to deserialize the event.txt back into the ArrayList list
	*/
	public void Deserialize() {
		 try
	        {
	            FileInputStream fis = new FileInputStream("event.txt");
	            ObjectInputStream ois = new ObjectInputStream(fis);
	            events = (ArrayList<Event>) ois.readObject();
	            ois.close();
	            fis.close();
	            System.out.println("Load succeeded.");
	            
	    }catch(IOException ioe){
	        	 System.out.println("There is no event.txt. This might be the first run");
	             return;
	             
	    }catch(ClassNotFoundException c){
	             System.out.println("There is no event.txt. This is the first run");
	             return;
	      	}		
	}
	
	/*
	 * Serialize method is to serialize the ArrayList list into event.txt 
	 */
	public void Serialize() {
		try{
	         FileOutputStream fos= new FileOutputStream("event.txt");
	         ObjectOutputStream oos= new ObjectOutputStream(fos);
	         oos.writeObject(events);
	         oos.close();
	         fos.close();
	         
	       }catch(IOException e){
	    	   		System.out.println("IOException is catched ");
	            e.printStackTrace();
	        }
	}

	/*
	 * addEvent method is to add an event into the events ArrayList, it will check for time conflict 
	 * @param Event ev
	 * @return boolean whether or not an event add succeed  
	 */
	public boolean addEvent (Event ev) {
	
		//if the list is empty, add the event into the list
		if (events.isEmpty()) {
			events.add(ev);
			for (ChangeListener l : listeners) 
				l.stateChanged(new ChangeEvent(this));
			
			return true;
		}
		else {	
			//check if there is any time conflict, if not then add into the list, else return false 
			for(Event x: events) {
			if (x.getDay() == ev.getDay() && x.getMonth() == ev.getMonth() && x.getYear() == ev.getYear()) {
				if (x.getEndTime() !=null && ev.getEndTime()!= null) {
					if (ev.getEndTime().before(x.getStartTime()) || ev.getStartTime().after(x.getEndTime())) {
						events.add(ev);
						for (ChangeListener l : listeners) 
							l.stateChanged(new ChangeEvent(this));
						return true;
					}
					else return false;
				}
				if (x.getEndTime() !=null && ev.getEndTime() == null) {
					if(ev.getStartTime().before(x.getStartTime()) || ev.getStartTime().after(x.getEndTime())) {
						events.add(ev);
						for (ChangeListener l : listeners) 
							l.stateChanged(new ChangeEvent(this));
						return true;
					}
					else return false;	
				}
				if (x.getEndTime() ==null && ev.getEndTime() != null) {
					if(ev.getEndTime().before(x.getStartTime()) || ev.getStartTime().after(x.getStartTime())) {
						events.add(ev);
						for (ChangeListener l : listeners) 
							l.stateChanged(new ChangeEvent(this));
						return true;
					}
					else return false;	
				}
				else {
					if(ev.getStartTime().before(x.getStartTime()) || ev.getStartTime().after(x.getStartTime())) {
						events.add(ev);
						for (ChangeListener l : listeners) 
							l.stateChanged(new ChangeEvent(this));
						return true;
					}
					else return false;
				}	
			}	
			}
			//there is no event on the date, then add
			events.add(ev);
			for (ChangeListener l : listeners) 
				l.stateChanged(new ChangeEvent(this));
			return true;
		}	
	}
	
	/*
	 * dateEvent method is to get the event(s) in the selected date
	 * @return String of event(s)
	 */
	public String dateEvent() {
		
		Collections.sort(events);
		SimpleDateFormat sf = new SimpleDateFormat(" EE, MMM dd");
		String text = sf.format(date.getTime());
		text += "\n----------\n";
		
		//get events in a particular day
		for(Event x: events) {
			if (x.getDay() == date.get(Calendar.DAY_OF_MONTH) && x.getMonth() == (date.get(Calendar.MONTH) + 1)
				&&	x.getYear() == date.get(Calendar.YEAR)) {
				text += x.toString();
				text += "\n";
			}
		}
		return text;
	}
	
	/*
	 * setDate method is to set date
	 * @param Calendar da
	 */
	public void setDate(Calendar da) {
		Date d= da.getTime();
		date.setTime(d); 
		for (ChangeListener l : listeners) 
			l.stateChanged(new ChangeEvent(this));
	}
	
	/*
	 * getDate is date getter
	 * @return Calendar date 
	 */
	public Calendar getDate() {
		return date;
	}
	
	/*
	 * toString method is to get all the events in the events ArrayList
	 * @return String
	 */
	public String toString() {
		String text ="";
			for (Event x : events) {
			text += x.toString();
			text += "\n";
			}
		return text;
	}
}
