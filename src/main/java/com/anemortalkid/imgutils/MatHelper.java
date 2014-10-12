package com.anemortalkid.imgutils;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Provides hand matrix operations
 * 
 * @author jan_monterrubio
 * 
 */
public class MatHelper {

	/**
	 * Creates an empty matrix that is the same size as the given matrix and the
	 * same type
	 * 
	 * @param fromMat
	 *            the matrix to use when copying properties
	 * @return a blank matrix of the same size and type
	 */
	public static Mat emptyMat(Mat fromMat) {
		Mat mat = new Mat(fromMat.cols(), fromMat.rows(), fromMat.type());
		return mat;
	}

	/**
	 * Creates an empty matrix that is the same size as the given matrix and the
	 * given type
	 * 
	 * @param fromMat
	 *            the matrix to use when copying properties
	 * @param matType
	 *            the type of the matrix.
	 * @return a blank matrix of the same size and type
	 */
	public static Mat emptyMat(Mat fromMat, int matType) {
		Mat mat = new Mat(fromMat.cols(), fromMat.rows(), matType);
		return mat;
	}

	/**
	 * Converts the given {@link Mat} to a grayscale version
	 * 
	 * @param matrix
	 *            the {@link Mat} to convert to grasycale, in RGB format
	 * @return a grayscale version of the Mat with type CvType.8UC
	 */
	public static Mat toGrayscale(Mat matrix) {
		Mat grayscaleMat = emptyMat(matrix, CvType.CV_8U);
		Imgproc.cvtColor(matrix, grayscaleMat, Imgproc.COLOR_RGB2GRAY);
		return grayscaleMat;
	}

	/**
	 * Converts a BGR Mat to RGB. Since Hihghui always reads as BGR
	 * 
	 * @param mat
	 * @return
	 */
	public static Mat fromBGR2RGB(Mat mat) {
		Mat fixed = new Mat();
		Imgproc.cvtColor(mat, fixed, Imgproc.COLOR_BGR2RGB);
		return fixed;
	}
}
