import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import java.text.*;

/*
 * View_Cal class is to create the view of the calendar
 */
public class View_Cal {
	
	//View_Cal fields
	private final String [] da = {" Su "," Mo "," Tu "," We "," TH "," Fr "," Sa "};
	private JLabel [] label;
	private JLabel calDate;
	private JFrame frame; 
	private JButton preButton,nextButton,createButton,quit, save;
	private JTextArea eventText;
	private JPanel preNext;
	private Model_Cal model;
	private ButtonGroup group;
	private SimpleDateFormat dateFormat1;
	private Container cont_whole,cont_cal;
	private JTextField eventName, startTime, endTime;
	private JDialog dialog;
	
	/*
	 * View_Cal constructor is to create the calendar 
	 * @param Model_Cal mod
	 */
	public View_Cal( Model_Cal mod){
		
		model = mod;
		frame = new JFrame("Calendar by Steven Ngo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		 calDate = new JLabel();
		 cont_cal = new Container();
		 cont_whole = new Container();
		 group = new ButtonGroup();
		 label = new JLabel[7];
		 dateFormat1 = new SimpleDateFormat(" MMMMM yyyy");
	
		 preButton = new JButton("<<");
	     nextButton = new JButton(">>");
	     quit = new JButton("Quit");
	     createButton= new JButton("CREATE EVENT");
	    
	     preNext = new JPanel ();
	     preNext.setLayout(new GridLayout());
	     preNext.add(preButton);
	     preNext.add(nextButton);
	     preNext.add(quit);
	     
	     eventText = new JTextArea(9,10);
	     JScrollPane scoll = new JScrollPane(eventText);
	     eventText.setEditable(false);
	     eventText.setText(model.dateEvent());

	    frame.add(createButton);
	    viewMonth(model.getDate());
	    frame.add(cont_whole);
	    frame.add(preNext);
	    frame.add(scoll);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
		frame.setVisible(true);

	}

	/*
	 * viewMonth method is to print out month view calendar 
	 * @param Calendar ca
	 */
	public void viewMonth(Calendar ca) {
		Calendar cal = new GregorianCalendar();
		Calendar cal1 = new GregorianCalendar();
		cal.setTime(ca.getTime());
		cal1.setTime(ca.getTime());

		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		calDate.setText(dateFormat1.format(cal.getTime()));
		calDate.setFont(new Font("Courier", Font.BOLD,18));
		cont_whole.setLayout(new BoxLayout(cont_whole, BoxLayout.Y_AXIS));
		cont_whole.add(calDate);
		
		cont_cal.setLayout(new GridLayout(6,6));
		
		//print out day in week Name (Su,Mo,Tu,We,Th,Fr,Sa) 
		for (int i=0; i<7; i++) {
			label [i] = new JLabel(da[i]);
			cont_cal.add(label [i]);
		}
	
		//print out empty spaces of the first week of the month
		for(int i=0; i < (cal.get(Calendar.DAY_OF_WEEK) -1);i++) {
			JButton b = new JButton(String.valueOf(i+1));
			b.setVisible(false);
			cont_cal.add(b);
		}
		
		// print out the day of the month 
		for(int i=0; i<cal.getActualMaximum(Calendar.DAY_OF_MONTH);i++) {
				JToggleButton button = new JToggleButton(String.valueOf(i+1));
				
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cal1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(button.getText()));
						model.setDate(cal1);
					}
		        });
				group.add(button);
				if(ca.get(Calendar.DAY_OF_MONTH)-1 == i) 
					button.setSelected(true);
				cont_cal.add(button);		
		}
		cont_whole.add(cont_cal);	
	}

/*
 * dialog method is to create a dialog allowing users enter an event information
 */
	public void dialog() {
		
		dialog = new JDialog(frame,true);
		dialog.setTitle("Creating An Event");
		dialog.setSize(420, 140);
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("EEE, dd MMM yyyy");
		JLabel dateheader = new JLabel(dateFormat2.format(model.getDate().getTime()));
		
		eventName= new JTextField("Untitled Event ",30);
		JPanel panel1 = new JPanel();
		panel1.add(eventName);
		
		JLabel instr = new JLabel("00:00 to 23:59");
		startTime= new JTextField("HH:MM",6);
		JLabel to = new JLabel("to");
		endTime= new JTextField("HH:MM ",6);
		
		save = new JButton("SAVE");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String eventNam = eventName.getText().trim();
				String startTim = startTime.getText().trim();
				String endTim = endTime.getText().trim();
				Date date = model.getDate().getTime();
				Event event = new Event(eventNam, date,startTim, endTim);
				if(event.getInvalidInput()==0) {
					if(model.addEvent(event) == false) 
						JOptionPane.showMessageDialog(null, "ERROR! Time Conflict ");
				}		
					dialog.dispose();
			}
		});
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(startTime);
		panel.add(to);
		panel.add(endTime);
		panel.add(save);
		
		Container container = new Container();
		container = dialog.getContentPane();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(dateheader);
		container.add(panel1);
		container.add(instr);
		container.add(panel);

		dialog.setResizable(false);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
	
	
	
	/** frame getter
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**cont_cal getter
	 * @return the cont_cal
	 */
	public Container getCont_cal() {
		return cont_cal;
	}

	/** cont_whole getter
	 * @return the cont_whole
	 */
	public Container getCont_whole() {
		return cont_whole;
	}

	/** preButton getter
	 * @return the preButton
	 */
	public JButton getPreButton() {
		return preButton;
	}

	/** dialog getter
	 * @return the dialog
	 */
	public JDialog getDialog() {
		return dialog;
	}

	/** nextButton getter
	 * @return the nextButton
	 */
	public JButton getNextButton() {
		return nextButton;
	}

	/** createButton getter
	 * @return the createButton
	 */
	public JButton getCreateButton() {
		return createButton;
	}

	/** quit getter
	 * @return the quit
	 */
	public JButton getQuit() {
		return quit;
	}

	/**eventText getter
	 * @return the eventText
	 */
	public JTextArea getEventText() {
		return eventText;
	}

	/**group getter
	 * @return the group
	 */
	public ButtonGroup getGroup() {
		return group;
	}

	/**calDate getter
	 * @return the calDate
	 */
	public JLabel getCalDate() {
		return calDate;
	}

	/**save getter
	 * @return the save
	 */
	public JButton getSave() {
		return save;
	}

	/**eventName getter
	 * @return the eventName
	 */
	public JTextField getEventName() {
		return eventName;
	}

	/**startTime getter
	 * @return the startTime
	 */
	public JTextField getStartTime() {
		return startTime;
	}

	/**endTime getter
	 * @return the endTime
	 */
	public JTextField getEndTime() {
		return endTime;
	}	
}
