package com.valuequest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "mfs.t_temp_agent_dashboard")
public class AgentDashboardTemp implements Serializable {

	private static final long serialVersionUID = -4766966130697302290L;

	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.t_temp_agent_dashboard_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "id", length = 15)
	private Long id;

	@Column(name = "branch_code", length = 15)
	private String branchCode;

	@Column(name = "branch_desc")
	private String branchDesc;

	@Column(name = "cash_in")
	private String cashIn;

	@Column(name = "cash_out")
	private String cashOut;

	@Column(name = "agent_assisted_payment")
	private String agentAssistedPayment;

	@Column(name = "bill_payment")
	private String billPayment;

	@Column(name = "total")
	private String total;

	@Column(name = "total_income")
	private String totalIncome;

	public AgentDashboardTemp() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchDesc() {
		return branchDesc;
	}

	public void setBranchDesc(String branchDesc) {
		this.branchDesc = branchDesc;
	}

	public String getCashIn() {
		return cashIn;
	}

	public void setCashIn(String cashIn) {
		this.cashIn = cashIn;
	}

	public String getCashOut() {
		return cashOut;
	}

	public void setCashOut(String cashOut) {
		this.cashOut = cashOut;
	}

	public String getAgentAssistedPayment() {
		return agentAssistedPayment;
	}

	public void setAgentAssistedPayment(String agentAssistedPayment) {
		this.agentAssistedPayment = agentAssistedPayment;
	}

	public String getBillPayment() {
		return billPayment;
	}

	public void setBillPayment(String billPayment) {
		this.billPayment = billPayment;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
	}

}
