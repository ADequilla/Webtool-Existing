package com.valuequest.controller.administration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.valuequest.common.AjaxResponse;
import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.controller.administration.model.HierarchyModel;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.maintenance.model.BranchModel;
import com.valuequest.controller.maintenance.model.CenterModel;
import com.valuequest.controller.maintenance.model.InstitutionModel;
import com.valuequest.controller.maintenance.model.UnitModel;
import com.valuequest.entity.security.SecUser;

@Controller
@RequestMapping("/administration/hierarchy")
public class HierarchyController extends BaseController {
	

	final static String MENU 		= "ADMINISTRATION";
	final static String PRIVILEDGE 	= "HIERARCHY";
	private String BASE_VIEW 		= "02.administration/";
	private String LIST_VIEW 		= "hierarchy";
	private String EDIT_VIEW 		= "hierarchy-edit";
	
	@RequestMapping("/")
	public String index(Model model, HttpSession session) {

		SecUser user = this.getLoginSecUser(session);

        user.setIsLogin(true);
        adminService.updateCekStatus(user, session.getId());
		
		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {
			
			model.addAttribute("availableInstiList", institutionService.mappedList());
			putIntoRequest(model);
			
			return BASE_VIEW + LIST_VIEW;
		}
		
		return getUnauthorizedPage();
	}

	@RequestMapping("/search")
	public @ResponseBody DataTables search(DataTables dataTables, 
			@RequestParam String institution, 
			@RequestParam String branch, 
			@RequestParam String unit,
			@RequestParam String center) {
		
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("institution", institution);
		searchMap.put("branch", branch);
		searchMap.put("unit", unit);
		searchMap.put("center", center);

		return hierarchyService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {
			
			putIntoRequest(model);
			
			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {
			
			model.addAttribute("hierarchy", hierarchyService.findById(id));
			putIntoRequest(model);
			
			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody HierarchyModel hierarchy, HttpSession session) {

		if(hierarchyService.isDuplicate(hierarchy)){
			
			return new AjaxResponse(false, "Duplicate mapping data.");
		}else{
			SecUser userLogin = getLoginSecUser(session);
			
			if (StringUtils.isNotBlank(hierarchy.getBranchCode()) && !branchService.isExist(hierarchy.getBranchCode())) {
				BranchModel bm = new BranchModel();
				bm.setCode(hierarchy.getBranchCode());
				bm.setDescription(hierarchy.getBranchDesc());
				System.out.println("#####New Branch Save");
				branchService.save(bm, userLogin);
			}
			
			if (StringUtils.isNotBlank(hierarchy.getUnitCode()) && !unitService.isExist(hierarchy.getUnitCode())) {
				UnitModel um = new UnitModel();
				um.setCode(hierarchy.getUnitCode());
				um.setDescription(hierarchy.getUnitDesc());
				System.out.println("#####New Unit Save");
				unitService.save(um, userLogin);
			}
			
			if (StringUtils.isNotBlank(hierarchy.getCenterCode()) && !centerService.isExist(hierarchy.getCenterCode())) {
				CenterModel cm = new CenterModel();
				cm.setCode(hierarchy.getCenterCode());
				cm.setDescription(hierarchy.getCenterDesc());
				System.out.println("#####New Center Save");
				centerService.save(cm, userLogin);
			}
			
			hierarchyService.save(hierarchy, userLogin);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) { }
			
			return new AjaxResponse(hierarchy);
		}
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<StateModel> states, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			hierarchyService.delete(states);

			return new AjaxResponse(true, "Hierarcy succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}
	
	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(
		@RequestParam MultipartFile files, 
		@RequestParam(required = false) String instiCode,
		@RequestParam(required = false) String instiDesc,
		Model model, 
		HttpSession session) {
		File file			= null;
		BufferedReader br 	= null;
		String delimeter 	= "\\|";
		String instCode		= instiCode;
		String instDesc		= instiDesc;
		String header		= "Branch Code";

		System.out.println("#######level1");
		System.out.println("#######Insti Code: "+instCode );
		System.out.println("#######Insti Desc: "+instDesc );
		
		
		if (getPriviledgeUser(session, PRIVILEDGE, UPLOAD)) {
			System.out.println("#######level2 get prviledge");
			putIntoRequest(model);
			
			if (files.isEmpty()) {
				System.out.println("#######level3 empty");
				model.addAttribute("err_msg", "Failed to upload file because its empty.");
			} else {
				System.out.println("#######level3 not empty");
				List<String> typeList = new ArrayList<String>();
		    	typeList.add("application/vnd.ms-excel");
		    	typeList.add("text/plain");
		    	typeList.add("text/csv");
		    	typeList.add("text/tsv");

		    	
		    	if(typeList.contains(files.getContentType())){
		    		try {
						System.out.println("#######level4 contains");
						file = new File(files.getOriginalFilename());
						files.transferTo(file);
						br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
						String line;
						HierarchyModel hierarchy;
						SecUser userLogin = getLoginSecUser(session);
						
						while ((line = br.readLine()) != null) {
							
							if (line.contains(header)) {
								continue;
							}
							
							String[] data = line.split(delimeter, -1);
							System.out.println("#######Data Size: "+data.length);
							if (data.length == 6) {	
								
								hierarchy = new HierarchyModel();
								hierarchy.setInstitutionCode(instCode);
								hierarchy.setInstitutionDesc(instDesc);
								hierarchy.setBranchCode(data[0]);
								hierarchy.setBranchDesc(data[1]);
								hierarchy.setUnitCode(data[2]);
								hierarchy.setUnitDesc(data[3]);
								hierarchy.setCenterCode(data[4]);
								hierarchy.setCenterDesc(data[5]);
								
								if (StringUtils.isBlank(hierarchy.getBranchCode())) {
									model.addAttribute("err_msg", "Branch is required. content: " + line);
									break;
								} else if ((StringUtils.isNotBlank(hierarchy.getUnitCode()) && StringUtils.isBlank(hierarchy.getCenterCode())) || (StringUtils.isBlank(hierarchy.getUnitCode()) && StringUtils.isNotBlank(hierarchy.getCenterCode()))) {
									model.addAttribute("err_msg", "Invalid mapping, wrong value unit and center. content: " + line);
									break;
								}
								
								if (! hierarchyService.isDuplicate(hierarchy)) {
									
									if (StringUtils.isNotBlank(instCode) && !institutionService.isExist(instCode)) {
										InstitutionModel im = new InstitutionModel();
										im.setCode(instCode);
										im.setDescription(instDesc);
										
										institutionService.save(im, userLogin);
									}
									
									if (StringUtils.isNotBlank(data[0]) && !branchService.isExist(data[0])) {
										BranchModel bm = new BranchModel();
										bm.setCode(data[0]);
										bm.setDescription(data[1]);
										
										branchService.save(bm, userLogin);
									}
									
									if (StringUtils.isNotBlank(data[2]) && !unitService.isExist(data[2])) {
										UnitModel um = new UnitModel();
										um.setCode(data[2]);
										um.setDescription(data[3]);
										
										unitService.save(um, userLogin);
									}
									
									if (StringUtils.isNotBlank(data[4]) && !centerService.isExist(data[4])) {
										CenterModel cm = new CenterModel();
										cm.setCode(data[4]);
										cm.setDescription(data[5]);
										
										centerService.save(cm, userLogin);
									}
									
									hierarchyService.save(hierarchy, userLogin);
									
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) { }
								}
								
							} else {
								
								model.addAttribute("err_msg", "Format content is wrong. content: " + line);
								break;
							}
						}
						
						if (! model.containsAttribute("err_msg")){
							model.addAttribute("msg", "Hierarchy successfully uploaded.");
						}
					} catch (IllegalStateException e) {
						
						model.addAttribute("err_msg", e.getMessage());
					} catch (IOException e) {
						
						model.addAttribute("err_msg", e.getMessage());
					}
		    		
		    	} else {
		    		
		    		model.addAttribute("err_msg", "Please upload CSV file.");
		    	}
			}
			
		} else {
			
			model.addAttribute("err_msg", "You are not authorized to access this module.");
		}
		
		return BASE_VIEW + LIST_VIEW;
	}
}
