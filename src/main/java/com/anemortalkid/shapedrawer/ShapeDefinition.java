package com.anemortalkid.shapedrawer;

import java.awt.geom.GeneralPath;

/**
 * Stores the definition of a shape as a GeneralPath
 * 
 * @author jan_monterrubio
 * 
 */
public interface ShapeDefinition {

	/**
	 * Returns a new GeneralPath which represents a given shape
	 * 
	 * @return the GeneralPath that is defined by this {@link ShapeDefinition}
	 */
	GeneralPath getPath();
}
