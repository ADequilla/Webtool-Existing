package com.valuequest.controller.maintenance;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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

import com.valuequest.common.AjaxResponse;
import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.ParamConfig;
import com.valuequest.entity.Product;
import com.valuequest.entity.StructureInstitution;
import com.valuequest.entity.StructureUser;
import com.valuequest.entity.UserInstitution;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.SmsLogsService;
import com.valuequest.services.impl.SimpleServiceImpl;
import com.valuequest.util.Constantas;
import com.valuequest.util.HttpClientUpload;
  
@Controller
@RequestMapping("/utilities/news")
public class BankNewsController extends BaseController {

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

	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "BANK_NEWS";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "news";
	private String EDIT_VIEW 		= "news-edit";

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
			@RequestParam(required = false) String topic,
			HttpSession session, HttpServletResponse response) {
			response.setHeader("X-Frame-Options", "DENY");
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("topic", topic);

		return productInfoService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {

			model.addAttribute("ImageMaxSize", getUploadImageSizeMax());
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("product", productInfoService.findById(id));
			model.addAttribute("ImageMaxSize", getUploadImageSizeMax());
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<StateModel> states, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			productInfoService.delete(states);

			return new AjaxResponse(true, "Bank News succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody Product product, Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		String dir = getProductImageDirectory();
		if (StringUtils.isNotBlank(dir)) {
// 			if (StringUtils.isNotBlank(product.getProductImg())) {
// 				try {
// 					String base64Image	= product.getProductImg().split(",")[1];
// 					String extImage 	= (product.getProductImg().split("/")[1]).split(";")[0];
// 					byte[] imageBytes 	= javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
// 					BufferedImage img 	= ImageIO.read(new ByteArrayInputStream(imageBytes));
// 					String filename 	= null;
// 					if (product.getId() != null) {
// 						filename = product.getId() + "." + extImage;
// 					} else {
// 						filename = product.getProductName() + "_" + Constantas.ffilename.format(new Date()) + "." + extImage;
// 					}

// 					File outputfile = new File(dir, filename);
// 					ImageIO.write(img, extImage, outputfile);

// 					product.setProductImg(filename);

// //					String agentWsUrl = getAgentProductWsUrl();
// //					if (StringUtils.isNotBlank(agentWsUrl)) {
// //						HttpClientUpload httpClient = new HttpClientUpload();
// //						String message = httpClient.upload(agentWsUrl, dir, filename);
// //						if (!"OK".equalsIgnoreCase(message)) {
// //
// //							return new AjaxResponse(false, message);
// //						}
// //					}

// 					String memberWsUrl = getMemberProductWsUrl();
// 					System.out.println("######### memberWsUrl = "+memberWsUrl);
// 					if (StringUtils.isNotBlank(memberWsUrl)) {
// 						HttpClientUpload httpClient = new HttpClientUpload();
// 						String message = httpClient.upload(memberWsUrl, dir, filename);
// 						if (!"OK".equalsIgnoreCase(message)) {

// 							return new AjaxResponse(false, message);
// 						}
// 					}

// 				} catch (Exception e) {

// 					return new AjaxResponse(false, "Internal Server Error.");
// 				}
// 			} else {
// 				if (product.getId() != null) {
// 					Product findProduct = productInfoService.findById(product.getId());
// 					if (findProduct != null) {
// 						product.setProductImg(findProduct.getProductImg());
// 					}
// 				}
// 			}

			productInfoService.save(product, getLoginSecUser(session));

			return new AjaxResponse(product);
		} else {

			return new AjaxResponse(false, "Directory image banner not configure.");
		}
	}

	@RequestMapping(value = "/banner/{id}", method = RequestMethod.GET)
	public void download(@PathVariable Long id, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		try {
			Product product				= (Product) productInfoService.findById(id);
			String filename 			= product.getProductImg();
			String dir 					= getProductImageDirectory();
			File file 					= new File(dir, filename);
			BufferedImage buffImage 	= ImageIO.read(file);
			ByteArrayOutputStream baos 	= new ByteArrayOutputStream();
			ImageIO.write(buffImage, "png", baos);
			baos.flush();
			byte[] imageData = baos.toByteArray();

			response.setContentType("image/png");
			response.getOutputStream().write(imageData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
	
	
	
	private String getProductImageDirectory() {
		String value = genericService.getConfigValueByName(ParamConfig.WEB_PARAMCONFIG_PROMO_BANNER);
		if (StringUtils.isNotBlank(value)) {
			File dir = new File(value);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			return value;
		}

		return null;
	}
	
	private Long getUploadImageSizeMax() {
		Long size = 0L;
		String value = genericService.getConfigValueByName(ParamConfig.WEB_UPLOAD_IMAGE_SIZE_MAX);
		if (StringUtils.isNotBlank(value)) {
			size = Long.valueOf(value);
		}

		return size;
	}
	
	// private String getAgentProductWsUrl() {

	// 	return genericService.getConfigValueByName(ParamConfig.WS_PARAMCONFIG_BANNER_AGENT_URL);
	// }

	private String getMemberProductWsUrl() {

		return genericService.getConfigValueByName(ParamConfig.WS_PARAMCONFIG_BANNER_MEMBER_URL);
	}
}