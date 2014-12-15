package com.anemortalkid.playzone;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.opencv.video.BackgroundSubtractorMOG;

import com.anemortalkid.imgutils.ImgCache;
import com.anemortalkid.imgutils.ImgHelper;

public class BackgroundSubtractionZone {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
}
