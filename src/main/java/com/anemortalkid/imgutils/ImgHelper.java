package com.anemortalkid.imgutils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;

import com.anemortalkid.resources.ResourceHelper;

/**
 * {@link ImgHelper} contains helper methods that deal with creating and
 * transforming {@link BufferedImage}
 * 
 * @author jan_monterrubio
 * 
 */
public class ImgHelper {

	/**
	 * Converts the desired inputMat to a {@link BufferedImage} so we can see it
	 * visually
	 * 
	 * @param inputMat
	 *            the Matrix to convert to a {@link BufferedImage}
	 * @return a {@link BufferedImage} with the contents of the matrix
	 */
	public static BufferedImage toBufferedImage(Mat inputMat) {
		BufferedImage bi;
		int height = inputMat.rows();
		int width = inputMat.cols();
		byte[] imgData = new byte[(int) (inputMat.cols() * inputMat.rows() * inputMat
				.elemSize())];
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (inputMat.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}

		// Copy the data into the matrix
		inputMat.get(0, 0, imgData);

		bi = new BufferedImage(width, height, type);

		bi.getRaster().setDataElements(0, 0, width, height, imgData);
		return bi;
	}

	/**
	 * Given a filePath, this method returns a BufferedImage loaded with the
	 * information within that file
	 * 
	 * @param filePath
	 *            the path to the file
	 * @return a {@link BufferedImage} with the contents of the image file
	 * @throws IOException
	 *             whenever the file could not be read or the file was corrupted
	 */
	public static BufferedImage toBufferedImage(String filePath)
			throws IOException {
		URL resource = ResourceHelper.loadResource(filePath);
		return ImageIO.read(resource);
	}
	
	public static Mat toMatrix(String filePath) throws MalformedURLException, IllegalArgumentException
	{
		//Works weird on windows, returning blue image :(
		String fullPath = "src/main/resources/" + filePath;
		Mat mat = Highgui.imread(fullPath);
		System.out.println(mat.channels());
		return mat;
	}

	public static Mat toMatrix(BufferedImage bufferedImage) {
		//Works weird, returning blue image :(
		int imageType = bufferedImage.getType();
		System.out.println("ImageType:" + imageType);
		int matrixType = CvType.CV_8UC3;
		if (imageType == BufferedImage.TYPE_BYTE_GRAY)
			matrixType = CvType.CV_8UC1;

		byte[] pixels = ((DataBufferByte) bufferedImage.getRaster()
				.getDataBuffer()).getData();

		Mat matrix = new Mat(new Size(bufferedImage.getWidth(),
				bufferedImage.getHeight()), CvType.CV_8UC3);
		matrix.put(0, 0, pixels);
		return matrix;

		// byte[] pixels = ((DataBufferByte)
		// image.getRaster().getDataBuffer()).getData();
		/*
		 * 2. Then you can simply put it to Mat if you set type to CV_8UC3
		 * 
		 * image_final.put(0, 0, pixels);
		 */

	}
}
