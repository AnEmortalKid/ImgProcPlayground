package com.anemortalkid.playzone;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.anemortalkid.imgutils.ImgCache;
import com.anemortalkid.imgutils.MatHelper;

import static com.anemortalkid.imgutils.Imshow.*;

public class ThresholdPlayground {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		Mat src = ImgCache.SHIRT4_1;
		Mat src_gray = MatHelper.toGrayscale(src);
		Mat dst = MatHelper.emptyMat(src_gray);
		Imgproc.adaptiveThreshold(src_gray, dst, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 11, 2);
		
		imshow("src", src_gray);
		imshow("dst", dst);
	}
}
