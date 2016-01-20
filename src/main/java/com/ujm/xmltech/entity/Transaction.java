package com.ujm.xmltech.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Transaction implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 8315057757268890401L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String BIC;
  
  private long amount;

  private String endToEndId;
  
  private String currency;
  
  @Temporal(TemporalType.DATE)
  private Date date;
  
  private String sequence;

  @OneToOne
  private IntegrationFiles fileId;
  
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }

  public String getEndToEndId() {
    return endToEndId;
  }

  public void setEndToEndId(String endToEndId) {
    this.endToEndId = endToEndId;
  }

public String getBIC() {
	return BIC;
}

public void setBIC(String bIC) {
	BIC = bIC;
}

public IntegrationFiles getFileId() {
	return fileId;
}

public void setFileId(IntegrationFiles fileId) {
	this.fileId = fileId;
}

public static long getSerialversionuid() {
	return serialVersionUID;
}



public String getCurrency() {
	return currency;
}

public void setCurrency(String currency) {
	this.currency = currency;
}

public Date getDate() {
	return date;
}

public void setDate(Date date) {
	this.date = date;
}

public String getSequence() {
	return sequence;
}

public void setSequence(String sequence) {
	this.sequence = sequence;
}
  
  

}
