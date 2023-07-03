package com.valuequest.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;

import org.springframework.web.multipart.MultipartFile;

public class CsvUtils {
	public static int getRowCount(MultipartFile file) {
		Reader fr;
		int linenumber = 0;
		try {
			fr = new InputStreamReader(file.getInputStream());
			LineNumberReader lnr = new LineNumberReader(fr);

			while (lnr.readLine() != null) {
				linenumber++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return linenumber;
	}
}
