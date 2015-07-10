package com.anemortalkid.playzone;

import java.net.MalformedURLException;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.anemortalkid.imgutils.ImgHelper;
import com.anemortalkid.imgutils.Imshow;

public class TemplateMatcherDemo {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) throws MalformedURLException,
			IllegalArgumentException {
		run();
	}

	private static void run() throws MalformedURLException,
			IllegalArgumentException {
		Mat input = ImgHelper.toMatrix("demo/lena.png");
		Mat img = ImgHelper.toMatrix("demo/lena.png");
		Mat templ = ImgHelper.toMatrix("demo/template.png");
		
		int match_method = Imgproc.TM_SQDIFF;

		// / Create the result matrix
		int result_cols = img.cols() - templ.cols() + 1;
		int result_rows = img.rows() - templ.rows() + 1;
		Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);

		// / Do the Matching and Normalize
		Imgproc.matchTemplate(img, templ, result, match_method);
		Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());

		// / Localizing the best match with minMaxLoc
		MinMaxLocResult mmr = Core.minMaxLoc(result);

		Point matchLoc;
		if (match_method == Imgproc.TM_SQDIFF
				|| match_method == Imgproc.TM_SQDIFF_NORMED) {
			matchLoc = mmr.minLoc;
		} else {
			matchLoc = mmr.maxLoc;
		}

		// / Show me what you got
		Imgproc.rectangle(img, matchLoc, new Point(matchLoc.x + templ.cols(),
				matchLoc.y + templ.rows()), new Scalar(0, 255, 0));

		// Save the visualized detection.
		Imshow.imshow("Input", input);
		Imshow.imshow("Output", img);
	}

}
