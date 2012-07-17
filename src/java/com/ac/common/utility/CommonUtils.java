package com.ac.common.utility;

import java.util.Collection;


public class CommonUtils {
	public static <T> void addIfNotNull(Collection<T> collection, T element){
		if(element != null){
			collection.add(element);	
		}
	}
	
	public static boolean numberIsNotNullAndNotZero(Number number){
		return number != null && number.intValue() != 0;
	}
	
	public static boolean isNumberEven(Number number){
		return number.intValue() %2 == 0;
	}
	
	public static boolean isNumberOdd(Number number){
		return number.intValue() %2 != 0;
	}
}
