package com.valuequest.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.valuequest.entity.Suspicious;
import com.valuequest.entity.ViewCashinSuspicious;
import com.valuequest.entity.ViewTransSuspicious;
import com.valuequest.services.SuspiciousService;

public class SuspiciousScheduler {
	
	@Autowired
	SuspiciousService suspiciousService;
	
	public void process() {
		Suspicious suspicious = null;
		
		List<ViewTransSuspicious> trans = suspiciousService.transList();
		for (ViewTransSuspicious tran : trans) {
			suspicious = new Suspicious();
			suspicious.setCode(tran.getCode());
			suspicious.setTransType(tran.getType());
			suspicious.setTransDesc(tran.getDesc());
			suspicious.setTransDate(tran.getDate());
			suspicious.setTrans(tran.getTrans());
			suspicious.setClientType(tran.getClienType());
			suspicious.setClient(tran.getClient());
			
			suspiciousService.saveOrUpdate(suspicious);
		}
		
		List<ViewCashinSuspicious> cashins = suspiciousService.cashinList();
		String transCode 	= "";
		int totalCashin		= 0;
		for (ViewCashinSuspicious cashin : cashins) {
			
			if (transCode.equals(cashin.getCode())) {
				if ("CASH_IN".equals(cashin.getType())) {
					totalCashin++;
				} else {
					totalCashin = 0;
				}
			} else {
				transCode = cashin.getCode();
				if ("CASH_IN".equals(cashin.getType())) {
					totalCashin = 1;
				} else {
					totalCashin = 0;
				}
			}
			
			if (totalCashin >= 3 && !"CASH_IN".equals(cashin.getType())) {
				suspicious = new Suspicious();
				suspicious.setCode(cashin.getCode());
				suspicious.setTransType("CASH_IN+" + cashin.getType());
				suspicious.setTransDesc(cashin.getDesc());
				suspicious.setTransDate(cashin.getDate());
				suspicious.setTrans(cashin.getTrans());
				suspicious.setClientType(cashin.getClienType());
				suspicious.setClient(cashin.getClient());
				
				suspiciousService.saveOrUpdate(suspicious);
			}
		}
	}
}
