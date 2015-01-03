package com.anemortalkid.playzone;

import java.net.MalformedURLException;
import java.util.LinkedList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.anemortalkid.imgutils.ImageConstants;
import com.anemortalkid.imgutils.ImgHelper;
import com.anemortalkid.imgutils.MatHelper;
import static com.anemortalkid.imgutils.Imshow.*;

public class HistogramZone {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	public static void main(String[] args) throws MalformedURLException, IllegalArgumentException {
		Mat src = ImageConstants.SHIRT3_1;
		Mat src_gray = MatHelper.toGrayscale(src);
		Mat dst = MatHelper.emptyMat(src_gray);
		imshow("src", src);
		
		//Calculate histogram
		java.util.List<Mat> matList = new LinkedList<Mat>();
		matList.add(src);
		Mat histogram = new Mat();
		MatOfFloat ranges=new MatOfFloat(0,256);
		MatOfInt histSize = new MatOfInt(255);
		Imgproc.calcHist(
		                matList, 
		                new MatOfInt(0), 
		                new Mat(), 
		                histogram , 
		                histSize , 
		                ranges);

		// Create space for histogram image
		Mat histImage = Mat.zeros( 100, (int)histSize.get(0, 0)[0], CvType.CV_8UC1);
		// Normalize histogram                          
		Core.normalize(histogram, histogram, 1, histImage.rows() , Core.NORM_MINMAX, -1, new Mat() );   
		// Draw lines for histogram points
		for( int i = 0; i < (int)histSize.get(0, 0)[0]; i++ )
		{                   
		        double histVal = histogram.get(i,0)[0];
				Core.line(
		                histImage,
		                new org.opencv.core.Point( i, histImage.rows() ),
		                new org.opencv.core.Point( i, histImage.rows()-Math.round( histVal )) ,
		                new Scalar( 255, 255, 255),
		                1, 8, 0 );
		}
		
		imshow("Histogram", histImage);
	}
}
