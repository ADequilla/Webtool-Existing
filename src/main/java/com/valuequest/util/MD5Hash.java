/**
 * 
 */
package com.valuequest.util;

import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.util.encoders.Hex;

/**
 * @author Eki Nurhadi
 *
 */
public class MD5Hash {

	public static String generateMd5(String str) {
		MD5Digest md5 = new MD5Digest();
		byte[] input = str.getBytes();
		md5.update(input, 0, input.length);

		// get the output/ digest size and hash it
		byte[] digest = new byte[md5.getDigestSize()];
		md5.doFinal(digest, 0);

		return new String(Hex.encode(digest));
	}
	
	/**
	 * @param value (plain/text)
	 * @param hash md5
	 *
	 */
	
	public static Boolean checkPw(String value, String hash) {
		if(value != null) {
			if(generateMd5(value).equals(hash)) {
				return true;
			}
		}
		return false;
	}
}
