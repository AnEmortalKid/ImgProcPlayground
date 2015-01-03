package com.anemortalkid.playzone;

import static com.anemortalkid.imgutils.Imshow.imshow;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.anemortalkid.imgutils.ImageConstants;
import com.anemortalkid.imgutils.ImgHelper;
import com.anemortalkid.imgutils.MatHelper;

/**
 * Based on:
 * http://docs.opencv.org/doc/tutorials/imgproc/imgtrans/canny_detector
 * /canny_detector.html
 * 
 * @author jan_monterrubio This class finds contours on a given image, colors
 *         those contours and then creates bounding boxes around them.
 * 
 *         I'm thinking that maybe if I find the contours, I can build a cookie
 *         cutter shape and cut the shape out and then figure out the color and
 *         what not.
 */
public class ContourZone {
	static final Random randy = new Random();
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) throws MalformedURLException,
			IllegalArgumentException, URISyntaxException {
		Mat src = ImageConstants.SHIRT3_1;

		// convert to gray and blur
		Mat src_gray = MatHelper.toGrayscale(src);
		Imgproc.blur(src_gray, src_gray, new Size(3, 3));
		imshow("GrayScale", src_gray);

		// create matrix and use canny
		Mat canny_output = MatHelper.emptyMat(src_gray);
		Imgproc.Canny(src_gray, canny_output, 100, 150, 3, false);
		imshow("CannyOut", canny_output);

		List<MatOfPoint> contours = drawContours(src_gray, canny_output);
		drawContourBBox(canny_output, contours);
	}

	private static List<MatOfPoint> drawContours(Mat src_gray, Mat canny_output) {
		// Find contours
		List<MatOfPoint> contours = new ArrayList<>();
		Mat hierarchy = MatHelper.emptyMat(src_gray);
		Imgproc.findContours(canny_output, contours, hierarchy,
				Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

		Mat drawing = MatHelper.emptyMat(canny_output, CvType.CV_8UC3);
		for (int i = 0; i < contours.size(); i++) {
			Scalar color = new Scalar(randy.nextInt(256), randy.nextInt(256),
					randy.nextInt(256));
			Imgproc.drawContours(drawing, contours, i, color, 2, 8, hierarchy,
					0, new Point());
		}
		imshow("Contours", drawing);
		return contours;
	}

	private static void drawContourBBox(Mat canny_output,
			List<MatOfPoint> contours) {
		// For each contour found draw bounding box
		MatOfPoint2f approxCurve = new MatOfPoint2f();
		Mat contoursFrame = MatHelper.emptyMat(canny_output);
		for (int i = 0; i < contours.size(); i++) {
			// Convert contours(i) from MatOfPoint to MatOfPoint2f
			MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());
			// Processing on mMOP2f1 which is in type MatOfPoint2f
			double approxDistance = Imgproc.arcLength(contour2f, true) * 0.02;
			Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

			// Convert back to MatOfPoint
			MatOfPoint points = new MatOfPoint(approxCurve.toArray());

			// Get bounding rect of contour
			Rect rect = Imgproc.boundingRect(points);

			// draw enclosing rectangle (all same color, but you could use
			// variable i to make them unique)
			Core.rectangle(contoursFrame, new Point(rect.x, rect.y), new Point(
					rect.x + rect.width, rect.y + rect.height), new Scalar(255,
					0, 0), 3);
		}
		imshow("Contours Frame", contoursFrame);
	}
}
