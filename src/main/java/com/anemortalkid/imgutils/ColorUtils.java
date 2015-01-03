package com.anemortalkid.imgutils;

import java.awt.Color;

import org.opencv.core.Mat;

public class ColorUtils {

	/**
	 * Calculates the average RGB value for all the colors within a Mat and
	 * returns it as a single {@link Color}. The Mat should have WHITE pixels
	 * for colors that shouldn't be counted
	 * 
	 * @param whiteBGMat
	 *            the Mat which should have its average colors calculated
	 * @return the
	 */
	public static Color getAverageColorForMat(Mat whiteBGMat) {
		double redVal = 0;
		double greenVal = 0;
		double blueVal = 0;

		int colorCounts = 0;

		for (int r = 0; r < whiteBGMat.rows(); r++) {
			for (int c = 0; c < whiteBGMat.cols(); c++) {
				double[] vals = whiteBGMat.get(r, c);

				double red = vals[0];
				double green = vals[1];
				double blue = vals[2];
				if (red == 255.0 && green == 255.0 && blue == 255.0)
					continue;

				redVal += red;
				greenVal += green;
				blueVal += blue;
				colorCounts++;
			}
		}

		int redAvg = (int) (redVal / colorCounts);
		int greenAvg = (int) (greenVal / colorCounts);
		int blueAvg = (int) (blueVal / colorCounts);
		return new Color(redAvg, greenAvg, blueAvg);
	}
}
