package com.valuequest.dao;

import java.util.List;

//import com.valuequest.entity.StructureRemittance;
import com.valuequest.entity.ViewRemittance;

public interface RemittanceDao extends InterfaceBaseDao<ViewRemittance> {

	public ViewRemittance findById(Long id);

	public void delete(List<Long> ids);

}
