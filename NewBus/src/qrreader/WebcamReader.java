package qrreader;

import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.Webcam;

public class WebcamReader implements IWebcamReader {

	@Override
	public BufferedImage readImageFromCam() {
		Webcam webcam = Webcam.getDefault();
		webcam.open();
		return webcam.getImage();
	}


}
