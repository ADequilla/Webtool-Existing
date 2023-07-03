package com.valuequest.util;

public class DoubleUtil {

	public static Double StringToDouble(String data) {
		try {
			if (data == null)
				return null;
			return Double.valueOf(data);
		} catch (Exception e) {
			return null;
		}
	}
}
