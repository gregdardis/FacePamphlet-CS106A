/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants, ChangeStatusListener.StatusChanger,
					ChangePictureListener.PictureChanger, AddFriendListener.FriendAdder {
	
	/* Instance variables for North Controller */
	private JTextField nameField;
	private JButton addButton;
	private JButton deleteButton;
	private JButton lookupButton;
	
	/* Instance variables for West Controller */
	private JTextField changeStatusField;
	private JTextField changePictureField;
	private JTextField addFriendField;
	private JButton changeStatusButton;
	private JButton changePictureButton;
	private JButton addFriendButton;
	
	private FacePamphletProfile currentProfile = null;
	private FacePamphletDatabase database = new FacePamphletDatabase();
	private FacePamphletCanvas canvas = new FacePamphletCanvas();

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		this.resize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		createNorthController();
		createWestController();
		add(canvas);
    }
	
	public void run() {
		noProfileSelectedMessage();
	}
	
	private void noProfileSelectedMessage() {
		canvas.showMessage("No profile selected. Please look one up or add a new one.");
	}
	
	private void createNorthController() {
		addLabelAndTextField();
		addThreeButtonsNorth();
	}
	
	private void addLabelAndTextField() {
		add(new JLabel("Name"), NORTH);
		nameField = new JTextField(TEXT_FIELD_SIZE);
		nameField.addActionListener(this);
		add(nameField, NORTH);
	}
	
	private void addAddButton() {
		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fieldIsNotEmpty(nameField)) {
					/* Stub */
					if (database.containsProfile(nameField.getText())) {
						profileAlreadyExistsMessage();
						System.out.println(database.getProfile(nameField.getText()).toString());
						currentProfile = database.getProfile(nameField.getText());
					} else {
						FacePamphletProfile profile = new FacePamphletProfile(nameField.getText());
						database.addProfile(profile);
						System.out.println("Added new profile named: " + profile.getName() + ".");
						addNewProfileMessage(profile);
						currentProfile = profile;
					}
					System.out.println("Current profile is: " + currentProfile);
				}
			}
		});
		add(addButton, NORTH);
	}
	
	private void profileAlreadyExistsMessage() {
		canvas.showMessage("A profile with that name already exists.");
	}
	
	private void addNewProfileMessage(FacePamphletProfile profile) {
		canvas.showMessage("Added new profile named: " + profile.getName() + ".");
	}
	
	private void addDeleteButton() {
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fieldIsNotEmpty(nameField)) {
					/* Stub */
					if (database.containsProfile(nameField.getText())) {
						database.deleteProfile(nameField.getText());
						System.out.println("The profile was deleted.");
						currentProfile = null;
					} else {
						System.out.println("That profile doesn't exist in the database.");
					}
					System.out.println("Current profile is: " + currentProfile);
				}
			}
		});
		add(deleteButton, NORTH);
	}
	
	private void addLookupButton() {
		lookupButton = new JButton("Lookup");
		lookupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fieldIsNotEmpty(nameField)) {
					/* Stub */
					if (database.containsProfile(nameField.getText())) {
						System.out.println("Lookup: " + database.getProfile(nameField.getText()).toString());
						currentProfile = database.getProfile(nameField.getText());
					} else {
						System.out.println("No profile with that name in the database");
						currentProfile = null;
					}
					System.out.println("Current profile is: " + currentProfile);
				}
			}
		});
		add(lookupButton, NORTH);
	}
	
	private void addThreeButtonsNorth() {
		addAddButton();
		addDeleteButton();
		addLookupButton();
	}
	
	private void createWestController() {
		addChangeStatusInteractors();
		addEmptySpace();
		addChangePictureInteractors();
		addEmptySpace();
		addAddFriendInteractors();
	}
	
	private void addChangeStatusInteractors() {
		addChangeStatusField();
		addChangeStatusButton();
	}
	
	private void addChangePictureInteractors() {
		addChangePictureField();
		addChangePictureButton();
	}
	
	private void addAddFriendInteractors() {
		addAddFriendField();
		addAddFriendButton();
	}
	
	private void addEmptySpace() {
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
	}
	
	/* Makes a new ChangeStatusListener for this field, and the FacePamphlet class itself is a StatusChanger (interface),
	 * so when actionPerformed is called, FacePamphlet calls changeStatus and since it is a StatusChanger it changes the
	 * status of the currentProfile. The rest of the methods below this are analogous */
	private void addChangeStatusField() {
		changeStatusField = new JTextField(TEXT_FIELD_SIZE);
		changeStatusField.addActionListener(new ChangeStatusListener(changeStatusField, this));
		add(changeStatusField, WEST);
	}
	
	private void addChangeStatusButton() {
		changeStatusButton = new JButton("Change Status");
		changeStatusButton.addActionListener(new ChangeStatusListener(changeStatusField, this));
		add(changeStatusButton, WEST);
	}
	
	private void addChangePictureField() {
		changePictureField = new JTextField(TEXT_FIELD_SIZE);
		changePictureField.addActionListener(new ChangePictureListener(changePictureField, this));			
		add(changePictureField, WEST);
	}
	
	private void addChangePictureButton() {
		changePictureButton = new JButton("Change Picture");
		changePictureButton.addActionListener(new ChangePictureListener(changePictureField, this));
		add(changePictureButton, WEST);
	}
	
	private void addAddFriendField() {
		addFriendField = new JTextField(TEXT_FIELD_SIZE);
		addFriendField.addActionListener(new AddFriendListener(addFriendField, this));
		add(addFriendField, WEST);
	}
	
	private void addAddFriendButton() {
		addFriendButton = new JButton("Add Friend");
		addFriendButton.addActionListener(new AddFriendListener(addFriendField, this));
		add(addFriendButton, WEST);
	}
    
    public static boolean fieldIsNotEmpty(JTextField field) {
    	return field.getText().length() != 0;
    }
    
    @Override
    public void changeStatus() {
    	if (fieldIsNotEmpty(changeStatusField)) {
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
    
    @Override
    public void changePicture() {
    	if (fieldIsNotEmpty(changePictureField)) {
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
    
    @Override
    public void addFriend() {
    	if (fieldIsNotEmpty(addFriendField)) {
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