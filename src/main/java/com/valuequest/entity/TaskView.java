package com.valuequest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.valuequest.entity.util.CustomJsonDatetimeSerializer;

@Entity
@Table(name = "timetable.task")
public class TaskView implements Serializable {

    private static final long serialVersionUID = 1736383603848350063L;

    @Id
	@SequenceGenerator(name = "sequence", sequenceName = "timetable.task_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "task_id")
	private Long taskID;

    @Column(name = "task_name")
	private String taskName;

    @Column(name = "chain_id")
	private Long chainId;

    @Column(name = "task_order")
	private Double taskOrder;

    @Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transDate;

    
}
