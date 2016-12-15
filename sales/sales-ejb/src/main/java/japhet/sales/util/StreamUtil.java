package japhet.sales.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class StreamUtil {

	private Logger logger = Logger.getLogger(getClass());

	@SuppressWarnings("resource")
	public byte[] convertFileTobytesArray(File file) throws IOException {
		logger.info("Converting file to byte array...");
		InputStream is = new FileInputStream(file);
		long length = file.length();
		
		if (length > Integer.MAX_VALUE) {
			throw new IOException("The file is too big for the DB.");
		}
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = is.read(bytes, offset, bytes.length - offset);
		while (offset < bytes.length && numRead != 0) {
			offset += numRead;
			numRead = is.read(bytes, offset, bytes.length - offset);
		}

		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}
		is.close();
		logger.info("File succesfully converted to bytes array.");
		
		return bytes;
	}
	
	public Image convertByteArrayToFile(byte[] bytes) throws IOException {
		logger.info("Converting byte array to image...");
		Image image = null;
		BufferedImage bfImage = null;
		bfImage = ImageIO.read(new ByteArrayInputStream(bytes));
		image = bfImage;
		logger.info("Bytes array succesfully converted to image.");
		return image;
	}
	
}
