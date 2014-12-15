package com.anemortalkid.playzone;

import java.net.MalformedURLException;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.anemortalkid.imgutils.ImgHelper;
import com.anemortalkid.imgutils.Imshow;
import com.anemortalkid.imgutils.MatHelper;

/**
 * A place to play with images
 * 
 * @author jan_monterrubio
 * 
 */
public class ImgPlayzone {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	// ClassLoader classloader = Thread.currentThread().getContextClassLoader()
	// InputStream is = classloader.getResourceAsStream("test.csv");

	public static void main(String[] args) throws MalformedURLException,
			IllegalArgumentException {

		Mat catMat = null;
		try {
			catMat = ImgHelper.toMatrix("shirts/shirt1-1.jpg");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		Mat grayscale = MatHelper.toGrayscale(catMat);
		Imshow.imshow("Gray Scale", grayscale);

		// threshold the thing
		// thresholdOnRange(grayscale, 0, 0, 5);
		// thresholdOnRange(grayscale, 0, grayscale.cols() - 1, 5);

		Mat clone = emptyMat(grayscale);
		Imgproc.resize(grayscale, clone, new Size(600, 800));
		double colorJumperValue = colorJumpFinder(clone, clone.rows() / 2, 0,
				clone.rows() / 2, clone.cols() / 2);
		Imshow.imshow("Color Jump Finder", grayscale);
		Imshow.imshow("Color Jump Finder Clone", clone);

		Mat noEdgeClone = emptyMat(grayscale);
		Imgproc.resize(grayscale, noEdgeClone, new Size(600, 800));

		System.out.println("Cols: " + noEdgeClone.cols() + " Rows; "
				+ noEdgeClone.rows());
		System.out.println("color jumper value:" + colorJumperValue);
		showThresholds(noEdgeClone, colorJumperValue, 255.0);

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

	public static double colorJumpFinder(Mat matrix, int rowStart,
			int colStart, int endRow, int endCol) {
		double[] mid = matrix.get(endRow-rowStart/2, endCol-colStart/2);
		double startVal = matrix.get(rowStart, colStart)[0];
		double tolerance = Math.abs(mid[0]-startVal);
		double minRange = startVal-tolerance;
		double maxRange = startVal+tolerance;
		double minValFound = startVal;
		Point pointOfJump = null;
		for (int r = rowStart; r <= endRow; r++)
			for (int c = colStart; c <= endCol; c++) {
				double[] data = matrix.get(r, c);
				double dataVal = data[0];
				if (dataVal < minRange || dataVal > maxRange) {
					System.out.println("R,C:" + r + "," + c + "=" + dataVal
							+ ". CloneVal:" + matrix.get(r, c)[0]);
					if (pointOfJump == null) {
						pointOfJump = new Point(c, r);
					}
				} else {
					if (dataVal < minValFound)
						minValFound = dataVal;
				}
			}

		System.out.println("Point of jump:" + pointOfJump);
		Core.rectangle(matrix, new Point(colStart, rowStart), pointOfJump,
				new Scalar(0, 255, 0), 3);
		System.out.println("Mid:"+(endRow-rowStart/2)+","+(endCol-colStart/2) + " MidVal:" + mid[0]);
		return minValFound;
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
	public static void thresholdOnArea(Mat toThreshold, int rowLoc, int colLoc,
			int pixelRange) {
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

		int colLocMinBound = colLoc - pixelRange;
		int colLocMaxBound = colLoc + pixelRange;
		int colLocMin = 0;
		int colLocMax = toThreshold.cols();
		if (colLocMinBound < colLocMin)
			colLocMinBound = colLocMin;
		if (colLocMaxBound > colLocMax)
			colLocMaxBound = colLocMax;
		double minValue = 255.0;
		for (int r = rowLocMinBound; r < rowLocMaxBound; r++)
			for (int c = colLocMinBound; c < colLocMaxBound; c++) {
				double[] pixelVals = toThreshold.get(r, c);
				double pixelValue = pixelVals[0]; // assuming it is gray here
				if (pixelValue < minValue)
					minValue = pixelValue;
			}
		Mat threshBinMat = emptyMat(toThreshold);
		Imgproc.threshold(toThreshold, threshBinMat, minValue, 255.0,
				Imgproc.THRESH_BINARY);
		Imshow.imshow("ThreshBinRange", threshBinMat);
	}

	public static void showThresholds(Mat toThreshold, double value, double max) {
		Mat threshBinMat = emptyMat(toThreshold);
		Imgproc.threshold(toThreshold, threshBinMat, value, max,
				Imgproc.THRESH_BINARY);
		Imshow.imshow("Binary Threshold", threshBinMat);

		Mat threshBinInvMat = emptyMat(toThreshold);
		Imgproc.threshold(toThreshold, threshBinInvMat, value, max,
				Imgproc.THRESH_BINARY_INV);
		Imshow.imshow("Inverted Binary Threshold", threshBinInvMat);

		Mat threshToZero = emptyMat(toThreshold);
		Imgproc.threshold(toThreshold, threshToZero, value, max,
				Imgproc.THRESH_TOZERO);
		Imshow.imshow("To Zero Threshold", threshToZero);

		Mat threshToZeroInv = emptyMat(toThreshold);
		Imgproc.threshold(toThreshold, threshToZeroInv, value, max,
				Imgproc.THRESH_TOZERO_INV);
		Imshow.imshow("Inverted To Zero Threshold", threshToZeroInv);

		Mat threshTrunc = emptyMat(toThreshold);
		Imgproc.threshold(toThreshold, threshTrunc, value, max,
				Imgproc.THRESH_TRUNC);
		Imshow.imshow("Trunc Threshold", threshTrunc);
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
