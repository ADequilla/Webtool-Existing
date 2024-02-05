package com.valuequest.controller.maintenance;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
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

import com.valuequest.common.AjaxResponse;
import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.ParamConfig;
import com.valuequest.entity.ProductAndService;
import com.valuequest.entity.security.SecUser;
import com.valuequest.util.Constantas;
import com.valuequest.util.HttpClientUpload;
    
@Controller
@RequestMapping("/utilities/product-service")
public class ProductAndServiceController extends BaseController {

	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "PRODUCT_SERVICE";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "product-service";
	private String EDIT_VIEW 		= "product-service-edit";

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
			@RequestParam(required = false) String name,
			HttpSession session, HttpServletResponse response) {
			response.setHeader("X-Frame-Options", "DENY");

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("name", name);

		return productService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {

			//model.addAttribute("ImageMaxSize", getUploadImageSizeMax());
			model.addAttribute("productServicesStatus", genericService.lookup(Lookup.LOOKUP_PRODUCT_SERVICES_STATUS));
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage(); 
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("product", productService.findById(id));
			model.addAttribute("productServicesStatus", genericService.lookup(Lookup.LOOKUP_PRODUCT_SERVICES_STATUS));
			//model.addAttribute("ImageMaxSize", getUploadImageSizeMax());
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<StateModel> states, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			productService.delete(states);

			return new AjaxResponse(true, "Product and Services succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody ProductAndService product, Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		String dir = getProductImageDirectory();
		if (StringUtils.isNotBlank(dir)) {
			// if (StringUtils.isNotBlank(product.getBanner())) {
			// 	try {
			// 		String base64Image	= product.getBanner().split(",")[1];
			// 		String extImage 	= (product.getBanner().split("/")[1]).split(";")[0];
			// 		byte[] imageBytes 	= javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
			// 		BufferedImage img 	= ImageIO.read(new ByteArrayInputStream(imageBytes));
			// 		String filename 	= null;
			// 		if (product.getId() != null) {
			// 			filename = "PS_" + product.getId() + "." + extImage;
			// 		} else {
			// 			filename = "PS_" + product.getName() + Constantas.ffilename.format(new Date()) + "." + extImage;
			// 		}

			// 		File outputfile = new File(dir, filename);
			// 		ImageIO.write(img, extImage, outputfile);

			// 		product.setBanner(filename);

			// 		/*String agentWsUrl = getAgentProductWsUrl();
			// 		if (StringUtils.isNotBlank(agentWsUrl)) {
			// 			HttpClientUpload httpClient = new HttpClientUpload();
			// 			String message = httpClient.upload(agentWsUrl, dir, filename);
			// 			if (!"OK".equalsIgnoreCase(message)) {

			// 				return new AjaxResponse(false, message);
			// 			}
			// 		}*/

			// 		String memberWsUrl = getMemberProductWsUrl();
			// 		if (StringUtils.isNotBlank(memberWsUrl)) {
			// 			HttpClientUpload httpClient = new HttpClientUpload();
			// 			String message = httpClient.upload(memberWsUrl, dir, filename);
			// 			if (!"OK".equalsIgnoreCase(message)) {

			// 				return new AjaxResponse(false, message);
			// 			}
			// 		}

			// 	} catch (Exception e) {

			// 		return new AjaxResponse(false, "Internal Server Error.");
			// 	}
			// } else {
			// 	if (product.getId() != null) {
			// 		ProductAndService findProduct = productService.findById(product.getId());
			// 		if (findProduct != null) {
			// 			product.setBanner(findProduct.getBanner());
			// 		}
			// 	}
			// }

			productService.save(product, getLoginSecUser(session));

			return new AjaxResponse(product);
		} else {

			return new AjaxResponse(false, "Directory image banner not configure.");
		}
	}

	@RequestMapping(value = "/banner/{id}", method = RequestMethod.GET)
	public void download(@PathVariable Long id, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		try {
			ProductAndService product	= (ProductAndService) productService.findById(id);
			String filename 			= product.getBanner();
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
	
/*	private String getAgentProductWsUrl() {

		return genericService.getConfigValueByName(ParamConfig.WS_PARAMCONFIG_BANNER_AGENT_URL);
	}*/

	private String getMemberProductWsUrl() {

		return genericService.getConfigValueByName(ParamConfig.WS_PARAMCONFIG_BANNER_MEMBER_URL);
	}
}