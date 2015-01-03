package com.anemortalkid.shapedrawer;

/**
 * The DefinedShapes enumerates all the shapes we have concrete definitions of
 * 
 * @author jan_monterrubio
 * 
 */
public enum DefinedShapes {

	TSHIRT(new TShirtDefinition());

	private ShapeDefinition shapeDefinition;

	/**
	 * Creates a DefinedShape
	 * 
	 * @param shapeDefinition
	 *            the definition for the shape
	 */
	private DefinedShapes(ShapeDefinition shapeDefinition) {
		this.shapeDefinition = shapeDefinition;
	}

	/**
	 * @return the {@link ShapeDefinition} that defines this DefinedShape
	 */
	public ShapeDefinition getDefinition() {
		return shapeDefinition;
	}
}
