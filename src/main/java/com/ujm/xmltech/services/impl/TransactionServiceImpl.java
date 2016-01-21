package com.ujm.xmltech.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ujm.xmltech.dao.TransactionDao;
import com.ujm.xmltech.entity.IntegrationFiles;
import com.ujm.xmltech.entity.OperationReport;
import com.ujm.xmltech.entity.Transaction;
import com.ujm.xmltech.services.TransactionService;

@Service("TransactionService")
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  private TransactionDao dao;

  public void createTransaction(Transaction transaction) {
    dao.createTransaction(transaction);
  }
  public void createIntegrationFile(IntegrationFiles file) {
	dao.createIntegrationFile(file);
  }
  
  public IntegrationFiles findIntegrationFileByMsgId(String msgId, String nomCrea){
	  return dao.findIntegrationFileByMsgId(msgId, nomCrea);
  }
  
  public void createOperationReport(OperationReport op){
	  dao.createOperationReport(op);
  }
}