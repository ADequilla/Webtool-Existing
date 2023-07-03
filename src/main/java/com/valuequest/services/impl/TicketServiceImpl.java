package com.valuequest.services.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.customer_service.model.TicketModel;
import com.valuequest.entity.Concern;
import com.valuequest.entity.CsTicket;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.ViewCsTicket;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.TicketService;
import com.valuequest.services.TypeOfConcernService;
import com.valuequest.util.Constantas;
import com.valuequest.util.DateUtil;
import com.valuequest.util.SequenceUtil;

@Service
@Transactional(readOnly = true)
public class TicketServiceImpl extends SimpleServiceImpl<CsTicket> implements TicketService {

	@Autowired
	private TypeOfConcernService concernService;

	@Override
	public Class<CsTicket> getRealClass() {
		return CsTicket.class;
	}

	@Override
	public ViewCsTicket findViewById(Long id) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewCsTicket.class);
		criteria.add(Restrictions.eq("id", id));
		return ((ViewCsTicket) criteria.uniqueResult());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Modifying
	@Override
	public void saveTicket(TicketModel model, SecUser login) {
		CsTicket ticket = null;
		if (model.getId() == null) {
			ticket = new CsTicket();
			ticket.setCreatedBy(login.getId());
			ticket.setCreatedDate(new Date(System.currentTimeMillis()));
			ticket.setReceivedBy(login.getId());
			ticket.setUserType("WEB");
			ticket.setCid(model.getClientCid());
			ticket.setClientType(model.getClientType());
			ticket.setClientName(model.getClientName());
			ticket.setConcernId(model.getConcernId());
			ticket.setTransType(model.getTransType());
			ticket.setAssignedTo(model.getAssignedToId());
			ticket.setActionTaken(model.getAction());
			ticket.setStatus(Lookup.LOOKUP_CS_STATUS_OPEN);
			ticket.setMessage(model.getDetail());
			ticket.setTicketNo(getTicketNumber());
		} else {
			ticket = this.findById(model.getId());
			ticket.setActionTaken(model.getAction());
			ticket.setStatus(Lookup.LOOKUP_CS_STATUS_OPEN);
			if (ticket.getAssignedTo() == null) {
				ticket.setConcernId(model.getConcernId());
				ticket.setTransType(model.getTransType());
				ticket.setAssignedTo(model.getAssignedToId());
			}
		}

		this.saveOrUpdate(ticket);
		model.setId(ticket.getId());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Modifying
	@Override
	public void closeTicket(Long id, SecUser login) {
		if (id != null) {
			Date now = new Date(System.currentTimeMillis());
			CsTicket ticket = this.findById(id);
			ticket.setStatus(Lookup.LOOKUP_CS_STATUS_CLOSE);
			ticket.setClosedDate(now);

			Concern concern = concernService.findById(ticket.getConcernId());
			if (concern != null) {
				String turntime = concern.getTime();
				Long actualtime = (now.getTime() - ticket.getCreatedDate().getTime()) / 60000;
				if (actualtime <= Long.valueOf(turntime)) {
					ticket.setTotalTime(0L);
				} else {
					ticket.setTotalTime(actualtime - Long.valueOf(turntime));
				}
			}

			this.saveOrUpdate(ticket);
		}
	}

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String ticketNo 		= (String) searchMap.get("ticketNo");
		String cid 				= (String) searchMap.get("cid");
		String submittedBy 		= (String) searchMap.get("submittedBy");
		String postedDateStart 	= (String) searchMap.get("postedDateStart");
		String postedDateEnd 	= (String) searchMap.get("postedDateEnd");
		Long concern 			= (Long) searchMap.get("concern");
		String status 			= (String) searchMap.get("status");
		Criteria criteria 		= this.getSessionFactory().getCurrentSession().createCriteria(ViewCsTicket.class);

		if (StringUtils.isNotBlank(ticketNo))
			criteria.add(Restrictions.ilike("ticketNo", ticketNo, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(cid))
			criteria.add(Restrictions.ilike("cid", cid, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(submittedBy))
			criteria.add(Restrictions.ilike("submittedBy", submittedBy, MatchMode.ANYWHERE));

		if (concern != null)
			criteria.add(Restrictions.eq("concernId", concern));

		if (StringUtils.isNotBlank(status))
			criteria.add(Restrictions.eq("statusCode", status));

		if (StringUtils.isNotBlank(postedDateStart)) {
			try {
				Date date = Constantas.fdate.parse(postedDateStart);
				criteria.add(Restrictions.ge("createdDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (StringUtils.isNotBlank(postedDateEnd)) {
			try {
				Date date = Constantas.fdate.parse(postedDateEnd);
				criteria.add(Restrictions.le("createdDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	
	
	@SuppressWarnings("rawtypes")
	private String getTicketNumber() {
		List list = this.getSessionFactory().getCurrentSession().createSQLQuery("select mfs.getcsticketsequence()").list();
		for (Object object : list) {
			Integer sequence = (Integer) object;

			return SequenceUtil.convertToAlpaNumbericSeq(sequence.longValue(), 3, 3);
		}
		
		return null;
	}
}
