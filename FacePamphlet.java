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
		
//		addActionListeners();
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
		changeStatusField = new JTextField(TEXT_FIELD_SIZE);
		changeStatusField.addActionListener(this);
		changeStatusButton = new JButton("Change Status");
		add(changeStatusField, WEST);
		add(changeStatusButton, WEST);
	}
	
	private void addChangePictureInteractors() {
		changePictureField = new JTextField(TEXT_FIELD_SIZE);
		changePictureField.addActionListener(this);
		changePictureButton = new JButton("Change Picture");
		add(changePictureField, WEST);
		add(changePictureButton, WEST);
	}
	
	private void addAddFriendInteractors() {
		addFriendField = new JTextField(TEXT_FIELD_SIZE);
		addFriendField.addActionListener(this);
		addFriendButton = new JButton("Add Friend");
		add(addFriendField, WEST);
		add(addFriendButton, WEST);
	}
    
    private boolean fieldIsNotEmpty(JTextField field) {
    	return field.getText().length() != 0;
    }

}
