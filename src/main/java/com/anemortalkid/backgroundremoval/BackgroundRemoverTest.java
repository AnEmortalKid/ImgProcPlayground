package com.anemortalkid.backgroundremoval;

import java.awt.Color;
import java.awt.Dimension;
import java.net.MalformedURLException;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import com.anemortalkid.imgutils.ImgHelper;
import com.anemortalkid.imgutils.Imshow;
import com.anemortalkid.imgutils.MatHelper;

public class BackgroundRemoverTest {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) throws MalformedURLException, IllegalArgumentException {
		Mat img = ImgHelper.toMatrix("shirts/shirt6-1.jpg");
		Imshow.imshow("Original", img);
		Mat removed = BackgroundRemover.removeBackground(img);
		Imshow.imshow("Removed", removed);
		
		ShapeDrawer sd = new ShapeDrawer(averageColor(removed));
		sd.setTitle("Average Color");
		sd.setSize(new Dimension(600,600));
		sd.setVisible(true);
		sd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static Color averageColor(Mat singleColor)
	{
		double redVal = 0;
		double greenVal=0;
		double blueVal=0;
		
		int colorCounts=0;
		
		for(int r = 0; r < singleColor.rows(); r++)
		{
			for(int c =0; c < singleColor.cols(); c++)
			{
				double[] vals = singleColor.get(r, c);
				
				double red = vals[0];
				double green = vals[1];
				double blue = vals[2];
				if(red == 255.0 && green == 255.0 && blue == 255.0)
					continue;
				
				redVal+= red;
				greenVal+=green;
				blueVal+=blue;
				colorCounts++;
			}
		}
		
		int redAvg = (int) (redVal / colorCounts);
		int greenAvg =(int) (greenVal / colorCounts);
		int blueAvg = (int) (blueVal / colorCounts);
		return new Color(redAvg, greenAvg, blueAvg);
	}
}