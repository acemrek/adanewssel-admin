package com.ac.common.utility;

import org.apache.commons.codec.digest.DigestUtils;

public class HashingUtils {
	
	public static String getMD5Hash(String raw){
		return DigestUtils.md5Hex(raw);
	}
}
