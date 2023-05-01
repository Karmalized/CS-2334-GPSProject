import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

public class CheckBox extends JCheckBox implements ItemListener {

	private static final long serialVersionUID = 1L;
	
	private static boolean includeStops;
	private static String name;

	public CheckBox(String string, boolean checked) {
		super(string,checked);
		includeStops = checked;
		name = string;
	}
	
	public void addItemListener() {
		super.addItemListener(this);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			includeStops = true;
		}
		if(e.getStateChange() == ItemEvent.DESELECTED) {
			includeStops = false;
		}
	}
	
	public static boolean getIsChecked() {
		return includeStops;
	}
	
	public String getName() {
		return name;
	}

}
