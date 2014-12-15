package com.anemortalkid.playzone;

import java.net.MalformedURLException;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.anemortalkid.imgutils.ImgHelper;
import static com.anemortalkid.imgutils.Imshow.*;


public class WatershedSegmenter {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public Mat markers = new Mat();

	public void setMarkers(Mat markerImage) {
		markerImage.convertTo(markers, CvType.CV_32S);
	}

	public Mat process(Mat image) {
		Imgproc.watershed(image, markers);
		markers.convertTo(markers, CvType.CV_8U);
		return markers;
	}
	
	public static void main(String[] args) throws MalformedURLException,
			IllegalArgumentException {
		Mat img = ImgHelper.toMatrix("shirts/shirt3-1.jpg");

		Mat threeChannel = new Mat();
		Imgproc.cvtColor(img, threeChannel, Imgproc.COLOR_BGR2GRAY);
		Imgproc.threshold(threeChannel, threeChannel, 100, 255,
				Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);

		Mat fg = new Mat(img.size(), CvType.CV_8U);
		Imgproc.erode(threeChannel, fg, new Mat(), new Point(-1, -1), 12);

		Mat bg = new Mat(img.size(), CvType.CV_8U);
		Imgproc.dilate(threeChannel, bg, new Mat(), new Point(-1, -1), 12);
		Imgproc.threshold(bg, bg, 1, 128, Imgproc.THRESH_BINARY_INV);

		Mat markers = new Mat(img.size(), CvType.CV_8U, new Scalar(0));
		Core.add(fg, bg, markers);
		
		WatershedSegmenter segmenter = new WatershedSegmenter();
		segmenter.setMarkers(markers);
		Mat result = segmenter.process(img);	
	
		Mat subtract = new Mat(img.size(), CvType.CV_8U, new Scalar(0));
		Core.subtract(fg, bg, subtract);
		Imgproc.dilate(subtract, subtract, new Mat(), new Point(-1,-1), 12);
		
		imshow("result", result);
		imshow("subtract", subtract);
	}



}
