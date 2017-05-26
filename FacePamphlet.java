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

public class FacePamphlet extends ConsoleProgram 
					implements FacePamphletConstants {
	
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
	
	private FacePamphletProfile profile;
	private FacePamphletProfile profile2;
	private FacePamphletDatabase database;

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		this.resize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		createNorthController();
		createWestController();
		database = new FacePamphletDatabase();
//		profile = new FacePamphletProfile("Greg Dardis");
//		profile2 = new FacePamphletProfile("Hailey");
//		database.addProfile(profile);
//		database.addProfile(profile2);
//		profile.setStatus("coding like a fiend");
//		profile.addFriend("Hailey");
//		profile2.addFriend("Greg Dardis");
//		database.deleteProfile("Hailey");
//		System.out.println(profile.toString());
//		System.out.println(profile2.toString());
		
		
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
						println("A profile with that name already exists.");
						println(database.getProfile(nameField.getText()).toString());
					} else {
						FacePamphletProfile profile = new FacePamphletProfile(nameField.getText());
						database.addProfile(profile);
						println("Added new profile named: " + profile.getName() + ".");
					}
				}
			}
		});
		add(addButton, NORTH);
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
						println("The profile was deleted.");
					} else {
						println("That profile doesn't exist in the database");
					}
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
						println("Lookup: " + database.getProfile(nameField.getText()).toString());
					} else {
						println("No profile with that name in the database");
					}
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
	
	private void addChangeStatusField() {
		changeStatusField = new JTextField(TEXT_FIELD_SIZE);
		changeStatusField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fieldIsNotEmpty(changeStatusField)) {
					/* Stub */
					println("Changed status to: " + changeStatusField.getText() + ".");
				}
			}
		});
		add(changeStatusField, WEST);
	}
	
	private void addChangeStatusButton() {
		changeStatusButton = new JButton("Change Status");
		changeStatusButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fieldIsNotEmpty(changeStatusField)) {
					/* Stub */
					println("Changed status to: " + changeStatusField.getText() + ".");
				}
			}
		});
		add(changeStatusButton, WEST);
	}
	
	private void addChangePictureField() {
		changePictureField = new JTextField(TEXT_FIELD_SIZE);
		changePictureField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fieldIsNotEmpty(changePictureField)) {
					/* Stub */
					println("Changed picture to: " + changePictureField.getText() + ".");
				}
			}
		});
		add(changePictureField, WEST);
	}
	
	private void addChangePictureButton() {
		changePictureButton = new JButton("Change Picture");
		changePictureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fieldIsNotEmpty(changePictureField)) {
					/* Stub */
					println("Changed picture to: " + changePictureField.getText() + ".");
				}
			}
		});
		add(changePictureButton, WEST);
	}
	
	private void addAddFriendField() {
		addFriendField = new JTextField(TEXT_FIELD_SIZE);
		addFriendField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fieldIsNotEmpty(addFriendField)) {
					/* Stub */
					println("Added friend: " + addFriendField.getText() + ".");
				}
			}
		});
		add(addFriendField, WEST);
	}
	
	private void addAddFriendButton() {
		addFriendButton = new JButton("Add Friend");
		addFriendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fieldIsNotEmpty(addFriendField)) {
					/* Stub */
					println("Added friend: " + addFriendField.getText() + ".");
				}
			}
		});
		add(addFriendButton, WEST);
	}
    
    private boolean fieldIsNotEmpty(JTextField field) {
    	return field.getText().length() != 0;
    }

}
