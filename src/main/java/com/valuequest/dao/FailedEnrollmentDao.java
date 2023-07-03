package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.ListFailedEnrollment;

public interface FailedEnrollmentDao extends InterfaceBaseDao<ListFailedEnrollment> {

	public ListFailedEnrollment findById(Long id);

	public void delete(List<Long> ids);

}
