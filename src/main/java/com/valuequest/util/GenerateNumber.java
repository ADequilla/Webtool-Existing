package com.valuequest.util;

import java.util.Random;

public class GenerateNumber {
	
	private static Random random = new Random(System.currentTimeMillis());

	public static String GenerateRandomNumber(int charLength) {
		return String.valueOf(charLength < 1 ? 0 : new Random().nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1) + (int) Math.pow(10, charLength - 1));
	}

	public static String generate(String bin, int length) {
		int randomNumberLength	= length - (bin.length() + 1);
		StringBuilder builder	= new StringBuilder(bin);
		try {
			for (int i = 0; i < randomNumberLength; i++) {
				int digit = random.nextInt(10);
				builder.append(digit);
			}
			Thread.sleep(100);

			int checkDigit = getCheckDigit(builder.toString());
			builder.append(checkDigit);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return builder.toString();
	}

	private static int getCheckDigit(String number) {
		int sum = 0;
		for (int i = 0; i < number.length(); i++) {
			int digit = Integer.parseInt(number.substring(i, (i + 1)));
			if ((i % 2) == 0) {
				digit = digit * 2;
				if (digit > 9) {
					digit = (digit / 10) + (digit % 10);
				}
			}
			sum += digit;
		}

		int mod = sum % 10;
		return ((mod == 0) ? 0 : 10 - mod);
	}
}
