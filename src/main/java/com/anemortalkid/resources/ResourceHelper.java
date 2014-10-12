package com.anemortalkid.resources;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * The {@link ResourceHelper} encapsulates the handling of resources
 * 
 * @author jan_monterrubio
 * 
 */
public class ResourceHelper {

	private static final String RESOURCES_DIR = "src/main/resources/";

	/**
	 * Attempts to load a resource contained within the folder structure
	 * {@code src/main/resources/}. The path {@code src/main/resources/} will be
	 * appended to the resourceName in order to form a full path.
	 * 
	 * @param resourceName
	 *            the name of a resource to load, relative to
	 *            src/main/resources/
	 * @return a URL which points to the resource with the given resourceName
	 * @throws IllegalArgumentException
	 *             if the resource did not exist
	 * @throws MalformedURLException
	 *             when {@link URI#toURL()} would throw the exception
	 */
	public static URL loadResource(String resourceName)
			throws MalformedURLException, IllegalArgumentException {
		String fullPath = RESOURCES_DIR + resourceName;
		File f = new File(fullPath);
		if (!f.exists())
			throw new IllegalArgumentException("Could not find resource: "
					+ resourceName + " located at " + fullPath);
		URI uri = f.toURI();
		URL url = uri.toURL();
		return url;
	}

	/**
	 * Returns the relative path from src/main/resources to the given
	 * resourceName
	 * 
	 * @param resourceName
	 *            the subpath for the resource
	 * @return a relative path to src/main/resources
	 */
	public static String getResourcePath(String resourceName) {
		return RESOURCES_DIR + resourceName;
	}

}
