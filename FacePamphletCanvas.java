/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		GLabel message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		double x = getMessageXCoordinate(message);
		double y = getMessageYCoordinate(message);
		
		removeOldMessage();
		add(message, x, y);
	}
	
	/* Checks if there is a message already displayed for the user at the bottom of the canvas.
	 * If there is, removes it. Otherwise, does nothing. */
	private void removeOldMessage() {
		GObject oldMessage = getElementAt(getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
		if (oldMessage != null) {
			remove(oldMessage);
		}
	}
	
	/* Returns the x coordinate for a GLabel that will make it centered on the screen */
	private double getMessageXCoordinate(GLabel message) {
		return (getWidth() - message.getWidth()) / 2;
	}
	
	private double getMessageYCoordinate(GLabel message) {
		return (getHeight() - BOTTOM_MESSAGE_MARGIN);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		if (profile != null) {
			displayName(profile.getName());
			if (profile.getImage() == null) {
				drawPlaceholderImageBox();
			} else {
				displayImage(profile.getImage());
			}
			displayStatus(profile.getName(), profile.getStatus());
			displayFriends(profile);
		}
		
	}
	
	private void displayFriends(FacePamphletProfile profile) {
		Iterator<String> iterator = profile.getFriends();
		int i = 0;
		while (iterator.hasNext()) {
			String friendName = iterator.next();
			GLabel friendLabel = new GLabel(friendName, LEFT_MARGIN + IMAGE_WIDTH + FRIENDS_SIDE_MARGIN, FRIENDS_TOP_MARGIN + (i * SPACE_BETWEEN_FRIENDS));
			friendLabel.setFont(PROFILE_FRIEND_FONT);
			add(friendLabel);
			i++;
		}
	}
	
	/* Displays the status for a profile. Called in displayProfile which is used to refresh a profile whenever any changes are made.
	 * If the profile has no current status, displays "No current status" where the status should be displayed. */
	private void displayStatus(String name, String status) {
		GLabel statusLabel;
		if (status.equals("")) {
			statusLabel = new GLabel("No current status");
		} else {
			statusLabel = new GLabel(name + " is " + status);
		}
		statusLabel.setFont(PROFILE_STATUS_FONT);
		add(statusLabel, LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN);
	}
	
	private void drawPlaceholderImageBox() {
		GRect noImage = new GRect(LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN, IMAGE_WIDTH, IMAGE_HEIGHT);
		add(noImage);
		GLabel noImageLabel = new GLabel("No Picture");
		noImageLabel.setFont(PROFILE_IMAGE_FONT);
		add(noImageLabel, (LEFT_MARGIN + (IMAGE_WIDTH - noImageLabel.getWidth()) / 2), TOP_MARGIN + IMAGE_MARGIN + ((IMAGE_HEIGHT + noImageLabel.getHeight()) / 2));
	}
	
	private void displayName(String name) {
		GLabel nameLabel = new GLabel(name);
		nameLabel.setFont(PROFILE_NAME_FONT);
		nameLabel.setColor(PROFILE_NAME_FONT_COLOR);
		add(nameLabel, LEFT_MARGIN, TOP_MARGIN);
	}
	
	private void displayImage(GImage image) {
//		double width = image.getWidth();
//		double height = image.getHeight();
		
		// TODO: scale the provided image to fit :) <3
		
		image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
		add(image, LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN);
	}

	
}
