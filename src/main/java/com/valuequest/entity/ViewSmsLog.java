package com.valuequest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.util.CustomJsonDateSerializer;
import com.valuequest.entity.util.CustomJsonDatetimeSerializer;

@Entity
@Table(name = "mfs.view_sms_log")
public class ViewSmsLog implements Serializable {

	private static final long serialVersionUID = -4766991230697392210L;

	@Id
	@Column(name = "msg_id")
	private Long msgId;

	@Column(name = "trans_id")
	private Long transId;

	@Column(name = "msg_desc")
	private String messageDesc;

	@Column(name = "msg_status")
	private String status;

	@Column(name = "msg_command")
	private String messageCommand;

	@Column(name = "msg_sent_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date sendDate;

	@Column(name = "trans_mobile_refno")
	private String transMobileRefNo;

	@Column(name = "trans_code_refno")
	private String transCodeRefNo;

	@Column(name = "trans_id_mcpro")
	private String transIdMcpro;

	@Column(name = "msisdn")
	private String mobileNumber;

	@Column(name = "msg_rsp_date")
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date msgRspDate;

	@Column(name = "activity")
	private String messageType;

	@Column(name = "cid")
	private String cid;

	@Column(name = "name")
	private String name;
	
	@Column(name = "inst_code")
	private String instCode;

	public ViewSmsLog() {
		super();
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public Long getTransId() {
		return transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}

	public String getMessageDesc() {
		return messageDesc;
	}

	public void setMessageDesc(String messageDesc) {
		this.messageDesc = messageDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessageCommand() {
		return messageCommand;
	}

	public void setMessageCommand(String messageCommand) {
		this.messageCommand = messageCommand;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getTransMobileRefNo() {
		return transMobileRefNo;
	}

	public void setTransMobileRefNo(String transMobileRefNo) {
		this.transMobileRefNo = transMobileRefNo;
	}

	public String getTransCodeRefNo() {
		return transCodeRefNo;
	}

	public void setTransCodeRefNo(String transCodeRefNo) {
		this.transCodeRefNo = transCodeRefNo;
	}

	public String getTransIdMcpro() {
		return transIdMcpro;
	}

	public void setTransIdMcpro(String transIdMcpro) {
		this.transIdMcpro = transIdMcpro;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Date getMsgRspDate() {
		return msgRspDate;
	}

	public void setMsgRspDate(Date msgRspDate) {
		this.msgRspDate = msgRspDate;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}
	
	

}
