package com.ujm.xmltech.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class File implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -666910219377627467L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String msgId;
	
	private String nomCreancier;
	
	private int nbTransaction;
	
	private float montantTransaction;
	
	private boolean traite;
	
	public String getNomCreancier() {
		return nomCreancier;
	}

	public void setNomCreancier(String nomCreancier) {
		this.nomCreancier = nomCreancier;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public int getNbTransaction() {
		return nbTransaction;
	}

	public void setNbTransaction(int nbTransaction) {
		this.nbTransaction = nbTransaction;
	}

	public float getMontantTransaction() {
		return montantTransaction;
	}

	public void setMontantTransaction(float montantTransaction) {
		this.montantTransaction = montantTransaction;
	}

	public boolean isTraite() {
		return traite;
	}

	public void setTraite(boolean traite) {
		this.traite = traite;
	}

}
