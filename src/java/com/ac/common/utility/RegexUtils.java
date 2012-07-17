package com.ac.common.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

	public static final String MSISDN_PATTERN="^(5[0345]\\d *[0123456789]\\d{2} *\\d{2} *\\d{2})$";
	public static final String EMAIL_PATTERN = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
	
	public static boolean isInEmailPattern(String candidateEmail){
		return patternMatches(candidateEmail, EMAIL_PATTERN);
	}

	public static boolean isInMsisdnPattern(String candidateMsisdn){
		return patternMatches(candidateMsisdn, MSISDN_PATTERN);
	}
	
	private static boolean patternMatches(String candidate, String regex){
		Pattern pattern = Pattern.compile(regex);  
		Matcher matcher = pattern.matcher(candidate);  
		return matcher.matches();
	}
}
