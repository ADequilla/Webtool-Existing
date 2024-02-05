package com.valuequest.controller.monitoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.entity.StructureInstitution;
import com.valuequest.entity.StructureUser;
import com.valuequest.entity.UserInstitution;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.AgentDashboardTempService;
import com.valuequest.services.SmsLogsService;
import com.valuequest.services.impl.SimpleServiceImpl;

/**
 * 
 * @author Gilang ZW
 *
 */

@Controller
@RequestMapping("/monitoring/agent-dashboard")
public class AgentDashboardController extends BaseController {


	List<String> instiList = new ArrayList<String>();
  
	
	protected SecUser getLoginSecUser(HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		return (SecUser) session.getAttribute("loginSecUser");
	}
	
	private class SimpleServ extends SimpleServiceImpl<StructureUser> implements SmsLogsService{

		
		
		private DetachedCriteria criteriaBy(Long userId) {
			DetachedCriteria criteria = DetachedCriteria.forClass(UserInstitution.class);
			criteria.createAlias("user", "user");
			criteria.createAlias("institution", "institution");
			criteria.add(Restrictions.eq("user.id", userId));
			criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("institution.code")));

			return criteria;
		}
		

		public void getUserInsti(Long userId) {
			
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureInstitution.class);
			criteria.add(Subqueries.propertyIn("code", criteriaBy(userId)));
			List r = criteria.list();
			for (Iterator iterator = r.iterator(); iterator.hasNext();){
				StructureInstitution insti = (StructureInstitution) iterator.next();		
	            instiList.add(insti.getCode());
			}
			
			System.out.println("#####Arrray of Insti#####\n "+  instiList.toString());
			
		}


		@Override
		public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap,
				HttpSession session) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Class<StructureUser> getRealClass() {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
			// TODO Auto-generated method stub
			return null;
		}
		
	
	}

	final static String MENU 		= "MONITORING";
	final static String PRIVILEDGE 	= "AGENT_DASHBOARD";
	private String BASE_VIEW 		= "04.monitoring/";
	private String LIST_VIEW 		= "agent_dashboard_list";
	
	@Autowired
	private AgentDashboardTempService agentDashboardTempService;
	
	@RequestMapping("/")
	public String index( Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		 SecUser user = this.getLoginSecUser(session);

        user.setIsLogin(true);
        adminService.updateCekStatus(user, session.getId());

		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {
			
			putIntoRequest(model);

			return BASE_VIEW + LIST_VIEW;
		}

		return getUnauthorizedPage();
	}
	
	@RequestMapping("/search")
	public @ResponseBody DataTables search(DataTables dataTables, 
			@RequestParam(required = false) String dateStart,
			@RequestParam(required = false) String dateEnd, 
			@RequestParam(required = false) String branch, 
			HttpSession session, HttpServletResponse response) {
				response.setHeader("X-Frame-Options", "DENY");
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("branch", branch);

		return agentDashboardTempService.searchByMapCriteria(dataTables, searchMap);
	}
	
	@RequestMapping("/search-agent")
	public @ResponseBody DataTables searchAgent(DataTables dataTables, 
			@RequestParam(required = false) String dateStart,
			@RequestParam(required = false) String dateEnd, 
			@RequestParam(required = false) String branch, 
			HttpSession session, HttpServletResponse response) {
				response.setHeader("X-Frame-Options", "DENY");
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("branch", branch);

		return agentDashboardTempService.searchByMapCriteriaAgent(dataTables, searchMap);
	}
	
	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
	
}