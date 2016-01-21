package com.ujm.xmltech.entity;

import java.io.Serializable;

public class IntegrationFilesID implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String msgId;
	public String nomCreancier;
	
	public IntegrationFilesID(){
		super();
	}
	
	public IntegrationFilesID(String msgId, String nomCreancier) {
		super();
		this.msgId = msgId;
		this.nomCreancier = nomCreancier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((msgId == null) ? 0 : msgId.hashCode());
		result = prime * result + ((nomCreancier == null) ? 0 : nomCreancier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntegrationFilesID other = (IntegrationFilesID) obj;
		if (msgId == null) {
			if (other.msgId != null)
				return false;
		} else if (!msgId.equals(other.msgId))
			return false;
		if (nomCreancier == null) {
			if (other.nomCreancier != null)
				return false;
		} else if (!nomCreancier.equals(other.nomCreancier))
			return false;
		return true;
	}
	
	
	
	

}