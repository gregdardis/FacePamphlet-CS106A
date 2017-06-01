/*
 * File: FacePamphletProfile.java
 * ------------------------------
 * This class keeps track of all the information for one profile
 * in the FacePamphlet social network.  Each profile contains a
 * name, an image (which may not always be set), a status (what 
 * the person is currently doing, which may not always be set),
 * and a list of friends.
 */

/* Give FPP a file path instead of an image then make the image when you call getimage. */
import acm.graphics.*;
import acm.util.ErrorException;

import java.io.IOException;
import java.util.*;

public class FacePamphletProfile implements FacePamphletConstants {
	
	/* Instance variables */
	private String name;
	private String status = "";
	private String imageFilename = "";
	private GImage image;
	private ArrayList<String> friendList = new ArrayList<String>();
	
	/** 
	 * Constructor.
	 * Takes a name and makes it the name for the profile.
	 */
	public FacePamphletProfile(String name) {
		this.name = name;
	}
	
	public FacePamphletProfile(String name, String status, String imageFilename) {
		this.name = name;
		this.status = status;
		setImageFilename(imageFilename);
	}

	/** This method returns the name associated with the profile. */ 
	public String getName() {
		return name;
	}

	/** 
	 * This method returns the image associated with the profile.  
	 * If there is no image associated with the profile, the method
	 * returns null. 
	 */ 
	/* TODO: see what happens if you return new GImage and imageFilename isn't an image file. */
	public GImage getImage() {
		if (imageFilename == null || imageFilename.equals("")) {
			return null;
		}
			image = new GImage(imageFilename);
			return image;
	}

	/** 
	 * Tries to create a new GImage using a filename. If the filename doesn't exist,
	 * does not set imageFilename to be filename, and doesn't change image.
	 * 
	 * If the filename is valid, changes imageFilename to be filename, and makes
	 * a new GImage and sets image equal to this new GImage.
	 */ 
	public boolean setImageFilename(String filename) {
		try {
			image = new GImage(filename);
		} catch (ErrorException e) {
			return false;
		}
		this.imageFilename = filename;	
		return true;
	}
	
	/** 
	 * This method returns the status associated with the profile.
	 * If there is no status associated with the profile, the method
	 * returns the empty string ("").
	 */ 
	public String getStatus() {
		return status;
	}
	
	/** This method sets the status associated with the profile. */ 
	public void setStatus(String status) {
		this.status = status;
	}

	/** 
	 * This method adds the named friend to this profile's list of 
	 * friends.  It returns true if the friend's name was not already
	 * in the list of friends for this profile (and the name is added 
	 * to the list).  The method returns false if the given friend name
	 * was already in the list of friends for this profile (in which 
	 * case, the given friend name is not added to the list of friends 
	 * a second time.)
	 */
	public boolean addFriend(String friend) {
		if (friendList.contains(friend)) {
			return false;
		}
		friendList.add(friend);
		return true;
	}

	/** 
	 * This method removes the named friend from this profile's list
	 * of friends.  It returns true if the friend's name was in the 
	 * list of friends for this profile (and the name was removed from
	 * the list).  The method returns false if the given friend name 
	 * was not in the list of friends for this profile (in which case,
	 * the given friend name could not be removed.)
	 */
	public boolean removeFriend(String friend) {
		if (friendList.contains(friend)) {
			friendList.remove(friendList.indexOf(friend));
			return true;
		}
		return false;
	}
	
	public void setFriendList(ArrayList<String> friendList) {
		this.friendList = friendList;
	}

	/** 
	 * This method returns an iterator over the list of friends 
	 * associated with the profile.
	 */ 
	public Iterator<String> getFriends() {
		return friendList.listIterator();
	}
	
	/** 
	 * This method returns a string representation of the profile.  
	 * This string is of the form: "name (status): list of friends", 
	 * where name and status are set accordingly and the list of 
	 * friends is a comma separated list of the names of all of the 
	 * friends in this profile.
	 * 
	 * For example, in a profile with name "Alice" whose status is 
	 * "coding" and who has friends Don, Chelsea, and Bob, this method 
	 * would return the string: "Alice (coding): Don, Chelsea, Bob"
	 */ 
	public String toString() {
		if (friendList.size() == 0) {
			return name + " (" + status + "): You have no friends";
		}
		
		String listOfNames = friendList.get(0);
		for (int i = 1; i < friendList.size(); i++) {
			listOfNames = listOfNames + ", " + friendList.get(i);
		}
		
		return name + " (" + status + "): " + listOfNames;
	}
	
}
