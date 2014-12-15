package com.anemortalkid.imgutils;

import java.net.MalformedURLException;

import org.opencv.core.Mat;

/**
 * Constants just for image retrieval
 * @author jan_monterrubio
 *
 */
public class ImgCache {

	public static final Mat SHIRT1_1 = getResource("shirts/shirt1-1.jpg");
	public static final Mat SHIRT2_1 = getResource("shirts/shirt2-1.jpg");
	public static final Mat SHIRT4_1 = getResource("shirts/shirt4-1.jpg");
	public static final Mat SHIRT3_1 = getResource("shirts/shirt3-1.jpg");

	private static final Mat getResource(String resource) {
		try {
			return ImgHelper.toMatrix(resource);
		} catch (MalformedURLException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		System.err.println("Returning Null for resource: " + resource);
		return null;
	}
}
