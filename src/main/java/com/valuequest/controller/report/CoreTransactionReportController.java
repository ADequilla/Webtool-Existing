package com.valuequest.controller.report;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valuequest.common.AjaxResponse;
import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.controller.api.http.HttpRequestSender;
import com.valuequest.controller.api.model.CoreTransactionReportModel;
import com.valuequest.controller.api.model.SuperAppTransactionModel;
import com.valuequest.controller.report.param.TransactionParam;
import com.valuequest.entity.AsynReport;
import com.valuequest.entity.K2CTransactionTypeLookup;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.security.SecUser;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/report/core-transaction")
public class CoreTransactionReportController extends BaseController {

	@Value("${soteria.auth.signinUsername}")
	private String signinUsername;
	
	final Logger log = Logger.getLogger("CoreTransactionReportController");
	final static String MENU 		= "REPORT";
	final static String PRIVILEDGE 	= "CORE_TRANSACTION";
	final static String RPT_MENU	= "core-transaction";
	final static String RPT_TITLE 	= "Core Transaction";
	private String BASE_VIEW 		= "08.report/";
	private String TRANSACTION_VIEW = "coretransaction";
	private String LIST_VIEW 		= "report-core-transaction-list";
	private String GENERATE_VIEW	= "param-core-transaction";
	
	@RequestMapping("/")
	public String index(Model model, HttpSession session) {
		 SecUser user = this.getLoginSecUser(session);

        user.setIsLogin(true);
        adminService.updateCekStatus(user, session.getId());
		System.out.print("#########Username:" + signinUsername);
		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {
			
			model.addAttribute("listTransactionType", genericService.k2cTransactionTypeLookup(K2CTransactionTypeLookup.LOOKUP_K2C_TRANSACTION_LOG_TYPE));
			putIntoRequest(model);

			return BASE_VIEW + TRANSACTION_VIEW;
		}
		System.out.println("Unauthorized");
		return getUnauthorizedPage();
	}
	
	@RequestMapping("/search")
	public @ResponseBody DataTables search(DataTables dataTables, 
			@RequestParam(required = false) String trnType,
			@RequestParam(required = false) String cid,	
			@RequestParam(required = false) String clientName,
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("trnType", trnType);
		searchMap.put("cid", cid);
		searchMap.put("clientName", clientName);

		return transactionService.searchByMapCriteria(dataTables, searchMap);
	}
	
	@Value("${API.CoreMFS}")
	private String APICoreMFS;
	
	@RequestMapping("/search/core-api")
	public @ResponseBody List<CoreTransactionReportModel> searchK2CAPI( 
			@RequestParam(required = false) String dtEnd,	
			@RequestParam(required = false) String dtStart,
			HttpSession session) {
		
			HttpSession sess = session;
			HttpRequestSender sender = null;
			System.out.println("######API###### " + APICoreMFS);
			
			 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
			 LocalDateTime now = LocalDateTime.now();  
			 System.out.println(dtf.format(now)); 
	
			try {
				
				SuperAppTransactionModel trn = new SuperAppTransactionModel();
				
				if(dtStart == null) {
					trn.setDtStart(dtf.format(now));
				}else {
					Date dtStrt=new SimpleDateFormat("yyyy-MM-dd").parse(dtStart);  
					trn.setDtStart(dtStrt.toString());
				}
				
				if(dtEnd == null) {
					trn.setDtEnd(dtf.format(now));
				}else {
					Date dtNd=new SimpleDateFormat("yyyy-MM-dd").parse(dtEnd);  
					trn.setDtEnd(dtNd.toString());
				}
				
				Gson gson = new Gson();
				
				System.out.println(gson.toJson(trn));

				//sender = new HttpRequestSender("https://cmfstest.cardmri.com/CoreDatawarehouse/API/getSuperAppTransaction",trn);
				sender = new HttpRequestSender("https://cmfs.cardmri.com:9443/CoreMFS/API/getSuperAppTransaction",trn); //Prod
				//sender = new HttpRequestSender("https://cmfs.cardmri.com:9443/CoreDatawarehouse/API/getSuperAppTransaction",trn);
				// sender = new HttpRequestSender("https://cmfs-kplus.cardmri.com:4443/CoreDatawarehouse/API/getSuperAppTransaction",trn);
				final HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				sender.setHeaders(headers);
			
				String ret;
				if(sender.requestSent()){

						//return gson.toJson(sender.getResponse());
						final List<CoreTransactionReportModel> trnReport = new Gson().fromJson(sender.getResponse(), new TypeToken<ArrayList<CoreTransactionReportModel>>(){}.getType());
						return trnReport;
						// if(DeleteTrn(sess.getId())) {
						// 	final List<K2CTransactionReportModel> trnReport = new Gson().fromJson(sender.getResponse(), new TypeToken<ArrayList<K2CTransactionReportModel>>(){}.getType());
						// 	for(int i = 0; i <= trnReport.size() - 1;i++) {
						// 		log.info("Inserting data row: " + i);
						// 		//String query = "INSERT INTO public.t_temp_core_transaction(area, unit, center, cid, client_name,accnt,accnt_name,debit,credit,trndate,reference,trndesc) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
						// 		String query = "INSERT INTO public.t_temp_core_transaction(area,unit,center,cid,client_name,accnt,accnt_name,debit,credit,trndate,reference,trndesc,session_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

						// 		// System.out.println(obj.toString());
						// 		try (Connection con = ConnectToDB();
						// 				PreparedStatement pst = con.prepareStatement(query)) {
										
						// 				pst.setString(1, trnReport.get(i).getArea());
						// 				pst.setString(2, trnReport.get(i).getUnit());
						// 				pst.setString(3, trnReport.get(i).getCenter());
						// 				pst.setInt(4, trnReport.get(i).getCid());
						// 				pst.setString(5, trnReport.get(i).getClientName());
						// 				pst.setString(6, trnReport.get(i).getAccnt());
						// 				pst.setString(7, trnReport.get(i).getAccntName());
						// 				pst.setDouble(8, trnReport.get(i).getDebit());
						// 				pst.setDouble(9, trnReport.get(i).getCredit());
						// 				pst.setDate(10,  java.sql.Date.valueOf(trnReport.get(i).getTrndate()));
						// 				pst.setString(11, trnReport.get(i).getReference());
						// 				pst.setString(12, trnReport.get(i).getTrndesc());
						// 				pst.setString(13, sess.getId());
						// 				pst.executeUpdate();

						// 			} catch (SQLException ex) {

						// 				log.info("Unable to INSERT data: " + ex);
									
						// 			} catch (Exception ex) {
						// 				// TODO Auto-generated catch block
						// 				log.info("Unable to INSERT data: " + ex);
						// 			}
						// 	}
						// 	return k2ctransactionService.getBySessionId(sess.getId());
						// 	//return trnReport;
							
						// }else {
							
						// 	return null;
						// }
					
				}else {
					
					log.info("HTTP request sender error: " + sender.getErrorMsg());
					return null;
	
				}
	
			}catch(final Exception e) {
	
				log.info("ERROR: " + e.toString());
				return null;
	
			}
		}
	
	@RequestMapping("/report-list")
	public String create(Model model, HttpSession session) {
		
		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {
			
			model.addAttribute("listReportStatus", genericService.lookup(Lookup.LOOKUP_REPORT_STATUS));
			
			putIntoRequest(model);
			
			return BASE_VIEW + LIST_VIEW;
		}
		
		return getUnauthorizedPage();
	}
	
	
	
	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	public void download(@PathVariable Long id, HttpServletResponse response) {
		
		downloadReport(id, response);
	}
	
	@RequestMapping("/search-report-list")
	public @ResponseBody DataTables searchReportList(DataTables dataTables, 
			@RequestParam(required = false) String submitedDateStart,
			@RequestParam(required = false) String submitedDateEnd,
			@RequestParam(required = false) String completedDateStart,
			@RequestParam(required = false) String completedDateEnd,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String reportId,
			@RequestParam(required = false) String branch,
			@RequestParam(required = false) String username,
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("loginId", getUserIdFromSession(session));
		searchMap.put("type", AsynReport.REPORT_TYPE_CORE_TRANSACTION_REPORT);
		searchMap.put("submitedDateStart", submitedDateStart);
		searchMap.put("submitedDateEnd", submitedDateEnd);
		searchMap.put("completedDateStart", completedDateStart);
		searchMap.put("completedDateEnd", completedDateEnd);
		searchMap.put("status", status);
		searchMap.put("reportId", reportId);
		searchMap.put("branch", branch);
		searchMap.put("username", username);

		return asynReportService.searchByMapCriteria(dataTables, searchMap);
	}
	@RequestMapping("/generate")
	public String generate(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {
			
			model.addAttribute("listTransactionType", genericService.k2cTransactionTypeLookup(K2CTransactionTypeLookup.LOOKUP_K2C_TRANSACTION_LOG_TYPE));
			model.addAttribute("listFileType", genericService.lookup(Lookup.LOOKUP_REPORT_TYPE));
			putIntoRequest(model);
			
			return BASE_VIEW + GENERATE_VIEW;
		}

		return getUnauthorizedPage();
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody TransactionParam param, HttpSession session) {
		
		String params = param.getTransDateStart() + "|" + 
						param.getTransDateEnd() + "|" + 
						param.getTransType() + "|END";

		AsynReport report = new AsynReport();
		report.setType(AsynReport.REPORT_TYPE_K2C_TRANSACTION_REPORT);
		report.setParam(params);
		report.setFileType(param.getType());
		report.setStatus(Lookup.LOOKUP_REPORT_STATUS_WAITING);
		report.setSubmitBy(getLoginSecUser(session));
		report.setSubmitedDate(new Date(System.currentTimeMillis()));
		
		asynReportService.saveOrUpdate(report);
		
		
		return new AjaxResponse(report);
	}
	
	
	
	 public Boolean DeleteTrn(String session_id) {
		 
		 	log.info("######Deleting data in public.t_temp_core_transaction########");
		 	log.info("Session ID: " + session_id);
	        String SQL = "DELETE FROM public.t_temp_core_transaction WHERE session_id = ?";

	        int affectedrows = 0;

	        try (Connection conn = ConnectToDB();
	                PreparedStatement pstmt = conn.prepareStatement(SQL)) {

	            pstmt.setString(1, session_id);

	            affectedrows = pstmt.executeUpdate();
	            
	            log.info("Affected rows: " + affectedrows);
	            
	            return true;

	        } catch (SQLException ex) {
	            log.info("Unable to Delete report data: "+ ex.getMessage());
	            return false;
	        } catch (Exception ex) {
				// TODO Auto-generated catch block
	        	log.info("Unable to Delete report data: "+ex.getMessage());
				return false;
			}
	    }
	
	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("REPORT_TITLE", RPT_TITLE);
		model.addAttribute("REPORT_MENU", RPT_MENU);
		model.addAttribute("REPORT_LIST", LIST_VIEW);
		// List<ViewTransaction> listTransactions = transactionService.getAll();
		// model.addAttribute("listTransactions", listTransactions);
	}
	
	public static Connection ConnectToDB() throws Exception {

		// //###############PROD
		String url = "jdbc:postgresql://35.241.122.188:5432/mfs";
		Properties props = new Properties();
		props.setProperty("user","postgres");
		props.setProperty("password","fd5@p.@dm1n");

		// ###############TEST
		// String url = "jdbc:postgresql://34.87.184.130:5432/mfs";
		// Properties props = new Properties();
		// props.setProperty("user","postgres");
		// props.setProperty("password","p0stgR35_t3sT_p@55_2022");

		//###############PROD RBI
		// String url = "34.92.119.77";
		// String url = "jdbc:postgresql://rbi-kplus.fortress-asya.com/mfs";
		// Properties props = new Properties();
		// props.setProperty("user","postgres");
		// props.setProperty("password","p0stgres");



		props.setProperty("ssl","false");
		Connection conn = DriverManager.getConnection(url, props);
		return conn;
	}
	
	
	
	

}
