/*
 * SimpleCalendar creates Model_Cal, view_Cal, and Controller_Cal  
 */
public class SimpleCalendar {
	/*
	 * main method
	 */
	public static void main(String[] args) {
		Model_Cal model = new Model_Cal();
		View_Cal view = new View_Cal(model);
		Controller_Cal controller = new Controller_Cal(view , model);

	}

}
