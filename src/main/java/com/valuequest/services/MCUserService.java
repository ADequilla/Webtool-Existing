/**
 * 
 */
package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.mobilecollection.model.McUserModel;
import com.valuequest.entity.LogUploadFile;
import com.valuequest.entity.MCUser;
import com.valuequest.entity.MCUserMapper;
import com.valuequest.entity.TempAccountOfficer;
import com.valuequest.entity.security.SecUser;

/**
 * @author Eki Nurhadi
 *
 */
public interface MCUserService {

	public void resetMCUserPassword(String mcuid, SecUser loginSecUser);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap, Boolean isPendingData);

	public MCUser getMCUserByMcuId(String mcuId);

	public void save(McUserModel model, SecUser loginSecUser);
	
	public void saveUpload(MCUser model, SecUser loginSecUser);

	public MCUser findById(Long id);

	public MCUser getMCUserByStaffId(String staffId);

	public List<MCUserMapper> searchByMapCriteria(HashMap<String, Object> map);

	public void update(List<StateModel> states, String lookupApprovalStatusApproved, SecUser loginSecUser);

	public void resetMCUserPin(String mcuid, SecUser loginSecUser);

	public void deactiveOrReactive(String mcuid, Integer deactive, SecUser loginSecUser);

	public void changeAccStatusByMcuId(String mcuid, String inactive, SecUser loginSecUser);

	public void uploadFile(List<MCUser> mcUsers, List<TempAccountOfficer> tempAccountOfficers, LogUploadFile logUploadFile, SecUser loginSecUser);

	public MCUser getMCUserByInternalAccount(String internalAccount);

	public MCUser getMCUserByMobileNumber(String mobileNumber);

}
