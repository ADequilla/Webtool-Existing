package com.valuequest.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "mfs.t_temp_dashboard")
public class DashboardTemp implements Serializable {

	private static final long serialVersionUID = -4766966130697392290L;

	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.t_temp_dashboard_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "id", length = 15)
	private Long id;
	@Column(name = "transaction_type", length = 15)
	private String transactionType;
	@Column(name = "number_total")
	private int numberTotal;
	@Column(name = "amount")
	private BigDecimal amount;
	@Column(name = "agent_income")
	private BigDecimal agentIncome;
	@Column(name = "bank_income")
	private BigDecimal bankIncome;

	public DashboardTemp() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public int getNumberTotal() {
		return numberTotal;
	}

	public void setNumberTotal(int numberTotal) {
		this.numberTotal = numberTotal;
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
