package com.valuequest.util;

public class SequenceUtil {

	public static String convertToAlpaNumbericSeq(long seq, int numOfAlphabet, int numberOfDigit) {
		String digitSeq = String.format("%0" + numberOfDigit + "d", (seq % (long) Math.pow(10, numberOfDigit)));
		long sisaSeq = (seq / (long) Math.pow(10, numberOfDigit));
		StringBuilder builder = new StringBuilder();

		for (int repeat = numOfAlphabet - 1; repeat >= 0; repeat--) {
			char charN;
			if (repeat == 0)
				charN = (char) ((sisaSeq % 26) + 65);
			else
				charN = (char) (((sisaSeq / (26 * repeat)) % 26) + 65);
			
			builder.append(charN);
		}
		
		builder.append(digitSeq);
		return builder.toString();
	}
}
