package com.anemortalkid.shapedrawer;

import java.awt.geom.GeneralPath;

/**
 * Defines a TShirt
 * 
 * @author jan_monterrubio
 * 
 */
public class TShirtDefinition implements ShapeDefinition {

	@Override
	public GeneralPath getPath() {
		GeneralPath gp = new GeneralPath();
		gp.moveTo(6, 102);
		gp.lineTo(165, 6);
		gp.lineTo(260, 6);
		gp.curveTo(320, 36, 320, 36, 382, 6);
		gp.lineTo(475, 6);
		gp.lineTo(634, 106);
		gp.lineTo(560, 218);
		gp.lineTo(478, 167);
		gp.lineTo(478, 508);
		gp.lineTo(157, 508);
		gp.lineTo(157, 172);
		gp.lineTo(70, 215);
		gp.lineTo(6, 102);
		return gp;
	}
}
