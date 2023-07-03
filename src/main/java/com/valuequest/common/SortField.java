package com.valuequest.common;

public final class SortField {
	  private final String field;
	  private final String direction;
	     
	  public SortField(String field, String direction)
	  {
	    this.field = field;
	    this.direction = direction;
	  }
	    
	  public String getField() {
	    return field;
	  }
	 
	  public String getDirection() {
	    return direction;
	  }
}
