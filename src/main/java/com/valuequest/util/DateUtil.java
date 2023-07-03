package com.valuequest.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	public static Date truncate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.AM_PM, Calendar.AM);
		return cal.getTime();
	}
	
	public static Date endOfDay(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}
	
	public static int compare(Date date1, Date date2){
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		cal1.set(Calendar.HOUR, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		cal1.set(Calendar.AM_PM, Calendar.AM);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		cal2.set(Calendar.HOUR, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);
		cal2.set(Calendar.AM_PM, Calendar.AM);
		
		return cal1.compareTo(cal2);
	}
	
	public static Date addMonth(Calendar cal, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(cal.getTime());
		calendar.add(Calendar.MONTH, month);
		
		return calendar.getTime();
	}
	
	public static Date addHour(Calendar cal, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(cal.getTime());
		calendar.add(Calendar.HOUR, hour);
		
		return calendar.getTime();
	}
	
	public static String formatDate(String format, Date date) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			sf.setTimeZone(TimeZone.getDefault());
			return sf.format(date);
		} catch (Exception e) {
			return "";
		}
	}
}
