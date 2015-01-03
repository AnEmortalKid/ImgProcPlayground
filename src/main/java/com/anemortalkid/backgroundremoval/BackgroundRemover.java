package com.anemortalkid.backgroundremoval;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.anemortalkid.imgutils.MatHelper;

/**
 * Performs removing of the background for a given image
 * 
 * @author jan_monterrubio
 * 
 */
public class BackgroundRemover {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static Mat removeBackground(Mat src) {

		Mat src_gray = MatHelper.toGrayscale(src);
		Mat src_thresh = MatHelper.emptyMat(src_gray);
		Imgproc.threshold(src_gray, src_thresh, 0.0, 255,
				Imgproc.THRESH_BINARY_INV + Imgproc.THRESH_OTSU);

		// Noise removal
		Mat kernel = MatOfPoint2f.eye(new Size(2, 2), CvType.CV_8U);
		Mat opening = MatHelper.emptyMat(src_thresh);
		Imgproc.morphologyEx(src_thresh, opening, Imgproc.MORPH_OPEN, kernel);

		// sure background area
		Mat sure_bg = MatHelper.emptyMat(opening);
		Imgproc.dilate(opening, sure_bg, kernel, new Point(-1, -1), 3);

		// find sure foreground
		Mat dist_transform = MatHelper.emptyMat(opening);
		Imgproc.distanceTransform(opening, dist_transform, Imgproc.CV_DIST_L2,
				5);
		Mat sure_fg = MatHelper.emptyMat(opening);
		MinMaxLocResult minMaxLoc = Core.minMaxLoc(dist_transform);
		double maxVal = minMaxLoc.maxVal;
		Imgproc.threshold(dist_transform, sure_fg, maxVal * .07, 255,
				Imgproc.THRESH_BINARY);
		Mat sure_fg_cv = MatHelper.emptyMat(sure_fg);
		sure_fg.convertTo(sure_fg_cv, sure_bg.type());
		return colorMultiplied(sure_fg_cv, src);
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
