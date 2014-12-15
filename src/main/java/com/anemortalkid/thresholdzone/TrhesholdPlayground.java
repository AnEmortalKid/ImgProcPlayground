package com.anemortalkid.thresholdzone;

import java.net.MalformedURLException;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.anemortalkid.imgutils.ImgHelper;
import com.anemortalkid.imgutils.Imshow;

/**
 * A place to play with thresholds
 * 
 * @author jan_monterrubio
 * 
 */
public class TrhesholdPlayground {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		Mat source = null;
		try {
			source = ImgHelper.toMatrix("shirts/shirt1-1.jpg");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Mat destination = new Mat(source.rows(), source.cols(), source.type());
		Imgproc.threshold(source, destination, 127, 255, Imgproc.THRESH_TOZERO);
		Imshow.imshow("Thresh To Zero", destination);
		Mat destination2 = new Mat(source.rows(), source.cols(), source.type());
		Imgproc.threshold(source, destination2, 127, 255, Imgproc.THRESH_BINARY);
		Imshow.imshow("Thresh To Binary", destination2);
	}
}
