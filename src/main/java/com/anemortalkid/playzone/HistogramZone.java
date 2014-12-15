package com.anemortalkid.playzone;

import java.net.MalformedURLException;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.anemortalkid.imgutils.ImgCache;
import com.anemortalkid.imgutils.ImgHelper;
import com.anemortalkid.imgutils.MatHelper;
import static com.anemortalkid.imgutils.Imshow.*;

public class HistogramZone {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	public static void main(String[] args) throws MalformedURLException, IllegalArgumentException {
		Mat src = ImgCache.SHIRT1_1;
		Mat src_gray = MatHelper.toGrayscale(src);
		Mat dst = MatHelper.emptyMat(src_gray);
		
		Imgproc.equalizeHist(src_gray, dst);
		
		imshow("src", src);
		imshow("src_gray", src_gray);
		imshow("dst", dst);
	}
}
