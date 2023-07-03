package com.valuequest.dao;

import com.valuequest.entity.MerchantEntity;

public interface MerchantDao extends InterfaceBaseDao<MerchantEntity>{

	Boolean ifExist(Long id, String businessName);

}
