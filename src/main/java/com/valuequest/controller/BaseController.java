
package com.valuequest.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.valuequest.entity.AsynReport;
import com.valuequest.entity.Client;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.ParamConfig;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.AdminService;
import com.valuequest.services.AsynReportService;
import com.valuequest.services.AtmLocationService;
import com.valuequest.services.AuditTrailService;
import com.valuequest.services.BillerService;
import com.valuequest.services.BranchService;
import com.valuequest.services.CSRHotlineService;
import com.valuequest.services.CenterService;
import com.valuequest.services.ClientService;
import com.valuequest.services.DownTimeService;
import com.valuequest.services.FailedEnrollmentService;
import com.valuequest.services.FeeStructureService;
import com.valuequest.services.GenericService;
import com.valuequest.services.HierarchyService;
import com.valuequest.services.InstitutionService;
import com.valuequest.services.JobService;
import com.valuequest.services.K2CTransactionService;
import com.valuequest.services.ListUsedDeviceService;
import com.valuequest.services.ListAgentService;
import com.valuequest.services.LogAuditTrailAoService;
import com.valuequest.services.LogPasswordService;
import com.valuequest.services.LogUploadFileService;
import com.valuequest.services.MCUserService;
import com.valuequest.services.NewsService;
import com.valuequest.services.OperationService;
import com.valuequest.services.ParamConfigService;
import com.valuequest.services.ProductAndServiceService;
import com.valuequest.services.ProductAndServicesService;
import com.valuequest.services.ProductInfoService;
import com.valuequest.services.RoleService;
import com.valuequest.services.RoutesService;
import com.valuequest.services.SlfRequestService;
import com.valuequest.services.SmsLogsService;
import com.valuequest.services.SplashScreenService;
import com.valuequest.services.SuspiciousService;
import com.valuequest.services.TempAccountOfficerService;
import com.valuequest.services.TicketService;
import com.valuequest.services.TypeOfConcernService;
import com.valuequest.services.UnitService;
import com.valuequest.services.UploadFileService;
import com.valuequest.services.RemittanceService;
import com.valuequest.services.MerchantService;
import com.valuequest.services.TransactionService;
import com.valuequest.services.CustomerLoanListService;
import com.valuequest.services.DashboardService;
import com.valuequest.util.HttpClientUpload;

@PropertySource("classpath:app.properties")
public class BaseController {
	@Autowired
	protected Environment env; 
	@Autowired
	protected GenericService genericService;
	@Autowired
	protected AdminService adminService;
	@Autowired
	protected HierarchyService hierarchyService;
	@Autowired
	protected InstitutionService institutionService;
	@Autowired
	protected BranchService branchService;
	@Autowired
	protected UnitService unitService;
	@Autowired
	protected CenterService centerService;
	@Autowired
	protected UploadFileService uploadFileService;
	@Autowired
	protected AuditTrailService auditTrailService;
	@Autowired
	protected ParamConfigService paramConfigService;
	@Autowired
	protected FeeStructureService feeStructureService;
	@Autowired
	protected ClientService clientService;
	@Autowired
	protected MerchantService merchantService;
	@Autowired
	protected SlfRequestService slfRequestService;
	@Autowired
	protected SmsLogsService smsLogsService;
	@Autowired
	protected RoleService roleService;
	@Autowired
	protected TicketService ticketService;
	@Autowired
	protected AtmLocationService atmLocationService;
	@Autowired
	protected ProductInfoService productInfoService;
	@Autowired
	protected JobService jobService;
	@Autowired
	protected NewsService newsService;
	@Autowired
	protected TypeOfConcernService typeOfConcernService;
	@Autowired
	protected CSRHotlineService csrHotlineService; 
	@Autowired
	protected LogPasswordService logPasswordService;
	@Autowired
	protected DownTimeService downTimeService;
	@Autowired
	protected AsynReportService asynReportService;
	@Autowired
	protected SuspiciousService suspiciousService;
	@Autowired
	protected BillerService billerService;
	@Autowired
	protected OperationService operationService;
	@Autowired
	protected ProductAndServiceService productService;
	@Autowired
	protected RemittanceService remittanceService;
	@Autowired
	protected MCUserService mcUserService;
	@Autowired
	protected LogUploadFileService logUploadFileService;
	@Autowired
	protected TempAccountOfficerService tempAccountOfficerService;
	@Autowired
	protected LogAuditTrailAoService logAuditTrailAoService;
	@Autowired
	protected FailedEnrollmentService failedEnrollmentService;
	@Autowired
	protected ListUsedDeviceService listUsedDeviceService;
	@Autowired
	protected ListAgentService listAgentService;
	@Autowired
	protected RoutesService routesService;
	@Autowired
	protected TransactionService transactionService;
	@Autowired
	protected CustomerLoanListService customerLoanListService;
	@Autowired
	protected K2CTransactionService k2ctransactionService;
	@Autowired
	protected DashboardService dashboardService;
	@Autowired
	protected ProductAndServicesService productAndServicesService;
	@Autowired
	protected SplashScreenService splashScreenService;
	
	
	protected DateFormat dateFormat 			= new SimpleDateFormat("dd/MM/yyyy");
	protected final NumberFormat numberFormat 	= new DecimalFormat("#,##0.00");

	public static final String USER_SUPER_ADMIN = "SUPER_ADMIN";
	public static final String USER             = "USER";
	public static final String VIEW 			= "VIEW";
	public static final String NEW 				= "NEW";
	public static final String EDIT 			= "EDIT";
	public static final String DELETE 			= "DELETE";
	public static final String UPLOAD 			= "UPLOAD";
	public static final String DOWNLOAD 		= "DOWNLOAD";
	public static final String RESET_PASSWORD   = "RESET_PASSWORD";
	public static final String RESET_MPIN 		= "RESET_MPIN";
	public static final String DEACTIVATE 		= "DEACTIVATE";
	public static final String REACTIVATE 		= "REACTIVATE";
	public static final String UNBLOCKED 		= "UNBLOCKED";
	public static final String APPROVE 			= "APPROVE";
	public static final String REJECT 			= "REJECT";
	
	public String getUnauthorizedPage() {
		return "01.misc/not_authorized";
	}

	@InitBinder
	public void initBinder(DataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(Double.class, numberFormat, true));
	}

	protected SecUser getLoginSecUser(HttpSession session) {
		
		return (SecUser) session.getAttribute("loginSecUser");
	}

	protected SecUser getLoginSecUserName(HttpSession session) {
	
		return (SecUser) session.getAttribute("loginSecUserName");
	}

	protected String getLoginUserName(HttpSession session) {
		SecUser user = this.getLoginSecUser(session);
		if (user != null) {
			return user.getUsrLoginname();
		}

		return null;
	}
	

	protected Long getUserIdFromSession(HttpSession session) {
		SecUser user = this.getLoginSecUser(session);
		if (user != null) {
			return user.getId();
		}

		return null;
	}

	protected Long getLoginIdFromSession(HttpSession session) {
		SecUser user = this.getLoginSecUser(session);
		if (user != null) {
			if (user.getUsrPosition().equals(USER_SUPER_ADMIN)) {
				return null;
			} else {
				return user.getId();
			}
		}

		return 0L;
	}

	protected boolean getPriviledgeUser(HttpSession session, String menu, String component) {
		SecUser user = getLoginSecUser(session);
		if (user.getUsrPosition().equals(USER_SUPER_ADMIN))
			return true;

		return adminService.getPriviledge(user.getId(), menu, component);
	}

	public String getXMLValue(Object object) {
		String XML = "";
		try {
			String className = object.getClass().getSimpleName();
			if (className.equals("MerchantEntity")) {
				className = "Merchant";
			}
			XML = "<" + className + ">\n";
			for (Field field : object.getClass().getDeclaredFields()) {
				field.setAccessible(true);

				String name 	= field.getName();
				Object value 	= field.get(object);

				XML += "\t<" + name + ">" + value + "</" + name + ">\n";

			}
			XML += "</" + className + ">\n";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return XML;
	}

	public void refreshParameters(String app) {
		HttpClientUpload httpClient = new HttpClientUpload();
		String url = null;
		
		if (Client.CLIENT_TYPE_AGENT.equals(app)  || Client.CLIENT_TYPE_MEMBER.equals(app)) {
			/*url = genericService.getConfigValueByName(ParamConfig.WS_PARAMCONFIG_REFRESH_AGENT_URL);
			if (StringUtils.isNotBlank(url)) {
				httpClient.call(url);
			}*/
			
			url = genericService.getConfigValueByName(ParamConfig.WS_PARAMCONFIG_REFRESH_MEMBER_URL);
			if (StringUtils.isNotBlank(url)) {
				httpClient.call(url);
			}

		} else if (Client.CLIENT_TYPE_MC.equals(app)) {
			url = genericService.getConfigValueByName(ParamConfig.WS_PARAMCONFIG_REFRESH_MOBILE_C_URL);
			if (StringUtils.isNotBlank(url)) {
				httpClient.call(url);
			}
		} else {
			/*url = genericService.getConfigValueByName(ParamConfig.WS_PARAMCONFIG_REFRESH_AGENT_URL);
			if (StringUtils.isNotBlank(url)) {
				httpClient.call(url);
			}*/
			
			url = genericService.getConfigValueByName(ParamConfig.WS_PARAMCONFIG_REFRESH_MEMBER_URL);
			if (StringUtils.isNotBlank(url)) {
				httpClient.call(url);
			}
		}
	}
	
	public void downloadReport(Long id, HttpServletResponse response) {
		try {
			AsynReport report = asynReportService.findById(id);
			System.out.println("report.getFilePath() = "+report.getFilePath());
			
			File file = new File(report.getFilePath());

			if (!file.exists()) {
				String errorMessage = "Sorry. The file you are looking for does not exist";
				OutputStream outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
				return;
			} else {
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					if (Lookup.LOOKUP_REPORT_TYPE_PDF.equals(report.getFileType())) {
						mimeType = "application/pdf";
					} else {
						mimeType = "text/csv";
					}
				}

				response.setContentType(mimeType);
				response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			}
		} catch (Exception e) {
			throw new RuntimeException("Internal Server Error.");
		}
	}
}