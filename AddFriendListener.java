import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class AddFriendListener implements ActionListener {

	private JTextField addFriendField;
	private FacePamphletProfile currentProfile;
	private FacePamphletDatabase database;
	
	public AddFriendListener(JTextField addFriendField, FacePamphletProfile currentProfile, FacePamphletDatabase database) {
		this.addFriendField = addFriendField;
		this.currentProfile = currentProfile;
		this.database = database;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (FacePamphlet.fieldIsNotEmpty(addFriendField)) {
			/* Stub */
			if (currentProfile != null) {
				if (database.containsProfile(addFriendField.getText())) {
					if (currentProfile.addFriend(addFriendField.getText())) {
						System.out.println(addFriendField.getText() + " added as a friend.");
						FacePamphletProfile profile = database.getProfile(addFriendField.getText());
						profile.addFriend(currentProfile.getName());
					} else {
						System.out.println("You are already friends with " + addFriendField.getText() + "!");
					}
				} else {
					System.out.println("That profile doesn't exist, so we can't add them as your friend.");
				}
			} else {
				System.out.println("You must lookup a profile or add a new one before you can add friends!");
			}
			System.out.println("Current profile is: " + currentProfile);
		}
	}

}
