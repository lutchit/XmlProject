package com.ujm.xmltech.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OperationReport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private CodeErreurEnum codeErreur;
	
	@OneToOne
	private Transaction transactionId;
	
	@OneToOne
	private IntegrationFiles fileId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public CodeErreurEnum getCodeErreur() {
		return codeErreur;
	}

	public void setCodeErreur(CodeErreurEnum codeErreur) {
		this.codeErreur = codeErreur;
	}

	public Transaction getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Transaction transactionId) {
		this.transactionId = transactionId;
	}

	public IntegrationFiles getFileId() {
		return fileId;
	}

	public void setFileId(IntegrationFiles fileId) {
		this.fileId = fileId;
	}

	
	
	
}