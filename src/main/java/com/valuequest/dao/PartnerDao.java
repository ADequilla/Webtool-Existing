package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructurePartner;

public interface PartnerDao extends InterfaceBaseDao<StructurePartner> {

	public StructurePartner findByPartnerId(String partnerId);

	public void delete(List<Long> ids);

}
