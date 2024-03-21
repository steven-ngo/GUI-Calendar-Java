import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * Controller_Cal class responds to the user input and converts it to commands for the model and view
 */
public class Controller_Cal {
	
	//Controller_Cal fields
	private View_Cal view;
	private Model_Cal model;
	private SimpleDateFormat dateFormat1;
	private Calendar chosenDate;
	
	/*
	 * Controller_Cal Constructor creates actions to performs when an user input receive
	 * @param View_Cal
	 * @param Model_Cal
	 */
	public Controller_Cal(View_Cal vi,Model_Cal mod) {
		view = vi;
		model = mod;
		dateFormat1 = new SimpleDateFormat(" MMMMM yyyy");
		chosenDate = new GregorianCalendar();
		
		// stateChanged actions 
		ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				chosenDate = model.getDate();
				view.getCalDate().setText(dateFormat1.format(chosenDate.getTime()));
				view.getCont_cal().removeAll();
				view.viewMonth(chosenDate);
				view.getEventText().setText(model.dateEvent());
			}
		};
		model.addChangeListener(listener);
		
		//createButton actions 
		view.getCreateButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.dialog();
			}
		});
		
		//preButton actions
		view.getPreButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chosenDate = model.getDate();
				chosenDate.add(Calendar.DATE, -1);
				model.setDate(chosenDate);
			}
		});
		
		//nextButton actions
		view.getNextButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chosenDate = model.getDate();
				chosenDate.add(Calendar.DATE, 1);

				model.setDate(chosenDate);
			}
		});
		
		//quit button action
		view.getQuit().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.Serialize();
				view.getFrame().dispose();
			}
		});

	}	
}
