package com.anemortalkid.imgutils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 * Imshow is a java based implementation of the OpenCV imshow() function, since
 * OpenCV for java did not include this function under {@link Highgui}, or
 * {@link Imgproc}, I decided to write my own. It basically just renders a
 * BufferedImage on a JFrame
 * 
 * @author jan_monterrubio
 * 
 */
public class Imshow {

	private static final int DEF_HEIGHT = 800;
	private static final int DEF_WIDTH = 600;

	/**
	 * Displays the given {@link Mat} in a frame of the default size
	 * 
	 * @param matrix
	 *            the {@link Mat} to display
	 */
	public static void imshow(Mat matrix) {
		BufferedImage image = ImgHelper.toBufferedImage(matrix);
		imshow(image);
	}

	/**
	 * Displays the given {@link BufferedImage} in a frame of the default size
	 * 
	 * @param image
	 *            the {@link BufferedImage} to display
	 */
	public static void imshow(BufferedImage image) {
		JFrame imshowFrame = getFrameWithImage(image);
		imshowFrame.setVisible(true);
	}

	/**
	 * Displays the given {@link Mat} in a frame of the default size with the
	 * given title
	 * 
	 * @param title
	 *            the title of the frame
	 * @param matrix
	 *            the {@link Mat} to display
	 */
	public static void imshow(String title, Mat matrix) {
		BufferedImage image = ImgHelper.toBufferedImage(matrix);
		imshow(title, image);
	}

	/**
	 * Displays the given {@link BufferedImage} in a frame of the default size
	 * with the given title
	 * 
	 * @param title
	 *            the title of the frame
	 * @param image
	 *            the {@link BufferedImage} to display
	 */
	public static void imshow(String title, BufferedImage image) {
		JFrame imshowFrame = getFrameWithImage(image);
		imshowFrame.setTitle(title);
		imshowFrame.setVisible(true);
	}

	/*
	 * Constructs a JFrame with an ImShowPanel and returns it so we can
	 * customize it further
	 */
	private static JFrame getFrameWithImage(BufferedImage image) {
		ImgShowPanel panel = new ImgShowPanel(image);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		frame.setSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));
		return frame;
	}

	/**
	 * An {@link ImgShowPanel} is a {@link JPanel} which renders the given
	 * {@link BufferedImage} to the size of its container
	 * 
	 * @author jan_monterrubio
	 * 
	 */
	private static class ImgShowPanel extends JPanel {

		/**
		 * Generated Serial Version
		 */
		private static final long serialVersionUID = -9051069073482301836L;

		/**
		 * Image to reference
		 */
		private BufferedImage image;

		/**
		 * Constructs an {@link ImgShowPanel} with the given
		 * {@link BufferedImage}
		 * 
		 * @param image
		 *            the {@link BufferedImage} to display within this
		 *            {@link ImgShowPanel}
		 */
		private ImgShowPanel(BufferedImage image) {
			this.image = image;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}
}
