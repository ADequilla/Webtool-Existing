package com.valuequest.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.valuequest.common.DataTables;
import com.valuequest.controller.api.model.CustomerLoanList;
import com.valuequest.entity.ViewTransaction;

public interface CustomerLoanListService {
	public CustomerLoanList findByAcc(String acc);
	public String GetAll();
	public DataTables searchByMapCriteria1(DataTables dataTables, HashMap<String, Object> searchMap, CustomerLoanList[] custLoanList);

}
