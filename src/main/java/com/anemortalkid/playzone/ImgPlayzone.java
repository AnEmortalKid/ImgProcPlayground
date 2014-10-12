package com.anemortalkid.playzone;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.anemortalkid.imgutils.ImgHelper;
import com.anemortalkid.imgutils.Imshow;

public class ImgPlayzone {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	// ClassLoader classloader = Thread.currentThread().getContextClassLoader()
	// InputStream is = classloader.getResourceAsStream("test.csv");

	public static void main(String[] args) {

		BufferedImage bi = null;
		try {
			bi = ImgHelper.toBufferedImage("shirts/shirt1-1.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Imshow.show(bi, "BuffImage");
		Mat catMat = null;
		try {
			catMat = ImgHelper.toMatrix("shirts/shirt1-1.jpg");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Imshow.show(catMat, "Cat Mat");
		int rgb = bi.getRGB(0, 0);
		double[] rgb2 = catMat.get(0, 0);
		System.out.println("Rgb:" + rgb);
		System.out.println("[" + getRed(rgb) + "," + getGreen(rgb) +"," + getBlue(rgb) + "Alpha:"+getAlpha(rgb)+"]");
		System.out.println(Arrays.toString(rgb2));
		// Imgproc.erode(catMat, catMat,
		// Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2,2)));
		// Mat grayMat = new Mat(catMat.rows(), catMat.cols(), CvType.CV_8U);
		// Imgproc.cvtColor(catMat, grayMat, Imgproc.COLOR_RGB2GRAY);
		// Imshow.show(grayMat);
		// int height = catMat.cols();
		// int width = catMat.rows();
		// System.out.println("w:" + width + "h:" + height);
		// double[] data = grayMat.get(0, 0);
		// System.out.println("Value:" + Arrays.toString(data));
		// double singleValue = data[0];
		// double[] center = grayMat.get(grayMat.rows() / 2, grayMat.cols() /
		// 2);
		// System.out.println("Center:" + Arrays.toString(center));
		// double singleCenter = center[0];
		// showThresholds(grayMat, singleValue - 20, 255);
		// double bottomRightCorner[] = grayMat.get(grayMat.rows() - 1,
		// grayMat.cols() - 1);
		// double bottomRightConrerVal = bottomRightCorner[0];
		// showThresholds(grayMat, bottomRightConrerVal - 20, 255);
		// Threshold to that value (look at tutorial)
		// See what happens
	}

	public static Mat emptyMat(Mat fromMat) {
		Mat mat = new Mat(fromMat.cols(), fromMat.rows(), fromMat.type());
		return mat;
	}

	/**
	 * Assumes it is one band
	 * 
	 * @param toThreshold
	 * @param rowLoc
	 * @param colLoc
	 * @param pixelRange
	 */
	public static void thresholdOnRange(Mat toThreshold, int rowLoc,
			int colLoc, int pixelRange) {
		List<Double> valuesToThresh = new ArrayList<>();

		/*
		 * 1. Grab pixels within the ranges 2. Get the values and put it in the
		 * thresh thing 3. Min 4. Threshold on min
		 */

		// Calculate "real bounds"
		int rowLocMinBound = rowLoc - pixelRange;
		int rowLocMaxBound = rowLoc + pixelRange;
		int rowLocMin = 0;
		int rowLocMax = toThreshold.rows();
		if (rowLocMinBound < rowLocMin)
			rowLocMinBound = rowLocMin;
		if (rowLocMaxBound > rowLocMax)
			rowLocMaxBound = rowLocMax;

	}

	public static void showThresholds(Mat toThreshold, double value, double max) {
		Mat threshBinMat = emptyMat(toThreshold);
		Imgproc.threshold(toThreshold, threshBinMat, value, max,
				Imgproc.THRESH_BINARY);
		Imshow.show(threshBinMat, "Binary Threshold");

		Mat threshBinInvMat = emptyMat(toThreshold);
		Imgproc.threshold(toThreshold, threshBinInvMat, value, max,
				Imgproc.THRESH_BINARY_INV);
		Imshow.show(threshBinInvMat, "Inverted Binary Threshold");

		Mat threshToZero = emptyMat(toThreshold);
		Imgproc.threshold(toThreshold, threshToZero, value, max,
				Imgproc.THRESH_TOZERO);
		Imshow.show(threshToZero, "To Zero Threshold");

		Mat threshToZeroInv = emptyMat(toThreshold);
		Imgproc.threshold(toThreshold, threshToZeroInv, value, max,
				Imgproc.THRESH_TOZERO_INV);
		Imshow.show(threshToZeroInv, "Inverted To Zero Threshold");

		Mat threshTrunc = emptyMat(toThreshold);
		Imgproc.threshold(toThreshold, threshTrunc, value, max,
				Imgproc.THRESH_TRUNC);
		Imshow.show(threshTrunc, "Trunc Threshold");
	}

	private static int getRed(int argb) {
		int r = (argb) & 0xFF;
		int g = (argb >> 8) & 0xFF;
		int b = (argb >> 16) & 0xFF;
		int a = (argb >> 24) & 0xFF;
		return r;
	}

	private static int getGreen(int argb) {
		int r = (argb) & 0xFF;
		int g = (argb >> 8) & 0xFF;
		int b = (argb >> 16) & 0xFF;
		int a = (argb >> 24) & 0xFF;
		return g;
	}

	private static int getBlue(int argb) {
		int r = (argb) & 0xFF;
		int g = (argb >> 8) & 0xFF;
		int b = (argb >> 16) & 0xFF;
		int a = (argb >> 24) & 0xFF;
		return b;
	}
	
	private static int getAlpha(int argb) {
		int r = (argb) & 0xFF;
		int g = (argb >> 8) & 0xFF;
		int b = (argb >> 16) & 0xFF;
		int a = (argb >> 24) & 0xFF;
		return a;
	}

}
