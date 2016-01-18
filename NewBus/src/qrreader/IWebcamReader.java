package qrreader;

import java.awt.image.BufferedImage;

public interface IWebcamReader {
	// possible transform image using ImageIO
	BufferedImage readImageFromCam();
}
