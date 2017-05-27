import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class ChangeStatusListener implements ActionListener {

	private JTextField changeStatusField;
	private FacePamphletProfile currentProfile;
	
	public ChangeStatusListener(JTextField changeStatusField, FacePamphletProfile currentProfile) {
		this.changeStatusField = changeStatusField;
		this.currentProfile = currentProfile;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (FacePamphlet.fieldIsNotEmpty(changeStatusField)) {
			/* Stub */
			if (currentProfile != null) {
				currentProfile.setStatus(changeStatusField.getText());
				System.out.println("Status has been updated to: " + changeStatusField.getText());
			} else if (currentProfile == null) {
				System.out.println("No profile selected. Add or lookup a profile to change their status");
			} else {
				System.out.println("Something went wrong");
			}
			System.out.println("Current profile is: " + currentProfile);
		}
	}

}
