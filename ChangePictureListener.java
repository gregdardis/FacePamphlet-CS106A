import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import acm.graphics.GImage;
import acm.util.ErrorException;

public class ChangePictureListener implements ActionListener {

	private JTextField changePictureField;
	private FacePamphletProfile currentProfile;
	
	public ChangePictureListener(JTextField changePictureField, FacePamphletProfile currentProfile) {
		this.changePictureField = changePictureField;
		this.currentProfile = currentProfile;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (FacePamphlet.fieldIsNotEmpty(changePictureField)) {
			/* Stub */
			if (currentProfile != null) {
				GImage image = null;
				try {
					image = new GImage(changePictureField.getText());
					System.out.println("That is an image! It will be added.");
					currentProfile.setImage(image);
				} catch (ErrorException ex) {
					System.out.println("That file doesn't exist! Try again.");
				}
			} else if (currentProfile == null) {
				System.out.println("No profile selected. Add or lookup a profile to change their picture");
			}
			System.out.println("Current profile is: " + currentProfile);
		}
	}

}
