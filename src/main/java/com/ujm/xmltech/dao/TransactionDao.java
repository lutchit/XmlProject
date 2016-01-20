package com.ujm.xmltech.dao;

import com.ujm.xmltech.entity.IntegrationFiles;
import com.ujm.xmltech.entity.Transaction;

public interface TransactionDao {

  void createTransaction(Transaction transaction);
  
  void createIntegrationFile(IntegrationFiles file);

  Transaction findTransactionById(long id);
  
  IntegrationFiles findIntegrationFileByMsgId(String msgId, String nomCrea);

}
