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
				else {
					boxIsEmptyMessage();
				}
			}
		});
		add(addButton, NORTH);
	}
	
	private void boxIsEmptyMessage() {
		canvas.showMessage("You must type something in the field before clicking the button.");
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
						profileDeletedMessage(nameField.getText());
						database.deleteProfile(nameField.getText());
						System.out.println("The profile was deleted.");
						currentProfile = null;
					} else {
						System.out.println("That profile doesn't exist in the database.");
						profileDoesNotExistMessage(nameField.getText());
					}
					System.out.println("Current profile is: " + currentProfile);
				} else {
					boxIsEmptyMessage();
				}
			}
		});
		add(deleteButton, NORTH);
	}
	
	private void profileDeletedMessage(String profileName) {
		canvas.showMessage(profileName + "'s profile was deleted.");
	}
	
	private void profileDoesNotExistMessage(String profileName) {
		canvas.showMessage("A profile for " + profileName + " does not exist in the database.");
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
						lookedUpUserMessage(nameField.getText());
						currentProfile = database.getProfile(nameField.getText());
					} else {
						System.out.println("No profile with that name in the database");
						profileDoesNotExistMessage(nameField.getText());
						currentProfile = null;
					}
					System.out.println("Current profile is: " + currentProfile);
				} else {
					canvas.displayProfile(currentProfile);
					boxIsEmptyMessage();
				}
			}
		});
		add(lookupButton, NORTH);
	}
	
	private void lookedUpUserMessage(String profileName) {
		canvas.showMessage("Looked up user: " + profileName + ".");
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
    
    /* Checks that there is a profile selected, then sets the status for it and tells the user that it is doing that.
     * If there is no profile selected and the user tries to change their status, tells them to add or lookup a profile
     * NOTE: still works if the changeStatusField is empty, which allows the user to change their status to "No current status" */
    @Override
    public void changeStatus() {
			if (currentProfile != null) {
				currentProfile.setStatus(changeStatusField.getText());
				System.out.println("Status has been updated to: " + changeStatusField.getText());
				updatedStatusMessage(changeStatusField.getText());
			} else if (currentProfile == null) {
				System.out.println("No profile selected. Add or lookup a profile to change their status");
				noProfileSelectedCannotChangeStatusMessage();
			}
			System.out.println("Current profile is: " + currentProfile);
	}
    
    private void updatedStatusMessage(String status) {
    	if (status.equals("")) {
    		canvas.showMessage("You now have no current status.");
    	} else {
    		canvas.showMessage("Status has been updated to: " + status);
    	}
    }
    
    private void noProfileSelectedCannotChangeStatusMessage() {
    	canvas.showMessage("No profile selected. Add or lookup a profile to change their status");
    }
    
    @Override
    public void changePicture() {
    	if (fieldIsNotEmpty(changePictureField)) {
			if (currentProfile != null) {
				GImage image = null;
				try {
					image = new GImage(changePictureField.getText());
					System.out.println("That is an image! It will be added.");
					existingImageMessage();
					currentProfile.setImage(image);
				} catch (ErrorException ex) {
					System.out.println("That file doesn't exist! Try again.");
					badImageNameMessage(changePictureField.getText());
				}
			} else if (currentProfile == null) {
				System.out.println("No profile selected. Add or lookup a profile to change their picture");
				noProfileSelectedCannotChangePictureMessage();
			}
			System.out.println("Current profile is: " + currentProfile);
		} else {
			boxIsEmptyMessage();
		}
    }
    
    private void existingImageMessage() {
    	canvas.showMessage("Image added for user: " + currentProfile.getName());
    }
    
    private void badImageNameMessage(String filename) {
    	canvas.showMessage("A file by the name of " + filename + " doesn't exist. Try again.");
    }
    
    private void noProfileSelectedCannotChangePictureMessage() {
    	canvas.showMessage("No profile selected. Add or lookup a profile to change their picture");
    }
    
    @Override
    public void addFriend() {
    	if (fieldIsNotEmpty(addFriendField)) {
			/* Stub */
			if (currentProfile != null) {
				if (!currentProfile.getName().equals(addFriendField.getText())) {
					if (database.containsProfile(addFriendField.getText())) {
						if (currentProfile.addFriend(addFriendField.getText())) {
							System.out.println(addFriendField.getText() + " added as a friend.");
							friendAddedMessage(addFriendField.getText());
							FacePamphletProfile profile = database.getProfile(addFriendField.getText());
							profile.addFriend(currentProfile.getName());
						} else {
							System.out.println("You are already friends with " + addFriendField.getText() + "!");
							alreadyFriendsMessage(addFriendField.getText());
						}
					} else {
						System.out.println("That profile doesn't exist, so we can't add them as your friend.");
						profileDoesNotExistCannotAddFriendMessage(addFriendField.getText());
					}
				} else {
					cannotAddSelfAsFriendMessage();
				}
			} else {
				lookupProfileToAddFriendMessage();
			}
			System.out.println("Current profile is: " + currentProfile);
		} else {
			boxIsEmptyMessage();
		}
    }
    
    private void friendAddedMessage(String friendName) {
    	canvas.showMessage(friendName + " has been added as a friend.");
    }
    
    private void alreadyFriendsMessage(String friendName) {
    	canvas.showMessage("You are already friends with " + friendName + "!");
    }
    
    private void profileDoesNotExistCannotAddFriendMessage(String profileName) {
    	canvas.showMessage("A profile for " + profileName + " doesn't exist, so you can't add them as your friend.");
    }
    
    private void lookupProfileToAddFriendMessage() {
    	canvas.showMessage("You must lookup a profile or add a new one before you can add friends!");
    }
    
    private void cannotAddSelfAsFriendMessage() {
    	canvas.showMessage("You can't add yourself as a friend!");
    }
}