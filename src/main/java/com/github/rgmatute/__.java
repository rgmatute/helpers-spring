package com.github.rgmatute;

public class __ {
	
	public static String logLocation() { 
		StackTraceElement[] current = Thread.currentThread().getStackTrace();
		return " - Location: " + current[2].getClassName() + "::" + current[2].getMethodName() +" - Line: " + current[2].getLineNumber(); 
	}
}
