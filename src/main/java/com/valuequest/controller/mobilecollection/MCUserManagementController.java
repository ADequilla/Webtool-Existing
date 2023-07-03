package com.valuequest.controller.mobilecollection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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
import com.valuequest.controller.mobilecollection.model.McUserModel;
import com.valuequest.entity.LogUploadFile;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.MCUser;
import com.valuequest.entity.MCUserMapper;
import com.valuequest.entity.StructureBranch;
import com.valuequest.entity.StructureInstitution;
import com.valuequest.entity.StructureUnit;
import com.valuequest.entity.TempAccountOfficer;
import com.valuequest.entity.security.SecUser;
import com.valuequest.util.CsvUtils;
import com.valuequest.util.GenerateReport;
import com.valuequest.util.StatusConstantas;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Eki Nurhadi
 *
 */

@Controller
@RequestMapping("/mobilecollection/user")
public class MCUserManagementController extends BaseController {
	final static String MENU = "MOBILECOLLECTION";
	final static String PRIVILEDGE = "MCUSER_MANAGEMENT";
	private String BASE_VIEW = "11.mobilecollection/";
	private String LIST_VIEW = "mcuser";
	private String EDIT_VIEW = "mcuser-edit";
	private String NEW_VIEW = "mcuser-new";
	private String GENERATE_VIEW = "mcuser-generate";
	private String REPORT_NAME = "mobile_collection_users";
	private String TEMPLATE_NAME = "MobileCollectionUsers";

	@RequestMapping("/")
	public String index(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {

			model.addAttribute("availableBranchList", branchService.mappedList());
			putIntoRequest(model);

			return BASE_VIEW + LIST_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/search")
	public @ResponseBody DataTables search(DataTables dataTables, @RequestParam(required = false) String mcuId,
			@RequestParam(required = false) String staffId, @RequestParam(required = false) String mobileNumber,
			@RequestParam(required = false) String internalAccount, @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String unitCode, @RequestParam(required = false) String designation,
			@RequestParam(required = false) String aoStatus, HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("mcuId", mcuId);
		searchMap.put("staffId", staffId);
		searchMap.put("mobileNumber", mobileNumber);
		searchMap.put("internalAccount", internalAccount);
		searchMap.put("branchCode", branchCode);
		searchMap.put("unitCode", unitCode);
		searchMap.put("designation", designation);
		searchMap.put("aoStatus", aoStatus);

		return mcUserService.searchByMapCriteria(dataTables, searchMap, false);
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {
			
			model.addAttribute("availableBranchList", branchService.mappedList());
			putIntoRequest(model);

			return BASE_VIEW + NEW_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("mcuser", mcUserService.findById(id));
			
			List<StructureBranch> branchList			= branchService.mappedListByMcUser(id);
			List<StructureUnit> unitList				= unitService.mappedListByMcUser(id);

			model.addAttribute("selectedBranchList", branchList);
			model.addAttribute("selectedUnitList", unitList);
			model.addAttribute("availableBranchList", branchService.mappedList());
			
			
			if(! branchList.isEmpty()) {
				String[] branchs	= new String[branchList.size()];
				for (StructureBranch branch : branchList) {
					branchs[branchList.indexOf(branch)]	= branch.getCode();
				}
				
				System.out.println("unitService.mappedListBy(branchs) = "+unitService.mappedListBy(branchs));
				model.addAttribute("availableUnitList", unitService.mappedListBy(branchs));
			}
			
			if(! unitList.isEmpty()) {
				String[] units	= new String[unitList.size()];
				for (StructureUnit unit : unitList) {
					units[unitList.indexOf(unit)]	= unit.getCode();
				}
				
				System.out.println("centerService.mappedListBy(units) = "+centerService.mappedListBy(units));
				model.addAttribute("availableCenterList", centerService.mappedListBy(units));
			}
			
			
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody McUserModel model, HttpSession session) {
		
		if (model.getMcuId() != null) {
			model.setMcuId(model.getMcuId().replaceAll("[^a-zA-Z0-9@.-]", ""));
			model.setMcuId(model.getMcuId().toUpperCase());
		}
		
		String[] branchs = model.getBranch();
		String branchCode = "";
		if (branchs != null && branchs.length > 0) {
			for (String branch : branchs) {
				branchCode = branch;
			}
		}
		
		if (!model.getInternalAccount().substring(model.getInternalAccount().length() - 4).equals(branchCode.substring(branchCode.length() - 4))) {
			return new AjaxResponse(false, "Branch and AO Internal Account that was tagged in your MobColl account do not match. For assistance please contact CARD Bank Inc., Web Application Administrator. Thank You!");
		}
		MCUser findUser = mcUserService.getMCUserByMcuId(model.getMcuId());
		if ((model.getId() == null && findUser != null)
				|| (findUser != null && !model.getId().equals(findUser.getId())))
			return new AjaxResponse(false, "MC User ID already exist.");

		MCUser findStaffID = mcUserService.getMCUserByStaffId(model.getStaffId());
		if ((model.getId() == null && findStaffID != null)
				|| (findStaffID != null && !model.getId().equals(findStaffID.getId())))
			return new AjaxResponse(false, "Staff ID already exist.");

		MCUser mobileNumber = mcUserService.getMCUserByMobileNumber(model.getMobileNumber());
		if ((model.getId() == null && mobileNumber != null)
				|| (mobileNumber != null && !model.getId().equals(mobileNumber.getId())))
			return new AjaxResponse(false, "Mobile Number already used.");

		MCUser internalAccount = mcUserService.getMCUserByInternalAccount(model.getInternalAccount());
		if ((model.getId() == null && internalAccount != null)
				|| (internalAccount != null && !model.getId().equals(internalAccount.getId())))
			return new AjaxResponse(false, "Internal Account already used.");

		MCUser mcUser = mcUserService.findById(model.getId());
		if (mcUser != null && mcUser.getModified())
			return new AjaxResponse(false, "Cannot modified data with status 'Waiting for authorization'.");

		mcUserService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("listMCUserStatus", genericService.lookup(Lookup.LOOKUP_MUSER_STATUS));
		model.addAttribute("listDesignation", genericService.lookup(Lookup.LOOKUP_DESIGNATION));
		model.addAttribute("listFileType", genericService.lookup(Lookup.LOOKUP_REPORT_TYPE));
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam MultipartFile files, Model model, HttpSession session) {
		File file = null;
		BufferedReader br = null;
		String delimeter = "\\|";
		String header = "BRANCH CODE";

		if (getPriviledgeUser(session, PRIVILEDGE, UPLOAD)) {

			putIntoRequest(model);

			if (files.isEmpty()) {

				model.addAttribute("err_msg", "Failed to upload file because its empty.");
			} else {

				List<String> typeList = new ArrayList<String>();
				typeList.add("application/vnd.ms-excel");
				typeList.add("text/plain");
				typeList.add("text/csv");
				typeList.add("text/tsv");

				if (typeList.contains(files.getContentType())) {
					try {
						int rowCount = CsvUtils.getRowCount(files)-1;
						String row = genericService.getConfigValueByName("CSV_MAX_ROW");
//						String size = genericService.getConfigValueByName("CSV_MAX_SIZE");
						if (rowCount > Integer.valueOf(row)) {
							model.addAttribute("err_msg", String.format("Max row must less than %s.", row));
						} else {

							file = new File(files.getOriginalFilename());
							files.transferTo(file);
							br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
							SecUser userLogin = getLoginSecUser(session);
//						mcUserService.uploadFile(br.readLine(), delimeter, header, userLogin);

							String line;
							MCUser mcUser;

							LogUploadFile logUploadFile = null;
							List<TempAccountOfficer> tempAccountOfficers = null;
							List<MCUser> mcUsers = null;
							int toUpload = 3;
							
							while ((line = br.readLine()) != null) {
								List<String> errorMsg = new ArrayList<>();
								tempAccountOfficers = new ArrayList<>();
								mcUsers = new ArrayList<>();
								
								if (line.contains(header)) {
									continue;
								}

								String[] data = line.split(delimeter, -1);

								if (data.length == 11) {
									for (int i=0;i<data.length;i++) {
										data[i] = data[i].replaceAll("[^a-zA-Z0-9@.-]", "");
									}
									
									mcUser = new MCUser();
									mcUser.setMcuId(data[0]);
									System.out.println("######mcUSer:"+data[0]);
									System.out.println("######mcUSer:"+mcUser.getMcuId());
									if (mcUser.getMcuId().equals("")) {
										errorMsg.add("No MCU ID");
									}
									
									mcUser.setStaffId(data[1]);
									if (mcUser.getStaffId().equals("")) {
										errorMsg.add("No Staff ID");
									}
									
									mcUser.setGivenName(data[2]);
									if (mcUser.getGivenName().equals("")) {
										errorMsg.add("No Given Name");
									}
									
									mcUser.setMiddleName(data[3]);
									if (mcUser.getMiddleName().equals("")) {
										errorMsg.add("No Middle Name");
									}
									
									mcUser.setSurname(data[4]);
									if (mcUser.getSurname().equals("")) {
										errorMsg.add("No Surname");
									}
									
									if (data[5].equals("")) {
										errorMsg.add("No Designation");
									} else if (!(data[5].equals("AO") || data[5].equals("UM"))){
										errorMsg.add("Invalid Designation. Please write AO or UM for designation");
										if (data[5].length() > 2){
											data[5] = "";
										}
									} else {
										mcUser.setDesignation(data[5]);
									}
									
									mcUser.setMobileNumber(data[6]);
									if (mcUser.getMobileNumber().equals("")) {
										errorMsg.add("No Mobile Number");
									}
									
									StructureBranch branch = branchService.findByCode(data[7]);
									if (data[7].equals("")) {
										errorMsg.add("No Branch Code");
									} else if (branch == null){
										errorMsg.add("Branch Code is wrong");
									} else {
										mcUser.setBranch(branch);
									}

									StructureUnit unit = unitService.findByCode(data[8]);
									
									if (data[8].equals("")) {
										errorMsg.add("No Unit Code");
									} else if (unit == null){
										errorMsg.add("Unit Code is wrong");
									} else if (!data[7].equals("") && !data[8].equals("") && branch != null && unit != null) {
										String[] branchs	= new String[]{branch.getCode()};
										List<StructureUnit> unitMapping = unitService.mappedListBy(branchs);
										Boolean message = false;
										for (StructureUnit unitCek : unitMapping) {
											if (unitCek.getCode().equals(data[8])) {
												message = true;
											}
										}
										
										if (!message) {
											errorMsg.add("Invalid Unit Code");
										} else {
											mcUser.setUnit(unit);
										}
									}

									mcUser.setInternalAccount(data[9]);
									if (mcUser.getInternalAccount().equals("")) {
										errorMsg.add("No Internal Account");
									}
									
									mcUser.setEmail(data[10]);
									if (mcUser.getEmail().equals("")) {
										errorMsg.add("No Email Address");
									}
									
									mcUser.setMcuId(mcUser.getMcuId().toUpperCase());
									MCUser findUser = mcUserService.getMCUserByMcuId(mcUser.getMcuId());
									if (findUser != null) {
										errorMsg.add("MC User ID already exist");
									}

									MCUser findStaffID = mcUserService.getMCUserByStaffId(mcUser.getStaffId());
									if (findStaffID != null) {
										errorMsg.add("Staff ID already exist");
									}

									if (!mcUser.getStaffId().matches("[0-9]{6}-[0-9]{5}")) {
										errorMsg.add("Staff ID invalid format");
									}
									
									MCUser internalAccount = mcUserService
											.getMCUserByInternalAccount(mcUser.getInternalAccount());
									if (internalAccount != null) {
										errorMsg.add("Internal Account already used");
									}

									MCUser mobileNumber = mcUserService
											.getMCUserByMobileNumber(mcUser.getMobileNumber());
									if (mobileNumber != null) {
										errorMsg.add("Mobile Number already used");
									}
									
									if (!mcUser.getInternalAccount().equals("")&&!data[7].equals("")) {
										if (!mcUser.getInternalAccount().substring(mcUser.getInternalAccount().length() - 4).equals(data[7].substring(data[7].length() - 4))) {
											errorMsg.add("Branch and AO Internal Account that was tagged in your MobColl account do not match. For assistance please contact CARD Bank Inc., Web Application Administrator. Thank You!");
										}
									}
									
									if (errorMsg.size() > 0) {
										TempAccountOfficer tempAccountOfficer = new TempAccountOfficer();
										tempAccountOfficer.setMcuId(data[0]);
										tempAccountOfficer.setStaffId(data[1]);
										tempAccountOfficer.setGivenName(data[2]);
										tempAccountOfficer.setMiddleName(data[3]);
										tempAccountOfficer.setSurname(data[4]);
										tempAccountOfficer.setDesignation(data[5]);
										tempAccountOfficer.setMobileNumber(data[6]);
										tempAccountOfficer.setBranch(branch);
										tempAccountOfficer.setUnit(unit);
										tempAccountOfficer.setInternalAccount(data[9]);
										tempAccountOfficer.setEmail(data[10]);
										tempAccountOfficer.setRemark(StringUtils.join(errorMsg, "|"));
										tempAccountOfficers.add(tempAccountOfficer);
										toUpload = toUpload - 1;
									} else {
										mcUsers.add(mcUser);
									}
								} else {
									errorMsg.add("Format content is wrong. content: " + line);
								}
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
								}
							
	
								if (toUpload == 2) {
									logUploadFile = new LogUploadFile();
									logUploadFile.setFileName(files.getOriginalFilename());
									logUploadFile.setUploadBy(userLogin);
									logUploadFile.setUploadDate(new Date());
									logUploadFileService.save(logUploadFile);
									toUpload = toUpload - 1;
								}
								
								if (tempAccountOfficers.size() <= 0 && mcUsers.size() <= 0) {
									model.addAttribute("err_msg", "Format content is wrong.");
								} else {
									mcUserService.uploadFile(mcUsers, tempAccountOfficers, logUploadFile, userLogin);
	
									model.addAttribute("msg", "MC Users successfully uploaded.");
								}
							}
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

	@RequestMapping("/report")
	public String report(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, DOWNLOAD)) {

			putIntoRequest(model);

			return BASE_VIEW + GENERATE_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/generate")
	public void generate(
			@RequestParam(required = false) String dateStart,
			@RequestParam(required = false) String dateEnd, 
			@RequestParam(required = false) String createdBy,
			HttpSession session, HttpServletResponse response) {

		HashMap<String, Object> map = new HashMap<>();
		map.put("START_PERIOD", dateStart);
		map.put("END_PERIOD", dateEnd);
		map.put("CREATED_BY", createdBy);

		List<MCUserMapper> mcUsers = mcUserService.searchByMapCriteria(map);
		
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(mcUsers);
		GenerateReport report = new GenerateReport();
		report.generatePdfReport(TEMPLATE_NAME, REPORT_NAME, map, beanColDataSource, "CSV", response);
	}

	@RequestMapping(value = "/resetPassword/{mcuid}", method = RequestMethod.POST, headers = {
			"content-type=application/json" })
	public @ResponseBody AjaxResponse reset(@PathVariable String mcuid, HttpServletRequest request,
			HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, RESET_PASSWORD)) {
			mcUserService.resetMCUserPassword(mcuid, getLoginSecUser(session));

			return new AjaxResponse(true, "Password successfully reset.");
		}
		return new AjaxResponse(false, "You are not authorized to access this module.");
	}

	@RequestMapping(value = "/resetPin/{mcuid}", method = RequestMethod.POST, headers = {
			"content-type=application/json" })
	public @ResponseBody AjaxResponse resetPin(@PathVariable String mcuid, HttpServletRequest request,
			HttpSession session) {
		if (getPriviledgeUser(session, PRIVILEDGE, RESET_MPIN)) {
			mcUserService.resetMCUserPin(mcuid, getLoginSecUser(session));

			return new AjaxResponse(true, "MPIN successfully reset.");
		}
		return new AjaxResponse(false, "You are not authorized to access this module.");
	}

	@RequestMapping(value = "/deactivate/{mcuid}", method = RequestMethod.POST, headers = {"content-type=application/json"})
	public @ResponseBody AjaxResponse deactivate(@PathVariable String mcuid, HttpServletRequest request,
			HttpSession session) {
		if (getPriviledgeUser(session, PRIVILEDGE, DEACTIVATE)) {
			mcUserService.deactiveOrReactive(mcuid, StatusConstantas.DEACTIVE, getLoginSecUser(session));

			return new AjaxResponse(true, "MC User successfully deactivate.", StatusConstantas.DEACTIVE);
		}
		return new AjaxResponse(false, "You are not authorized to access this module.");
	}

	@RequestMapping(value = "/reactivate/{mcuid}", method = RequestMethod.POST, headers = {"content-type=application/json"})
	public @ResponseBody AjaxResponse reactivate(@PathVariable String mcuid, HttpServletRequest request,
			HttpSession session) {
		if (getPriviledgeUser(session, PRIVILEDGE, REACTIVATE)) {
			MCUser findUser = mcUserService.getMCUserByMcuId(mcuid);
			MCUser internalAccount = mcUserService.getMCUserByInternalAccount(findUser.getInternalAccount());
			
			if (internalAccount != null) {
				return new AjaxResponse(false, "Internal Account was already used by other MC User.");
			} else {
				mcUserService.deactiveOrReactive(mcuid, StatusConstantas.REACTIVE, getLoginSecUser(session));
				return new AjaxResponse(true, "MC User successfully reactivate.", StatusConstantas.REACTIVE);
			}
		}
		return new AjaxResponse(false, "You are not authorized to access this module.");
	}

	@RequestMapping(value = "/unblock/{mcuid}", method = RequestMethod.POST, headers = {"content-type=application/json"})
	public @ResponseBody AjaxResponse unblocked(@PathVariable String mcuid, HttpServletRequest request,
			HttpSession session) {
		if (getPriviledgeUser(session, PRIVILEDGE, UNBLOCKED)) {
			mcUserService.changeAccStatusByMcuId(mcuid, StatusConstantas.INACTIVE, getLoginSecUser(session));

			return new AjaxResponse(true, "MC User Account successfully unblocked.");
		}
		return new AjaxResponse(false, "You are not authorized to access this module.");
	}
}
