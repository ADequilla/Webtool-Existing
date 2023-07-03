package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.mbo.model.MboModel;
import com.valuequest.controller.monitoring.model.ProfileModel;
import com.valuequest.entity.Client;
import com.valuequest.entity.ViewClient;
import com.valuequest.entity.security.SecUser;

public interface ClientService {

	public Client findById(Long id);

	public ViewClient findByCid(String cid);
	
	public ViewClient findByCidAndTypeCodeClientType(String cid);
	
	public ViewClient findByCidAndTypeCode(String cid);

	public ViewClient profile(ProfileModel model);
	
	public ViewClient profileMcu(ProfileModel model);
	
	public ViewClient findByAccountAndTypeCode(String account);

	public List<ViewClient> list(String name, int size);

	public DataTables searchByMapCriteriaListClients(DataTables dataTables, HashMap<String, Object> searchMap);

	public void save(Client client, SecUser userLogin);
	
	public void save(MboModel model, SecUser userLogin);

	public void saveMcu(MboModel model, SecUser userLogin);
	
	public void update(List<StateModel> states, String status, SecUser userLogin);

	public void resendActcode(Client client, SecUser userLogin);

	public void resetPassword(Client client, SecUser userLogin);
	
	public String resetPasswordRandom(Client client, SecUser userLogin);

	public void resetPin(Client client, SecUser userLogin);
	
	public String resetPinAuthor(Client client, SecUser userLogin);

	public void deactivate(Client client, SecUser userLogin);
	
	public void deactivateMbo();
	
	public void sendActCodeMbo();
	
	public void resetStatus(Client client, SecUser userLogin);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public DataTables searchByMapCriteriaTransactionLog(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public DataTables searchByMapCriteriaMCollectionUser(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public DataTables searchByMapCriteriaAoAccount(DataTables dataTables, HashMap<String, Object> searchMap);

	public ViewClient aoAccountprofile(ProfileModel model);
}