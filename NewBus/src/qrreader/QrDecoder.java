package qrreader;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrDecoder implements IQrDecoder {
	@SuppressWarnings("unchecked")
	public String decode(BufferedImage image) {
		String charset = "UTF-8"; // or "ISO-8859-1"
		Map hintMap = new HashMap();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
		Result qrCodeResult = null;
		try {
			qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return qrCodeResult.getText();
	}
}
