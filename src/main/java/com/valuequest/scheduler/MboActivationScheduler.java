package com.valuequest.scheduler;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.valuequest.entity.MboOperation;
import com.valuequest.services.ClientService;
import com.valuequest.services.OperationService;

public class MboActivationScheduler {
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	OperationService operationService;

	public void process() {
		Calendar calendarnow	= Calendar.getInstance();
		Calendar calendarexe	= null;
		MboOperation operation 	= operationService.getByDaysCode(calendarnow.get(Calendar.DAY_OF_WEEK));
		if (operation != null) {
			String hoursStart	= operation.getHoursStart();
			String hoursEnd		= operation.getHoursEnd();
			
			if (StringUtils.isNotBlank(hoursStart)) {
				calendarexe 		= Calendar.getInstance();
				String[] time_arr 	= hoursStart.split(":");
				calendarexe.set(Calendar.HOUR_OF_DAY, Integer.valueOf(time_arr[0]));
				calendarexe.set(Calendar.MINUTE, Integer.valueOf(time_arr[1]));
				calendarexe.set(Calendar.SECOND, 0);
				calendarexe.set(Calendar.MILLISECOND, 0);
				
				try {
					Thread.sleep(calendarexe.getTimeInMillis() - calendarnow.getTimeInMillis());
					
					clientService.sendActCodeMbo();
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if (StringUtils.isNotBlank(hoursEnd)) {
				calendarnow			= Calendar.getInstance();
				calendarexe			= Calendar.getInstance();
				String[] time_arr	= hoursEnd.split(":");
				calendarexe.set(Calendar.HOUR_OF_DAY, Integer.valueOf(time_arr[0]));
				calendarexe.set(Calendar.MINUTE, Integer.valueOf(time_arr[1]));
				calendarexe.set(Calendar.SECOND, 0);
				calendarexe.set(Calendar.MILLISECOND, 0);
				
				try {
					Thread.sleep(calendarexe.getTimeInMillis() - calendarnow.getTimeInMillis());
					
					clientService.deactivateMbo();
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
