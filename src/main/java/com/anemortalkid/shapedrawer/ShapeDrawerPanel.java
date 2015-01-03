package com.anemortalkid.shapedrawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

import javax.swing.JPanel;

/**
 * A {@link ShapeDrawerPanel} is a simple panel which can draw a given
 * {@link ShapeDefinition}
 * 
 * @author jan_monterrubio
 * 
 */
public class ShapeDrawerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7231613407437500394L;
	private ShapeDefinition shapeDefinition;
	private Color fillColor;

	/**
	 * Constructs a {@link ShapeDrawerPanel} with a given {@link DefinedShapes}
	 * and a Color
	 * 
	 * @param definedShape
	 *            the shape that has been defined
	 * @param fillColor
	 *            the color to fill the shape with
	 */
	public ShapeDrawerPanel(DefinedShapes definedShape, Color fillColor) {
		this(definedShape.getDefinition(), fillColor);
	}

	/**
	 * Constructs a {@link ShapeDrawerPanel} with a given ShapeDefinition and a
	 * Color
	 * 
	 * @param shapeDefinition
	 *            the ShapeDefinition to draw
	 * @param fillColor
	 *            the color to fill the shape
	 */
	public ShapeDrawerPanel(ShapeDefinition shapeDefinition, Color fillColor) {
		this.shapeDefinition = shapeDefinition;
		this.fillColor = fillColor;
		this.setBackground(Color.WHITE);
	}

	@Override
	public void paintComponent(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;
		BasicStroke bs = new BasicStroke(14, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND);
		g2d.setStroke(bs);
		GeneralPath gp = shapeDefinition.getPath();
		g2d.setColor(Color.BLACK);
		g2d.draw(gp);
		g2d.setColor(fillColor);
		g2d.fill(gp);
	}

}
