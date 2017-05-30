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

import org.sqlite.core.DB;

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
	
	/* Instance variables of other classes */
	private FacePamphletProfile currentProfile = null;
	private ProfileManager database;
	private FacePamphletCanvas canvas = new FacePamphletCanvas();
	private Database db = new Database();
	private ProfilesDataSource profilesDataSource = new ProfilesDataSource(db);
	private FriendsDataSource friendsDataSource = new FriendsDataSource(db);

	/**
	 * This method has the responsibility for initializing the interactors in the application
	 * resizing the window, and adding the canvas for the profiles to be drawn on.
	 */
	public void init() {
		this.resize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		createNorthController();
		createWestController();
		add(canvas);
		db.connect();
		profilesDataSource.createTable();
		friendsDataSource.createTable();
		database = new ProfileManager(profilesDataSource);
    }
	
	/** Runs long enough after init that the message will be printed to the canvas. 
	 * If instead noProfileSelectedMessage was called right after add(canvas) in init,
	 * nothing would be printed to the canvas. */
	public void run() {
		noProfileSelectedMessage();
	}
	
	/** Called before any profiles have been added, upon startup of the app. */
	private void noProfileSelectedMessage() {
		canvas.showMessage("No profile selected. Please look one up or add a new one.");
	}
	
	/** Adds the interactors to the NORTH controller region. */
	private void createNorthController() {
		addLabelAndTextField();
		addThreeButtonsNorth();
	}
	
	/** Adds NORTH textfield and label for it. */
	private void addLabelAndTextField() {
		add(new JLabel("Name"), NORTH);
		nameField = new JTextField(TEXT_FIELD_SIZE);
		nameField.addActionListener(this);
		add(nameField, NORTH);
	}
	
	/** Adds the "Add" button to the NORTH controller region, and controls what that button does if clicked.
	 * If the field isn't empty but the profile named in the field already exists,
	 * the user is informed and that profile is displayed.
	 * Otherwise, creates a new profile with that name, adds it to the database, and displays it. */
	private void addAddButton() {
		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fieldIsNotEmpty(nameField)) {
					if (database.containsProfile(nameField.getText())) {
						profileAlreadyExistsMessage();
						currentProfile = database.getProfile(nameField.getText());
						canvas.displayProfile(currentProfile);
					} else {
						FacePamphletProfile profile = new FacePamphletProfile(nameField.getText());
						database.addProfile(profile);
						addNewProfileMessage(profile);
						currentProfile = profile;
						canvas.displayProfile(currentProfile);
					}
				}
				else {
					boxIsEmptyMessage();
				}
			}
		});
		add(addButton, NORTH);
	}
	
	/** Called whenever the user presses enter or clicks a button with an empty field. */
	private void boxIsEmptyMessage() {
		canvas.showMessage("You must type something in the field before clicking the button.");
	}
	
	/** Called if the user tries to create a profile with a name that already exists. */
	private void profileAlreadyExistsMessage() {
		canvas.showMessage("A profile with that name already exists.");
	}
	
	/** Called when a new profile is created. */
	private void addNewProfileMessage(FacePamphletProfile profile) {
		canvas.showMessage("Added new profile named: " + profile.getName() + ".");
	}
	
	/** Adds the "Delete" button to the NORTH controller region, and controls what that button does if clicked.
	 * If the field isn't empty and contains the name of an existing profile when the button is clicked,
	 * deletes that profile and prints to the screen what happened to the user.
	 * Otherwise, prints to the screen that the profile they tried to delete does not exist. */
	private void addDeleteButton() {
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fieldIsNotEmpty(nameField)) {
					if (database.containsProfile(nameField.getText())) {
						profileDeletedMessage(nameField.getText());
						database.deleteProfile(nameField.getText());
						currentProfile = null;
						canvas.displayProfile(currentProfile);
					} else {
						profileDoesNotExistMessage(nameField.getText());
					}
				} else {
					boxIsEmptyMessage();
				}
			}
		});
		add(deleteButton, NORTH);
	}
	
	/** Called when a profile gets deleted. */
	private void profileDeletedMessage(String profileName) {
		canvas.showMessage(profileName + "'s profile was deleted.");
	}
	
	/** Called when the user tries to delete a profile that does not exist. */
	private void profileDoesNotExistMessage(String profileName) {
		canvas.showMessage("A profile for " + profileName + " does not exist in the database.");
	}
	
	/** Adds the "Lookup" button to the NORTH controller region, and controls what that button does if clicked.
	 * If the field isn't empty and the profile name typed into the field exists, displays the profile
	 * associated with that name.
	 * Otherwise, lets the user know the profile they tried to lookup does not exist.
	 * If the user presses Lookup but the field is empty, continues to display the profile that was being
	 * displayed before, and tells the user the box is empty. */
	private void addLookupButton() {
		lookupButton = new JButton("Lookup");
		lookupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fieldIsNotEmpty(nameField)) {
					if (database.containsProfile(nameField.getText())) {
						lookedUpUserMessage(nameField.getText());
						currentProfile = database.getProfile(nameField.getText());
						canvas.displayProfile(currentProfile);
					} else {
						profileDoesNotExistMessage(nameField.getText());
						currentProfile = null;
					}
				} else {
					canvas.displayProfile(currentProfile);
					boxIsEmptyMessage();
				}
			}
		});
		add(lookupButton, NORTH);
	}
	
	/** Displayed when a user is looked up. */
	private void lookedUpUserMessage(String profileName) {
		canvas.showMessage("Looked up user: " + profileName + ".");
	}
	
	/** Adds three button interactors to the NORTH controller region. */
	private void addThreeButtonsNorth() {
		addAddButton();
		addDeleteButton();
		addLookupButton();
	}
	
	/** Adds the six interactors to the WEST controller region, spaced out in groups of 2.  */
	private void createWestController() {
		addChangeStatusInteractors();
		addEmptySpace();
		addChangePictureInteractors();
		addEmptySpace();
		addAddFriendInteractors();
	}
	
	/** Called to create the WEST controller. */
	private void addChangeStatusInteractors() {
		addChangeStatusField();
		addChangeStatusButton();
	}
	
	/** Called to create the WEST controller. */
	private void addChangePictureInteractors() {
		addChangePictureField();
		addChangePictureButton();
	}
	
	/** Called to create the WEST controller. */
	private void addAddFriendInteractors() {
		addAddFriendField();
		addAddFriendButton();
	}
	
	/** Called to create the WEST controller. */
	private void addEmptySpace() {
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
	}
	
	/**
	 * Makes a new ChangeStatusListener for this field, and the FacePamphlet class itself is a StatusChanger (interface),
	 * so when actionPerformed is called, FacePamphlet calls changeStatus and since it is a StatusChanger it changes the
	 * status of the currentProfile. The rest of the methods below this are analogous */
	private void addChangeStatusField() {
		changeStatusField = new JTextField(TEXT_FIELD_SIZE);
		changeStatusField.addActionListener(new ChangeStatusListener(changeStatusField, this));
		add(changeStatusField, WEST);
	}
	
	/**
	 * Makes a new ChangeStatusListener for this button, and the FacePamphlet class itself is a StatusChanger (interface),
	 * so when actionPerformed is called, FacePamphlet calls changeStatus and since it is a StatusChanger it changes the
	 * status of the currentProfile. */
	private void addChangeStatusButton() {
		changeStatusButton = new JButton("Change Status");
		changeStatusButton.addActionListener(new ChangeStatusListener(changeStatusField, this));
		add(changeStatusButton, WEST);
	}
	
	/**
	 * Makes a new ChangePictureListener for this field, and the FacePamphlet class itself is a PictureChanger (interface),
	 * so when actionPerformed is called, FacePamphlet calls changePicture and since it is a PictureChanger it changes the
	 * picture of the currentProfile. */
	private void addChangePictureField() {
		changePictureField = new JTextField(TEXT_FIELD_SIZE);
		changePictureField.addActionListener(new ChangePictureListener(changePictureField, this));			
		add(changePictureField, WEST);
	}
	
	/**
	 * Makes a new ChangePictureListener for this button, and the FacePamphlet class itself is a PictureChanger (interface),
	 * so when actionPerformed is called, FacePamphlet calls changePicture and since it is a PictureChanger it changes the
	 * picture of the currentProfile. */
	private void addChangePictureButton() {
		changePictureButton = new JButton("Change Picture");
		changePictureButton.addActionListener(new ChangePictureListener(changePictureField, this));
		add(changePictureButton, WEST);
	}
	
	/**
	 * Makes a new AddFriendListener for this field, and the FacePamphlet class itself is a FriendAdder (interface),
	 * so when actionPerformed is called, FacePamphlet calls addFriend and since it is a FriendAdder it adds 
	 * a friend to the currentProfile. */
	private void addAddFriendField() {
		addFriendField = new JTextField(TEXT_FIELD_SIZE);
		addFriendField.addActionListener(new AddFriendListener(addFriendField, this));
		add(addFriendField, WEST);
	}
	
	/**
	 * Makes a new AddFriendListener for this button, and the FacePamphlet class itself is a FriendAdder (interface),
	 * so when actionPerformed is called, FacePamphlet calls addFriend and since it is a FriendAdder it adds 
	 * a friend to the currentProfile. */
	private void addAddFriendButton() {
		addFriendButton = new JButton("Add Friend");
		addFriendButton.addActionListener(new AddFriendListener(addFriendField, this));
		add(addFriendButton, WEST);
	}
    
	/** Called on fields with actionListeners to see if the field is empty.
	 * Returns true if the field is not empty.
	 * Returns false if the field is empty. */
    public static boolean fieldIsNotEmpty(JTextField field) {
    	return field.getText().length() != 0;
    }
    
    /**
     * Checks that there is a profile selected, then sets the status for it and tells the user that the status has been set
     * If there is no profile selected and the user tries to change their status, tells them to add or lookup a profile.
     * NOTE: still works if the changeStatusField is empty, which allows the user to change their status to "No current status" */
    @Override
    public void changeStatus() {
			if (currentProfile != null) {
				currentProfile.setStatus(changeStatusField.getText());
				updatedStatusMessage(changeStatusField.getText());
				canvas.displayProfile(currentProfile);
				profilesDataSource.changeStatus(currentProfile);
			} else if (currentProfile == null) {
				noProfileSelectedCannotChangeStatusMessage();
			}
	}
    
    /** 
     * Called by changeStatus, when status is changed using the button or the field.
     * Prints what the status has been updated to for the user to see.
     * If passed the empty string, the method prints that they have no current status.  */
    private void updatedStatusMessage(String status) {
    	if (status.equals("")) {
    		canvas.showMessage("You now have no current status.");
    	} else {
    		canvas.showMessage("Status has been updated to: " + status);
    	}
    }
    
    /** Called in changeStatus if no profile is selected when the user tries to change a status. */
    private void noProfileSelectedCannotChangeStatusMessage() {
    	canvas.showMessage("No profile selected. Add or lookup a profile to change their status");
    }
    
    /** Checks to see if a profile is selected, then tries to set a picture by the name of
     * whatever was typed into the "Change Picture" field. If it is contained in the images file,
     * changes the picture to that picture, otherwise prints to the user that the filename
     * does not exist.
     * If the user tries to change a picture with no profile selected, alerts the user of the problem. */
    @Override
    public void changePicture() {
    	if (fieldIsNotEmpty(changePictureField)) {
			if (currentProfile != null) {
				currentProfile.setImageFilename(changePictureField.getText());
				if (currentProfile.getImage() == null) {
					badImageNameMessage(changePictureField.getText());
					return;
				}
				canvas.displayProfile(currentProfile);
				existingImageMessage();
			} else if (currentProfile == null) {
				noProfileSelectedCannotChangePictureMessage();
			}
		} else {
			boxIsEmptyMessage();
		}
    }
    
    /** Called when a picture is added by changePicture. */
    private void existingImageMessage() {
    	canvas.showMessage("Image added for user: " + currentProfile.getName());
    }
    
    /** Called in changePicture if the user tries to add a picture that doesn't exist. */
    private void badImageNameMessage(String filename) {
    	canvas.showMessage("A file by the name of " + filename + " doesn't exist. Try again.");
    }
    
    /** Called in changePicture if a user tries to change the picture without a profile selected. */
    private void noProfileSelectedCannotChangePictureMessage() {
    	canvas.showMessage("No profile selected. Add or lookup a profile to change their picture.");
    }
    
    /** Adds a friend to a profile. Checks if a profile is selected, then checks the user isn't trying to
     * add themselves. Then checks a profile exists for the name they are trying to add. If all of this 
     * passes, a friend is added, otherwise a message is displayed to the user alerting them of the problem.
     * If the user tries to add someone they are already friends with, a message is displayed to that effect. */
    @Override
    public void addFriend() {
    	if (fieldIsNotEmpty(addFriendField)) {
			if (currentProfile != null) {
				if (!currentProfile.getName().equals(addFriendField.getText())) {
					if (database.containsProfile(addFriendField.getText())) {
						if (currentProfile.addFriend(addFriendField.getText())) {
							friendAddedMessage(addFriendField.getText());
							FacePamphletProfile profile = database.getProfile(addFriendField.getText());
							profile.addFriend(currentProfile.getName());
							canvas.displayProfile(currentProfile);
						} else {
							alreadyFriendsMessage(addFriendField.getText());
						}
					} else {
						profileDoesNotExistCannotAddFriendMessage(addFriendField.getText());
					}
				} else {
					cannotAddSelfAsFriendMessage();
				}
			} else {
				lookupProfileToAddFriendMessage();
			}
		} else {
			boxIsEmptyMessage();
			db.close();
		}
    }
    
    /** Called in addFriend when a friend has been successfully added. */
    private void friendAddedMessage(String friendName) {
    	canvas.showMessage(friendName + " has been added as a friend.");
    }
    
    /** Called in addFriend if the user tries to add someone they are already friends with. */
    private void alreadyFriendsMessage(String friendName) {
    	canvas.showMessage("You are already friends with " + friendName + "!");
    }
    
    /** Called in addFriend if the user tries to add a profile that doesn't exist. */
    private void profileDoesNotExistCannotAddFriendMessage(String profileName) {
    	canvas.showMessage("A profile for " + profileName + " doesn't exist, so you can't add them as your friend.");
    }
    
    /** Called in addFriend if the user tries to add a friend without a profile as their current profile. */
    private void lookupProfileToAddFriendMessage() {
    	canvas.showMessage("You must lookup a profile or add a new one before you can add friends!");
    }
    
    /** Called in addFriend if the user tries to add themselves as a friend. */
    private void cannotAddSelfAsFriendMessage() {
    	canvas.showMessage("You can't add yourself as a friend!");
    }
    
    // TODO: make it so if lookupButton is pressed with nothing in the textField, it sets currentprofile to null and updates the displayProfile.
}