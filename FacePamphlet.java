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

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		this.resize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		createNorthController();
		createWestController();
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
					println("Added: " + nameField.getText() + ".");
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
					println("Deleted: " + nameField.getText() + ".");
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
					println("Looked up: " + nameField.getText() + ".");
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
		addChangePictureInteractors();
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
