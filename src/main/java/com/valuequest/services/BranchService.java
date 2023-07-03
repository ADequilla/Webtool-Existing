package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.maintenance.model.BranchModel;
import com.valuequest.entity.StructureBranch;
import com.valuequest.entity.UserBranch;
import com.valuequest.entity.security.SecUser;

public interface BranchService {

	public StructureBranch findByCode(String code);

	public boolean isExist(String code);

	public void save(BranchModel model, SecUser user);

	public void delete(List<StateModel> states);

	public List<StructureBranch> mappedListBy(String[] institutions);

	public List<StructureBranch> mappedList();
	
	public List<StructureBranch> mappedListBy(Long userId);
	
	public List<UserBranch> getUserBranchList(Long userId);

	public DetachedCriteria criteriaBy(Long userId);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<StructureBranch> getAllBranch();
	
	public List<StructureBranch> mappedListByMcUser(Long mcUserId);

	public DetachedCriteria criteriaByMcUser(Long mcUserId);
}
