package com.ujm.xmltech.entity;

import java.io.Serializable;

public class IntegrationFilesID implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String msgId;
	public String nomCreancier;
	
	
	public IntegrationFilesID(String msgId, String nomCreancier) {
		super();
		this.msgId = msgId;
		this.nomCreancier = nomCreancier;
	}
	
	

}
