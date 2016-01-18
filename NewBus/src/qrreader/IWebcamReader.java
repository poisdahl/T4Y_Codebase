package qrreader;

import java.awt.image.BufferedImage;

interface IWebcamReader {
	// possible transform image using ImageIO
	BufferedImage readImageFromCam();
}
