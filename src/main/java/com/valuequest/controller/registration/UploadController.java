package com.valuequest.controller.registration;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.entity.JobProcess;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.ParamConfig;
import com.valuequest.entity.StructureBranch;
import com.valuequest.entity.StructureUnit;
import com.valuequest.entity.UploadFile;
import com.valuequest.entity.UploadFileBranch;
import com.valuequest.entity.UploadFileUnit;
import com.valuequest.entity.security.SecUser;

@Controller
@RequestMapping("/registration/upload")
public class UploadController extends BaseController {

	final static String MENU 		= "REGISTRATION";
	final static String PRIVILEDGE 	= "UPLOAD_CSV";
	private String BASE_VIEW 		= "03.registration/";
	private String LIST_VIEW 		= "upload";
	
	@RequestMapping("/")
	public String index(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {
			
			putIntoRequest(model);
			if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {
				model.addAttribute("UPLOAD_PERMISSION", true);
			}
			return BASE_VIEW + LIST_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/search")
	public @ResponseBody DataTables search(DataTables dataTables, 
			@RequestParam(required = false) String branch,
			@RequestParam(required = false) String unit, 
			@RequestParam(required = false) String uploadStatus,
			@RequestParam(required = false) Long uploadBy,
			@RequestParam(required = false) String uploadDate,
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("loginId", getLoginIdFromSession(session));
		searchMap.put("branch", branch);
		searchMap.put("unit", unit);
		searchMap.put("uploadStatus", uploadStatus);
		searchMap.put("uploadBy", uploadBy);
		
		if(StringUtils.isNotBlank(uploadDate)){
			try {
				Date date = new SimpleDateFormat("dd-MMM-yyyy").parse(uploadDate);
				searchMap.put("uploadDate", date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		DataTables dt = uploadFileService.searchByMapCriteria(dataTables, searchMap);
		return dt;
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("listUploadStatus", genericService.lookup(Lookup.LOOKUP_UPLOAD_STATUS));
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam MultipartFile files, Model model, HttpServletRequest request) {		
		  // HttpSession session 
		HttpSession session  = request.getSession();
		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {
			putIntoRequest(model);
			model.addAttribute("UPLOAD_PERMISSION", true);
	 
			String category = request.getParameter("category");
		 
			if (files.isEmpty()) {
		        model.addAttribute("err_msg", "Failed to upload file because its empty.");
		        return BASE_VIEW + LIST_VIEW;
		    }else{
		    	List<String> typeList = new ArrayList<String>();
		    	typeList.add("application/vnd.ms-excel");
		    	typeList.add("text/plain");
		    	typeList.add("text/csv");
		    	typeList.add("text/tsv");
		    	
		    	if(typeList.contains(files.getContentType())){
		    		ParamConfig config = genericService.getConfigByName(ParamConfig.WEB_PARAMCONFIG_UPLOAD_DIR);
		    	    if(config != null){
		    	    	File dir = new File(config.getValue());
		    		    if (! dir.exists()) {
		    		        dir.mkdirs();
		    		    }
		    		    
		    		    SecUser userLogin = getLoginSecUser(session);
		    	        String prefix = userLogin.getUsrLoginname() + "_" + (new SimpleDateFormat("yyyyMMdd_HHmmss_").format(new Date()));
		    	        String displayFilename = files.getOriginalFilename();
		    	        String originalFilename= prefix + files.getOriginalFilename();
		    		    File serverFile = new File(dir.getAbsolutePath() + File.separator + originalFilename);
		    		    
		    		    try {
		    		        try (InputStream is = files.getInputStream();
		    		        	BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
		    		            int i;
		    		            //write file to server
		    		            while ((i = is.read()) != -1) {
		    		                stream.write(i);
		    		            }
		    		            stream.flush();
		    		            
		    		            UploadFile uploadFile = new UploadFile();
		    		            uploadFile.setOriginalFilename(originalFilename);
		    		            uploadFile.setDisplayFilename(displayFilename);
		    		            uploadFile.setUploadDate(new Date());
		    		            uploadFile.setStatus(Lookup.LOOKUP_UPLOAD_STATUS_UPLOADED);
		    		            uploadFile.setUser(userLogin);
		    		            uploadFileService.saveOrUpdate(uploadFile);
		    		            
		    		            List<StructureBranch> branchs	= branchService.mappedListBy(userLogin.getId());
		    		            UploadFileBranch ufb = null;
		    		            for (StructureBranch sb : branchs) {
		    		            	ufb = new UploadFileBranch();
		    		            	ufb.setUploadFile(uploadFile);
		    		            	ufb.setBranch(sb);
		    		            	
		    		            	uploadFileService.saveDetail(ufb);
								}
		    		            
		    		            List<StructureUnit> units = unitService.mappedListBy(userLogin.getId());
		    		            UploadFileUnit ufu = null;
		    		            for (StructureUnit su : units) {
		    		            	ufu = new UploadFileUnit();
		    		            	ufu.setUploadFile(uploadFile);
									ufu.setUnit(su);
									
									uploadFileService.saveDetail(ufu);
								}
		    		            
		    		            JobProcess job = new JobProcess();
		    		            String jobName = category.equalsIgnoreCase("1")? JobProcess.JOB_ENROLMENT:JobProcess.JOB_ENROLMENT_EDIT;		    		           
		    					job.setName(jobName);		    		           
		    					job.setStatus(JobProcess.JOB_STATUS_WAITING);
		    					job.setFlag(1);
		    					job.setDate(new Date());
		    					job.setRefId(uploadFile.getId());
		    					
		    					jobService.saveOrUpdate(job);
		    					
		    		            model.addAttribute("msg", "CSV file successfully uploaded.");
		    		        }
		    		    } catch (IOException e) {
		    		        model.addAttribute("err_msg", "Internal Server Error.");
		    		    }
		    	    }else{
		    	    	model.addAttribute("err_msg", "Directory upload file not configure.");
		    	    }
		    	    
		    	    return BASE_VIEW + LIST_VIEW;
		    	} else {
		    		
		    		model.addAttribute("err_msg", "Please upload CSV file.");
			        return BASE_VIEW + LIST_VIEW;
		    	}
		    }
		}

		return getUnauthorizedPage();
	}
	
	
/* @RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String uploadEdit(@RequestParam MultipartFile files, Model model, HttpSession session) {		
		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			putIntoRequest(model);
			model.addAttribute("UPLOAD_PERMISSION", true);
			
			if (files.isEmpty()) {
		        model.addAttribute("err_msg", "Failed to upload file because its empty.");
		        return BASE_VIEW + LIST_VIEW;
		    }else{
		    	List<String> typeList = new ArrayList<String>();
		    	typeList.add("application/vnd.ms-excel");
		    	typeList.add("text/plain");
		    	typeList.add("text/csv");
		    	typeList.add("text/tsv");
		    	
		    	if(typeList.contains(files.getContentType())){
		    		ParamConfig config = genericService.getConfigByName(ParamConfig.WEB_PARAMCONFIG_UPLOAD_DIR);
		    	    if(config != null){
		    	    	File dir = new File("d:/temp/"); //config.getValue()
		    		    if (! dir.exists()) {
		    		        dir.mkdirs();
		    		    }
		    		    
		    		    SecUser userLogin = getLoginSecUser(session);
		    	        String prefix = userLogin.getUsrLoginname() + "_" + (new SimpleDateFormat("yyyyMMdd_HHmmss_").format(new Date()));
		    	        String displayFilename = files.getOriginalFilename();
		    	        String originalFilename= prefix + files.getOriginalFilename();
		    		    File serverFile = new File(dir.getAbsolutePath() + File.separator + originalFilename);
		    		    
		    		    try {
		    		        try (InputStream is = files.getInputStream();
		    		        	BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
		    		            int i;
		    		            //write file to server
		    		            while ((i = is.read()) != -1) {
		    		                stream.write(i);
		    		            }
		    		            stream.flush();
		    		            
		    		            UploadFile uploadFile = new UploadFile();
		    		            uploadFile.setOriginalFilename(originalFilename);
		    		            uploadFile.setDisplayFilename(displayFilename);
		    		            uploadFile.setUploadDate(new Date());
		    		            uploadFile.setStatus(Lookup.LOOKUP_UPLOAD_STATUS_UPLOADED);
		    		            uploadFile.setUser(userLogin);
		    		            uploadFileService.saveOrUpdate(uploadFile);
		    		            
		    		            List<StructureBranch> branchs	= branchService.mappedListBy(userLogin.getId());
		    		            UploadFileBranch ufb = null;
		    		            for (StructureBranch sb : branchs) {
		    		            	ufb = new UploadFileBranch();
		    		            	ufb.setUploadFile(uploadFile);
		    		            	ufb.setBranch(sb);		    		            	
		    		            	uploadFileService.saveDetail(ufb);
								}
		    		            
		    		            List<StructureUnit> units = unitService.mappedListBy(userLogin.getId());
		    		            UploadFileUnit ufu = null;
		    		            for (StructureUnit su : units) {
		    		            	ufu = new UploadFileUnit();
		    		            	ufu.setUploadFile(uploadFile);
									ufu.setUnit(su);									
									uploadFileService.saveDetail(ufu);
								}
		    		            
		    		            JobProcess job = new JobProcess();
		    					job.setName(JobProcess.JOB_ENROLMENT_EDIT);
		    					job.setStatus(JobProcess.JOB_STATUS_WAITING);
		    					job.setFlag(1);
		    					job.setDate(new Date());
		    					job.setRefId(uploadFile.getId());		    					
		    					jobService.saveOrUpdate(job);
		    					
		    		            model.addAttribute("msg", "CSV file successfully uploaded.");
		    		        }
		    		    } catch (IOException e) {
		    		        model.addAttribute("err_msg", "Internal Server Error.");
		    		    }
		    	    }else{
		    	    	model.addAttribute("err_msg", "Directory upload file not configure.");
		    	    }
		    	    
		    	    return BASE_VIEW + LIST_VIEW;
		    	} else {
		    		
		    		model.addAttribute("err_msg", "Please upload CSV file.");
			        return BASE_VIEW + LIST_VIEW;
		    	}
		    }
		}

		return getUnauthorizedPage();
	}
	
	*/
	
	
	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	public void download(@PathVariable Long id, HttpServletResponse response) {		
		try {
			UploadFile uploadFile = uploadFileService.findById(id);
			ParamConfig config = genericService.getConfigByName(ParamConfig.WEB_PARAMCONFIG_UPLOAD_DIR_FAILED);
			if(config != null){
				File dir = new File(config.getValue());
    		    File file = new File(dir.getAbsolutePath() + File.separator + uploadFile.getInvalidFilename());
    		    
    		    if(! file.exists()){
    	            String errorMessage = "Sorry. The file you are looking for does not exist";
    	            OutputStream outputStream = response.getOutputStream();
    	            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
    	            outputStream.close();
    	            return;
    	        }
    		    
    		    String mimeType= URLConnection.guessContentTypeFromName(file.getName());
    	        if(mimeType==null){
    	            mimeType = "application/octet-stream";
    	        }
    	        
    	        response.setContentType(mimeType);
    	        response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() +"\""));
    	        response.setContentLength((int)file.length());
    	        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
    	        FileCopyUtils.copy(inputStream, response.getOutputStream());
			}
		} catch (Exception e) {
			throw new RuntimeException("Internal Server Error.");	
		}
	}
}