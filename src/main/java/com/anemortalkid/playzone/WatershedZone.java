package com.anemortalkid.playzone;

import static com.anemortalkid.imgutils.Imshow.imshow;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.anemortalkid.imgutils.ImgHelper;
import com.anemortalkid.imgutils.MatHelper;

public class WatershedZone {
	static final Random randy = new Random();
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) throws MalformedURLException,
			IllegalArgumentException {

		Mat img = ImgHelper.toMatrix("shirts/shirt7-1.jpg");
		// Mat img = ImgHelper.toMatrix("water_coins.jpg");
		Mat gray = MatHelper.toGrayscale(img);
		Mat src_thresh = MatHelper.emptyMat(gray);
		double val = Imgproc.threshold(gray, src_thresh, 100.0, 255,
				Imgproc.THRESH_BINARY_INV + Imgproc.THRESH_OTSU);
		imshow("Src_Tresh", src_thresh);

		// Noise removal
		Mat kernel = MatOfPoint2f.eye(new Size(3, 3), CvType.CV_8U);
		Mat opening = MatHelper.emptyMat(src_thresh);
		Imgproc.morphologyEx(src_thresh, opening, Imgproc.MORPH_OPEN, kernel);
		imshow("Opening", opening);

		// sure background area
		Mat sure_bg = MatHelper.emptyMat(opening);
		Imgproc.dilate(opening, sure_bg, kernel, new Point(-1, -1), 3);
		imshow("sure_bg", sure_bg);

		// find sure foreground
		Mat dist_transform = MatHelper.emptyMat(opening);
		Imgproc.distanceTransform(opening, dist_transform, Imgproc.CV_DIST_L2,
				5);
		Mat sure_fg = MatHelper.emptyMat(opening);
		MinMaxLocResult minMaxLoc = Core.minMaxLoc(dist_transform);
		double maxVal = minMaxLoc.maxVal;
		double ret = Imgproc.threshold(dist_transform, sure_fg, maxVal * .07,
				255, Imgproc.THRESH_BINARY);

		Mat dist_transform_cv = MatHelper.emptyMat(dist_transform);
		dist_transform.convertTo(dist_transform_cv, sure_bg.type());
		imshow("DistanceTransform", dist_transform_cv);

		Mat sure_fg_cv = MatHelper.emptyMat(sure_fg);
		sure_fg.convertTo(sure_fg_cv, sure_bg.type());
		imshow("sure_fg", sure_fg_cv);

		// find unknown region
		Mat unknown = MatHelper.emptyMat(dist_transform);
		Core.subtract(sure_bg, sure_fg_cv, unknown);

		// Find contours
		List<MatOfPoint> contours = new ArrayList<>();
		Mat hierarchy = MatHelper.emptyMat(sure_fg_cv);
		Imgproc.findContours(sure_fg_cv, contours, hierarchy,
				Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

		Mat markers = MatHelper.emptyMat(sure_fg_cv, CvType.CV_8UC3);
		for (int i = 0; i < contours.size(); i++) {
			Scalar color = new Scalar(randy.nextInt(256), randy.nextInt(256),
					randy.nextInt(256));
			Imgproc.drawContours(markers, contours, i, color, 2, 8, hierarchy,
					0, new Point());
		}
		imshow("Contours", markers);

		Mat drawing_cv = MatHelper.toGrayscale(markers);
		imshow("drawing_cv", drawing_cv);
		drawing_cv.convertTo(drawing_cv, CvType.CV_32S);
		imshow("img", img);
		Mat graysc = MatHelper.toGrayscale(img);
		imshow("graysc", graysc);
		Imgproc.watershed(img, drawing_cv);
		imshow("target", img);

		Mat mulResult = MatHelper.emptyMat(img);
		Core.multiply(sure_fg_cv, graysc, mulResult);
		imshow("mulresult", mulResult);
		imshow("ColorMult", colorMultiplied(sure_fg_cv, img));
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
	
	private static Mat colorMultiplied(Mat fgMask, Mat img) {
		Mat colorThresh = MatHelper.emptyMat(img);

		// row, cols
		for (int r = 0; r < img.rows(); r++) {
			for (int c = 0; c < img.cols(); c++) {
				double[] ds = fgMask.get(r, c);
				double val = ds[0];

				// Means we chopu
				if (val == 0.0) {
					colorThresh.put(r, c, 255.0, 255.0, 255.0);
				} else {
					colorThresh.put(r, c, img.get(r, c));
				}
			}
		}

		return colorThresh;
	}
}
