import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class ComboBox extends JComboBox implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private static int duration = 0;

	private static final String[] options = {"Animation Time","15","30","60","90"};
	
	public ComboBox() {
		super(options);
	}
	
	public void addActionListener() {
		super.addActionListener(this);
	}
	
	public void setSelectedIndex(int index) {
		super.setSelectedIndex(index);
	}
	
	public void actionPerformed(ActionEvent e) {
		JComboBox list = (JComboBox)e.getSource();
		String option = (String)list.getSelectedItem();
		if(option == "15") {
			duration = 15;
		} else if(option == "30") {
			duration = 30;
		} else if(option == "60"){
			duration = 60;
		} else if(option == "90") {
			duration = 90;
			
		}
		
	}
	
	public static int getDuration() {
		return duration;
	}
}
