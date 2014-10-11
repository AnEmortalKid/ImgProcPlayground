package com.anemortalkid.resources;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

/**
 * {@link ResourceHelperTest} runs tests to ensure {@link ResourceHelper} is
 * working properly
 * 
 * @author jan_monterrubio
 * 
 */
public class ResourceHelperTest {

	/**
	 * Loads a test resource and confirms it doesn't return null
	 */
	@Test
	public void testLoadResource() throws MalformedURLException {
		String testPath = "testResource.txt";
		URL testUrl = ResourceHelper.loadResource(testPath);
		assertNotNull(testUrl);
	}

	/**
	 * Loads a fake resource that doesn't exist
	 * 
	 * @throws MalformedURLException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testLoadFakeResource() throws MalformedURLException {
		String fakePath = "hello.txt";
		ResourceHelper.loadResource(fakePath);
	}
}
