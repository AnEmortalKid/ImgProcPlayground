package com.anemortalkid.backgroundremoval;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShapeDrawer extends JFrame{

	private Color shapeColor;

	public ShapeDrawer(Color shapeColor)
	{
		this.shapeColor = shapeColor;
		add(new ShapeDrawerPanel(shapeColor));
	}
	
	private static class ShapeDrawerPanel extends JPanel {

		private Color shapeColor;

		private ShapeDrawerPanel(Color shapeColor) {
			this.shapeColor = shapeColor;
			this.setBackground(Color.WHITE);
		}

		@Override
		public void paintComponent(Graphics graphics) {
			Graphics2D g2d = (Graphics2D) graphics;
			
			//lets assume it is big enough to fit the shape
			int w = this.getWidth();
			int h = this.getHeight();
			
			//Our regular scale is in 25,25 space, we should multiply
			
			//Draw the outline first
			g2d.setColor(Color.BLACK);
			int wScale = w/25;
			int hScale = h/25;
			Polygon shirt = getPolygon(wScale, hScale);
			g2d.setStroke(new BasicStroke((float) 5.0));
			g2d.drawPolygon(shirt);
			
			g2d.setColor(shapeColor);
			g2d.fillPolygon(shirt);
		}

		private Polygon getPolygon(int wScale, int hScale) {
			Polygon p = new Polygon();
			p.addPoint(5*wScale, 5*hScale);
			p.addPoint(5*wScale,10*hScale);
			p.addPoint(8*wScale,8*hScale);
			p.addPoint(8*wScale,20*hScale);
			p.addPoint(18*wScale,20*hScale);
			p.addPoint(18*wScale,8*hScale);
			p.addPoint(21*wScale,10*hScale);
			p.addPoint(21*wScale,5*hScale);
			p.addPoint(18*wScale,3*hScale);
			p.addPoint(8*wScale,3*hScale);
			p.addPoint(5*wScale,5*hScale);
			return p;
		}

	}
	
	public static void main(String[] args)
	{
		new ShapeDrawer(Color.RED).setVisible(true);
	}

}
