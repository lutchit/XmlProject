package com.ujm.xmltech.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class IntegrationFiles implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String msgId;
	
	@Id
	private String nomCreancier;
	
	private double montantTransaction;
	
	private int nbTransaction;
	
	private StatusEnumeration traite;
	
	public IntegrationFiles() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getNomCreancier() {
		return nomCreancier;
	}

	public void setNomCreancier(String nomCreancier) {
		this.nomCreancier = nomCreancier;
	}

	public double getMontantTransaction() {
		return montantTransaction;
	}

	public void setMontantTransaction(double montantTransaction) {
		this.montantTransaction = montantTransaction;
	}

	public int getNbTransaction() {
		return nbTransaction;
	}

	public void setNbTransaction(int nbTransaction) {
		this.nbTransaction = nbTransaction;
	}

	public StatusEnumeration getTraite() {
		return traite;
	}

	public void setTraite(StatusEnumeration traite) {
		this.traite = traite;
	}
	
	

}