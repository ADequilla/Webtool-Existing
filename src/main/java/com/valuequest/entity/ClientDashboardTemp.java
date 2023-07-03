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
@Table(name = "mfs.t_temp_client_dashboard")
public class ClientDashboardTemp implements Serializable {

	private static final long serialVersionUID = -4766966130697302290L;

	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.t_temp_client_dashboard_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "id", length = 15)
	private Long id;

	@Column(name = "client_id", length = 15)
	private String clientId;

	@Column(name = "client_name")
	private String clientName;

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

	public ClientDashboardTemp() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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
