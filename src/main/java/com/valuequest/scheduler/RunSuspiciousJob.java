package com.valuequest.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class RunSuspiciousJob extends QuartzJobBean {

	private SuspiciousScheduler suspiciousTask;

	public void setSuspiciousTask(SuspiciousScheduler suspiciousTask) {
		this.suspiciousTask = suspiciousTask;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		suspiciousTask.process();
	}

}
