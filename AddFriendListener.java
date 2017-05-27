import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class AddFriendListener implements ActionListener {

	private JTextField addFriendField;
	private FriendAdder friendAdder;
	
	
	public AddFriendListener(JTextField addFriendField, FriendAdder friendAdder) {
		this.addFriendField = addFriendField;
		this.friendAdder = friendAdder;
	}
	
	public interface FriendAdder {
		void addFriend();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		friendAdder.addFriend();
	}

}
