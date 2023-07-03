package com.valuequest.services;

import java.util.HashMap;

import com.valuequest.common.DataTables;
import com.valuequest.controller.customer_service.model.TicketModel;
import com.valuequest.entity.CsTicket;
import com.valuequest.entity.ViewCsTicket;
import com.valuequest.entity.security.SecUser;

public interface TicketService {

	public CsTicket findById(Long id);

	public ViewCsTicket findViewById(Long id);

	public void saveTicket(TicketModel model, SecUser userLogin);

	public void closeTicket(Long id, SecUser userLogin);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
}
