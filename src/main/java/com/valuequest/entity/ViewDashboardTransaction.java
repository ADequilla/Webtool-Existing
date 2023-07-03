package com.valuequest.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mfs.view_dashboard_transaction")
public class ViewDashboardTransaction implements Serializable {

	private static final long serialVersionUID = -4766966240697392210L;

	@Id
	@Column(name = "transaction_type")
	private String transactionType;

	@Column(name = "number")
	private int number;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "agent_income")
	private BigDecimal agentIncome;

	@Column(name = "bank_income")
	private BigDecimal bankIncome;

	public ViewDashboardTransaction() {

	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAgentIncome() {
		return agentIncome;
	}

	public void setAgentIncome(BigDecimal agentIncome) {
		this.agentIncome = agentIncome;
	}

	public BigDecimal getBankIncome() {
		return bankIncome;
	}

	public void setBankIncome(BigDecimal bankIncome) {
		this.bankIncome = bankIncome;
	}

}
