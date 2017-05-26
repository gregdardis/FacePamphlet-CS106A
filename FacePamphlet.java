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
	private JButton changeStatusButton;
	private JTextField changePictureField;
	private JButton changePictureButton;
	private JTextField addFriendField;
	private JButton addFriendButton;

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		createNorthController();
		createWestController();
		addActionListeners();
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
	
	private void addThreeButtonsNorth() {
		addButton = new JButton("Add");
		deleteButton = new JButton("Delete");
		lookupButton = new JButton("Lookup");
		add(addButton, NORTH);
		add(deleteButton, NORTH);
		add(lookupButton, NORTH);
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
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    	Object source = e.getSource();
    	
    	if (source == addButton && nameField.getText().length() != 0) {
    		/* Stub code */
    		println("Added profile for " + nameField.getText() + ".");
    	}
    	
    	if (source == deleteButton && nameField.getText().length() != 0) {
    		/* Stub code */
    		println("Deleted profile for " + nameField.getText() + ".");
    	}
    	
    	if (source == lookupButton && nameField.getText().length() != 0) {
    		/* Stub code */
    		println("Looked up profile for " + nameField.getText() + ".");
    	}
    	
    	
    	
    	
	}

}
