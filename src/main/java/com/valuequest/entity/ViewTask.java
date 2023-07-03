package com.valuequest.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.util.CustomJsonDateSerializer;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "timetable.view_task")
public class ViewTask implements Serializable{

    private static final long serialVersionUID = -1L;

    @Id
    @Column(name = "task_id")
    private Long taskID;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "chain_id")
    private Long chainID;

    @Column(name = "chain_name")
    private String chainName;

    @Column(name = "task_order")
    private Long taskOrder;

    @Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date createdAt;




}
