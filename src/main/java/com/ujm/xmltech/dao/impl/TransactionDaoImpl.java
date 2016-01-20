package com.ujm.xmltech.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;
import com.ujm.xmltech.dao.TransactionDao;
import com.ujm.xmltech.entity.IntegrationFiles;
import com.ujm.xmltech.entity.QIntegrationFiles;
import com.ujm.xmltech.entity.QTransaction;
import com.ujm.xmltech.entity.Transaction;
import com.ujm.xmltech.utils.BankSimulationConstants;

@Repository("TransactionDao")
public class TransactionDaoImpl implements TransactionDao {
    @PersistenceContext(unitName = BankSimulationConstants.PERSISTENCE_UNIT)
    protected EntityManager entityManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, value = BankSimulationConstants.TRANSACTION_MANAGER)
    public void createTransaction(Transaction transaction) {
        entityManager.persist(transaction);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, value = BankSimulationConstants.TRANSACTION_MANAGER)
    public void createIntegrationFile(IntegrationFiles file) {
        entityManager.persist(file);
    }
    
    
    @Override
    public IntegrationFiles findIntegrationFileByMsgId(String msgId, String nomCrea){
    	JPAQuery q = new JPAQuery();
    	QIntegrationFiles file = QIntegrationFiles.integrationFiles;
    	q.from(file);
    	q.where(file.msgId.eq(msgId), file.nomCreancier.eq(nomCrea));
    	return q.uniqueResult(file);
    }
    
    @Override
    public Transaction findTransactionById(long id) {
        JPAQuery q = new JPAQuery();
        QTransaction transaction = QTransaction.transaction;
        q.from(transaction);
        q.where(transaction.id.eq(id));
        return q.uniqueResult(transaction);
    }

}
