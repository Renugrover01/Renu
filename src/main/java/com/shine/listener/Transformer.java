package com.shine.listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

@SuppressWarnings("rawtypes") 
public class Transformer implements IAnnotationTransformer {

	// Do not worry about calling this method as testNG calls it behind the scenes before EVERY method (or test).
	// It will disable single tests, not the entire suite like SkipException
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod){

		// If we have chose not to run this test then disable it.
		if (System.getProperty("user.dir").toLowerCase().contains("preprod")&&testClass.getClass().toString().contains("Test_FooterURL")){
			annotation.setEnabled(false);
		}
		
	}

	


}