package com.valuequest.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Generator {
	
	public static String generate(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] input = str.getBytes();
			md.update(input, 0, input.length);

			byte[] mdbytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "-";
		}
	}
}
