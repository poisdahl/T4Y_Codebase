package qrreader;

import java.awt.image.BufferedImage;

public interface IQrDecoder {
	String decode(BufferedImage image);
}
