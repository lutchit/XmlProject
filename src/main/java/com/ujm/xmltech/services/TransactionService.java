package com.ujm.xmltech.services;

import com.ujm.xmltech.entity.IntegrationFiles;
import com.ujm.xmltech.entity.OperationReport;
import com.ujm.xmltech.entity.Transaction;

public interface TransactionService {

	void createTransaction(Transaction transaction);
	
	void createIntegrationFile(IntegrationFiles file);
	
	void createOperationReport(OperationReport op);
	
	IntegrationFiles findIntegrationFileByMsgId(String msgId, String nomCrea);
	
}