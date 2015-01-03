package com.anemortalkid.haarplayzone;

import java.net.MalformedURLException;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.objdetect.CascadeClassifier;

import com.anemortalkid.imgutils.ImgHelper;
import com.anemortalkid.imgutils.Imshow;
import com.anemortalkid.resources.ResourceHelper;

/**
 * Uses the provided banana_classifier.xml to find bananas in given images
 * 
 * @author jan_monterrubio
 * @see http
 *      ://coding-robin.de/2013/07/22/train-your-own-opencv-haar-classifier.html
 */
public class HaarBananaZone {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) throws MalformedURLException,
			IllegalArgumentException {

		String classifierPath = ResourceHelper
				.getResourcePath("classifiers/banana_classifier.xml");
		CascadeClassifier cc = new CascadeClassifier(classifierPath);
		Mat banTest = ImgHelper.toMatrix("banana_test/banOnTable.jpg");
		Imshow.imshow("banTest", banTest);
		MatOfRect objects = new MatOfRect();
		cc.detectMultiScale(banTest, objects);
		System.out.println("Found: " + objects.elemSize() + " bans");
		Rect[] rects = objects.toArray();
		System.out.println("Rects:" + rects.length);
		for (Rect rect : rects) {
			Point origin = new Point(rect.x, rect.y);
			Point bound = new Point(rect.x + rect.width, rect.y + rect.height);
			Core.rectangle(banTest, origin, bound, new Scalar(255.0, 0, 0), 3);
		}
		Imshow.imshow("banAfter", banTest);
	}

}
