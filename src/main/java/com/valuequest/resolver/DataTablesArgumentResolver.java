package com.valuequest.resolver;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.valuequest.common.DataTables;

public class DataTablesArgumentResolver implements WebArgumentResolver {
	 
  private static final String S_ECHO = "sEcho";
  private static final String I_DISPLAY_START = "iDisplayStart";
  private static final String I_DISPLAY_LENGTH = "iDisplayLength";
  private static final String I_SORTING_COLS = "iSortingCols";
 
  private static final String I_SORT_COLS = "iSortCol_";
  private static final String S_SORT_DIR = "sSortDir_";
  private static final String S_DATA_PROP = "mDataProp_";
  private static final String B_SORTABLE = "bSortable_";
  private static final String I_COLUMNS = "iColumns";
 
  public Object resolveArgument(MethodParameter param, NativeWebRequest request) throws Exception
  {
	  HttpServletRequest httpRequest = (HttpServletRequest) request.getNativeRequest();
 
      String sEcho = httpRequest.getParameter(S_ECHO);
      String sDisplayStart = httpRequest.getParameter(I_DISPLAY_START);
      String sDisplayLength = httpRequest.getParameter(I_DISPLAY_LENGTH);
      String sSortingCols = httpRequest.getParameter(I_SORTING_COLS);
      String sColumns = httpRequest.getParameter(I_COLUMNS);
      List<String> mDataProps = new ArrayList<String>();
      List<String> sSortDirs = new ArrayList<String>();
      List<Boolean> bSortables = new ArrayList<Boolean>();
      List<Integer> iSortCols = new ArrayList<Integer>();
 
      Integer iEcho = Integer.parseInt(sEcho);
      Integer iDisplayStart = Integer.parseInt(sDisplayStart);
      Integer iDisplayLength = Integer.parseInt(sDisplayLength);
      Integer iSortingCols = Integer.parseInt(sSortingCols);
      Integer iColumns = Integer.parseInt(sColumns);
      for(int i=0;i<iColumns;i++){
    	  mDataProps.add(httpRequest.getParameter(S_DATA_PROP+i));
    	  bSortables.add("true".equals(httpRequest.getParameter(B_SORTABLE+i)));
      }
      
      for(int colCount=0; colCount<iSortingCols; colCount++)
      {
        Integer iSortCol = Integer.parseInt(httpRequest.getParameter(I_SORT_COLS+colCount));
        String sSortDir = httpRequest.getParameter(S_SORT_DIR+colCount);
        iSortCols.add(iSortCol);
        sSortDirs.add(sSortDir);
      }
 
      DataTables dt = new DataTables();
      dt.setiDisplayLength(iDisplayLength);
      dt.setiDisplayStart(iDisplayStart);
      dt.setsEcho(iEcho);
      dt.setiColumns(iColumns);
      dt.setmDataProp(mDataProps);
      dt.setsSortDir(sSortDirs);
      dt.setiSortingCols(iSortingCols);
      dt.setbSortable(bSortables);
      dt.setiSortCol(iSortCols);
      return dt;
  }	
	
}
