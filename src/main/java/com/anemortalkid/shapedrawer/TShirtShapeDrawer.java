package com.anemortalkid.shapedrawer;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * A simple frame which can draw a TShirt
 * 
 * @author jan_monterrubio
 * 
 */
public class TShirtShapeDrawer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9057216780889793245L;

	public TShirtShapeDrawer(Color shapeColor) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(660, 560));
		add(new ShapeDrawerPanel(DefinedShapes.TSHIRT, shapeColor));
	}

	public static void main(String[] args) {
		TShirtShapeDrawer sd = new TShirtShapeDrawer(Color.MAGENTA);
		sd.setVisible(true);
	}
}
