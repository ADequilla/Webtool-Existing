package com.valuequest.common;

import java.util.ArrayList;
import java.util.List;

public class DataTables {
    private Integer sEcho;

    private String sSearch;

    private List<String> mDataProp = new ArrayList<String>();

    private Integer iSortingCols;
    private List<Integer> iSortCol = new ArrayList<Integer>();
    private List<String> sSortDir = new ArrayList<String>();

    private Integer iDisplayStart;
    private Integer iDisplayLength;
    private Integer iColumns;

    private Long iTotalRecords;
    private Long iTotalDisplayRecords;
    private List<Boolean> bSortable = new ArrayList<Boolean>();

    private List aaData = new ArrayList();

    public DataTables extract(List aaData, Long iTotalRecords) {
        this.aaData = aaData;
        this.iTotalRecords = this.iTotalDisplayRecords = iTotalRecords;

        return this;
    }
    
    public void copyData(DataTables source,DataTables target){
    	target.iDisplayLength = source.getiDisplayLength();
    	target.iDisplayStart = source.getiDisplayStart();
    	target.iSortCol = source.getiSortCol();
    	target.iSortingCols = source.getiSortingCols();
    	target.iTotalDisplayRecords = source.getiTotalDisplayRecords();
    	target.iTotalRecords =source.getiTotalRecords();
    	target.mDataProp = source.getmDataProp();
    	target.sEcho = source.getsEcho();
    	target.sSearch = source.getsSearch();
    	target.sSortDir = source.getsSortDir();
    }

    public Integer getsEcho() {
        return sEcho;
    }
    public void setsEcho(Integer sEcho) {
        this.sEcho = sEcho;
    }
    public String getsSearch() {
        return sSearch;
    }
    public void setsSearch(String sSearch) {
        this.sSearch = sSearch;
    }
    public Integer getiSortingCols() {
        return iSortingCols;
    }
    public List<Integer> getiSortCol() {
        return iSortCol;
    }
    public void setiSortCol(List<Integer> iSortCol) {
        this.iSortCol = iSortCol;
    }
    public List<String> getmDataProp() {
        return mDataProp;
    }
    public void setmDataProp(List<String> mDataProp) {
        this.mDataProp = mDataProp;
    }
    public List<String> getsSortDir() {
        return sSortDir;
    }
    public void setsSortDir(List<String> sSortDir) {
        this.sSortDir = sSortDir;
    }
    public void setiSortingCols(Integer iSortingCols) {
        this.iSortingCols = iSortingCols;
    }
    public Integer getiDisplayStart() {
        return iDisplayStart;
    }
    public void setiDisplayStart(Integer iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }
    public Integer getiDisplayLength() {
        return iDisplayLength;
    }
    public void setiDisplayLength(Integer iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }
    public Long getiTotalRecords() {
        return iTotalRecords;
    }
    public void setiTotalRecords(Long iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }
    public Long getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }
    public void setiTotalDisplayRecords(Long iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }
    public List getAaData() {
        return aaData;
    }
    public void setAaData(List aaData) {
        this.aaData = aaData;
    }

	/**
	 * @return the bSortable
	 */
	public List<Boolean> getbSortable() {
		return bSortable;
	}

	/**
	 * @param bSortable the bSortable to set
	 */
	public void setbSortable(List<Boolean> bSortable) {
		this.bSortable = bSortable;
	}
    
	/**
	 * @return the iColumns
	 */
	public Integer getiColumns() {
		return iColumns;
	}

	/**
	 * @param iColumns the iColumns to set
	 */
	public void setiColumns(Integer iColumns) {
		this.iColumns = iColumns;
	}

	public List<SortField> generateSortFields(){
		List<SortField> sfields = new ArrayList<SortField>();
		if (!this.mDataProp.isEmpty()) {
			for (int i =0;i<this.iSortingCols;i++){
				int colIdx = this.iSortCol.get(i);
				if (this.bSortable.get(colIdx)){
					sfields.add(new SortField(this.mDataProp.get(colIdx),this.sSortDir.get(i)));
				}
			}
		}
		return sfields;
	}
}
