package com.valuequest.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaGenerator {
	
	public static String hashSha256(String value){
		try {
			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedHash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(encodedHash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "-";
		}
	}
	
	public static String hashSha512(String value){
		try {
			final MessageDigest digest = MessageDigest.getInstance("SHA-512");
			byte[] encodedHash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(encodedHash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "-";
		}
	}
	
	private static String bytesToHex(byte[] hash) {
		final StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			int decimal = (int) b & 0xff;
			String hex = Integer.toHexString(decimal);
			if (hex.length() == 1) hexString.append('0');
			hexString.append(hex);
		}
		
		return hexString.toString();
	}
}
