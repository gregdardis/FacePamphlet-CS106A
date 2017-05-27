import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class ChangeStatusListener implements ActionListener {

	private JTextField changeStatusField;
	private StatusChanger statusChanger;
	
	public ChangeStatusListener(JTextField changeStatusField, StatusChanger statusChanger) {
		this.changeStatusField = changeStatusField;
		this.statusChanger = statusChanger;
	}
	
	public interface StatusChanger {
		void changeStatus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		statusChanger.changeStatus();
	}
	
}
