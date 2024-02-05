package com.valuequest.controller.maintenance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valuequest.entity.SmsLogs;
import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;
import com.valuequest.common.AjaxResponse;
import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.maintenance.model.CenterModel;
import com.valuequest.entity.StructureInstitution;
import com.valuequest.entity.StructureUser;
import com.valuequest.entity.UserInstitution;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.SmsLogsService;
import com.valuequest.services.impl.SimpleServiceImpl;


@Controller
@RequestMapping("/utilities/center")
public class CenterController extends BaseController {


	List<String> instiList = new ArrayList<String>();
  
	
	protected SecUser getLoginSecUser(HttpSession session) {
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
			for (Iterator iterator = (Iterator) r.iterator(); ((java.util.Iterator) iterator).hasNext();){
				StructureInstitution insti = (StructureInstitution) ((java.util.Iterator) iterator).next();		
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

	


	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "CENTER";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "center";
	private String CREATE_VIEW 		= "center-create";

	@RequestMapping("/")
	public String index(Model model, HttpSession session, HttpServletResponse response) {
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
			@RequestParam(required = false) String code,
			@RequestParam(required = false) String description, 
			HttpSession session, HttpServletResponse response) {
				response.setHeader("X-Frame-Options", "DENY");

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("code", code);
		searchMap.put("description", description);

		return centerService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {

			model.addAttribute("isNew", true);
			putIntoRequest(model);

			return BASE_VIEW + CREATE_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/edit/{code}")
	public String edit(@PathVariable String code, Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("center", centerService.findByCode(code));
			model.addAttribute("isNew", false);
			putIntoRequest(model);

			return BASE_VIEW + CREATE_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<StateModel> states, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			centerService.delete(states);

			return new AjaxResponse(true, "Center succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody CenterModel model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (model.getIsNew()) {
			if (centerService.isExist(model.getCode())) {
				
				return new AjaxResponse(false, "Center Code is duplicated");
			}
		}

		centerService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
}
