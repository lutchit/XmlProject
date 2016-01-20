package com.ujm.xmltech.dao;

import com.ujm.xmltech.entity.IntegrationFiles;
import com.ujm.xmltech.entity.OperationReport;
import com.ujm.xmltech.entity.StatusEnumeration;
import com.ujm.xmltech.entity.Transaction;

public interface TransactionDao {
	
  void createTransaction(Transaction transaction);
 
  void createIntegrationFile(IntegrationFiles file);

  void createOperationReport(OperationReport op);
  
  Transaction findTransactionById(long id);
  
  IntegrationFiles findIntegrationFileByMsgId(String msgId, String nomCrea);
  
  IntegrationFiles findIntegrationFileByStatus(StatusEnumeration status);
  
  void UpdateStatus(String msgId, StatusEnumeration status);

}
