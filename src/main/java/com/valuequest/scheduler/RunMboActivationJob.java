package com.valuequest.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class RunMboActivationJob extends QuartzJobBean {

	private MboActivationScheduler activationTask;

	public void setActivationTask(MboActivationScheduler activationTask) {
		this.activationTask = activationTask;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		activationTask.process();
	}

}
