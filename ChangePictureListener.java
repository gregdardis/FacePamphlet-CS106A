import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import acm.graphics.GImage;
import acm.util.ErrorException;

public class ChangePictureListener implements ActionListener {

	private JTextField changePictureField;
	private PictureChanger pictureChanger;
	
	public ChangePictureListener(JTextField changePictureField, PictureChanger pictureChanger) {
		this.changePictureField = changePictureField;
		this.pictureChanger = pictureChanger;
	}
	
	public interface PictureChanger {
		void changePicture();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		pictureChanger.changePicture();
	}

}
