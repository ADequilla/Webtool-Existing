package com.valuequest.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;

public class Constantas {
	public static SimpleDateFormat fdatetime 	= new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	public static SimpleDateFormat fdate 		= new SimpleDateFormat("dd-MMM-yyyy");
	public static SimpleDateFormat ftimestamp	= new SimpleDateFormat("dd MMM yyyy - HH:mm",Locale.ENGLISH);
	public static SimpleDateFormat ftime 		= new SimpleDateFormat("HH:mm:ss");
	public static SimpleDateFormat ffilename	= new SimpleDateFormat("ddMMyyyy-HHmmss");
	public static SimpleDateFormat fdatetime2 	= new SimpleDateFormat("dd-MMM-yyyy HH:mm");
	
	
	public static final String DATETIME_FORMAT_ISO	= "yyyyMMddHHmm";
	public static final String APP_MSG_ACTAGNT_001	= "Cannot retrive your account information, please try again or contact Customer Service for futrher help";
	public static final String APP_MSG_ACTAGNT_004 	= "Cannot create E-Wallet Account,  please contact Konek2Card bank admin for help and information";
	
	public static Date atEndOfDay(Date date) {
	    return DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1);
	}

	public static Date atStartOfDay(Date date) {
	    return DateUtils.truncate(date, Calendar.DATE);
	}
}
