package com.app.utils;

/**
 * This class contains utility methods for data providers.
 * @author Muana Kimba
 */
public final class DataProviderUtil {

	// CONSTRUCTOR
	private DataProviderUtil() throws Exception {
		throw new Exception();
	}

	// METHODS
	// TODO Parameterize for n null value to add
	public static String[] addNullValueToTestData(String[] readTestdata) {
		String[] testdata = new String[readTestdata.length + 1];
		testdata[0] = null;
		
		// append the readTestdata to the testdata containing a null value
		for(int i = 1, j = 0; i < testdata.length; i++, j++)
			testdata[i] = readTestdata[j];
		
		return testdata;
	}
	
	// TODO Parameterize for n pair of null value to add
	public static String[][] addNullValueToTestData(String[][] readTestdata) {
		String[][] testdata = new String[readTestdata.length + 1][readTestdata[0].length];
		testdata[0][0] = null;	testdata[0][1] = null;
		
		// append the readTestdata to the testdata containing null values
		for(int i = 1, k = 0; i < testdata.length; i++, k++){
			for(int j = 0; j < testdata[0].length; j++)
				testdata[i][j] = readTestdata[k][j];
		}
		
		return testdata;
	}
}