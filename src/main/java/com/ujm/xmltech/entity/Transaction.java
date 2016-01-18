package com.ujm.xmltech.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
  
  

}
