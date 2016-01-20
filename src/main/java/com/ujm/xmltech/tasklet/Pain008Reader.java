package com.ujm.xmltech.tasklet;

import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.ujm.xmltech.entity.CodeErreurEnum;
import com.ujm.xmltech.entity.IntegrationFiles;
import com.ujm.xmltech.entity.OperationReport;
import com.ujm.xmltech.entity.StatusEnumeration;
import com.ujm.xmltech.entity.Transaction;
import com.ujm.xmltech.services.TransactionService;
import com.ujm.xmltech.utils.BankSimulationConstants;
import com.ujm.xmltech.utils.Banks;

import iso.std.iso._20022.tech.xsd.pain_008_001.DirectDebitTransactionInformation9;
import iso.std.iso._20022.tech.xsd.pain_008_001.Document;
import iso.std.iso._20022.tech.xsd.pain_008_001.GroupHeader39;
import iso.std.iso._20022.tech.xsd.pain_008_001.ObjectFactory;
import iso.std.iso._20022.tech.xsd.pain_008_001.PaymentInstructionInformation4;

public class Pain008Reader implements Tasklet {
	
	@Autowired
	private TransactionService service;

  @Override
  public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
    read((String) arg1.getStepContext().getJobParameters().get("inputFile"));
    return RepeatStatus.FINISHED;
  }

  @SuppressWarnings("rawtypes")
  public Object read(String fileName) throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    JAXBContext jc;
    try {
      jc = JAXBContext.newInstance(ObjectFactory.class);
      Unmarshaller u = jc.createUnmarshaller();
      File f = new File(BankSimulationConstants.WORK_DIRECTORY + fileName);
      FileReader fileReader = new FileReader(f);
      JAXBElement element = (JAXBElement) u.unmarshal(fileReader);
      Document document = (Document) element.getValue();
      GroupHeader39 header = document.getCstmrDrctDbtInitn().getGrpHdr();
      System.out.println(header.getMsgId());
      
      
      if (checkIntegrationFile(header)){
    	  
	      IntegrationFiles file = new IntegrationFiles();
	      
	      file.setMsgId(header.getMsgId());
	      file.setNomCreancier(header.getInitgPty().getNm());
	      file.setNbTransaction(Integer.valueOf(header.getNbOfTxs()));
	      file.setMontantTransaction(header.getCtrlSum().doubleValue());
	      file.setTraite(StatusEnumeration.EN_COURS);
	      
	      System.out.println(file.getMsgId() + file);
	      
	      service.createIntegrationFile(file);
	      
	      
	      Iterator<PaymentInstructionInformation4> it = document.getCstmrDrctDbtInitn().getPmtInf().iterator();
	      while (it.hasNext()) {
	    	    
	        PaymentInstructionInformation4 transaction = it.next();
	        
	        Iterator<DirectDebitTransactionInformation9> it2 = transaction.getDrctDbtTxInf().iterator();
	        while(it2.hasNext()){
	        	Transaction newTransaction = new Transaction();
	        	DirectDebitTransactionInformation9 infoTransaction = it2.next();
	        	
	        	newTransaction.setAmount(infoTransaction.getInstdAmt().getValue().longValue());
	        	newTransaction.setEndToEndId(infoTransaction.getPmtId().getEndToEndId());
	        	newTransaction.setBIC(infoTransaction.getDbtrAgt().getFinInstnId().getBIC());
	        	newTransaction.setFileId(file);
	        	newTransaction.setCurrency(infoTransaction.getInstdAmt().getCcy());
	        	newTransaction.setDate(infoTransaction.getDrctDbtTx().getMndtRltdInf().getDtOfSgntr().toGregorianCalendar().getTime());
	        	newTransaction.setSequence(infoTransaction.getPmtTpInf().getSeqTp().value());
	        	service.createTransaction(newTransaction);
	        	
	        	checkTransaction(newTransaction, file);
	        }
	      }
      }
      return document.getCstmrDrctDbtInitn();
    } catch (JAXBException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return RepeatStatus.FINISHED;
  }
  
  private boolean checkIntegrationFile(GroupHeader39 header){
	  
	  //IntegrationFiles existFile = service.findIntegrationFileByMsgId(header.getMsgId().toString(), header.getInitgPty().getNm());
	  String existFile = null;
	  if (existFile == null)
		  return true;
	  System.out.println("The files already exist, Abord Mission");
	  return false;
  }
  
  private boolean checkSum(GroupHeader39 header, Iterator<PaymentInstructionInformation4> iterator){
	  int nb_trans = Integer.valueOf(header.getNbOfTxs());
	  int amount = header.getCtrlSum().intValue();
	  
	  int nb_trans_eff = 0;
	  int amount_eff = 0;
	  
	  while(iterator.hasNext()){
		  PaymentInstructionInformation4 transaction = iterator.next();
		  for (DirectDebitTransactionInformation9 t : transaction.getDrctDbtTxInf()){
			  nb_trans_eff ++;
			  amount_eff += t.getInstdAmt().getValue().intValue();
		  }
	  }
	  
	  if (nb_trans != nb_trans_eff || amount != amount_eff){
		  System.out.println("Bad Checksum");
		  return false;
	  }
	  
	  return true;
  }
  
  private void checkTransaction(Transaction t, IntegrationFiles file){
	  
	  boolean exist = false;
	  GregorianCalendar cal = new GregorianCalendar();
	  Date now = cal.getTime();
	  
	  
	  OperationReport op = new OperationReport();
	  
	  op.setFileId(file);
	  op.setTransactionId(t);
	  
	  // Bank did'nt exist RJC000
	  for (Banks existBank : Banks.values()) {
		  if (t.getBIC().substring(0, 4).equals(existBank.toString())){
			  exist = true;
			  break;
		  }
	  }
	  
	  if (!exist){
		  System.out.println("The Bank didn't exist");
		  op.setCodeErreur(CodeErreurEnum.RJC000);
	  }
	  
	  // value < 1E
	  if (t.getAmount() < 1){
		  System.out.println("Value < 1E");
		  op.setCodeErreur(CodeErreurEnum.RJC001);
	  }
	  
	  //value > 10000 E
	  if (t.getAmount() > 10000){
		  System.out.println("Value > 10000E");
		  op.setCodeErreur(CodeErreurEnum.RJC002);
	  }
	  
	  // Currency != E
	  if (!t.getCurrency().equals("EUR")){
		  System.out.println("Currency != E");
		  op.setCodeErreur(CodeErreurEnum.RJC003);
	  }
	  // Transaction in the pass
	  if (now.after(t.getDate())){
		  System.out.println("Transaction in the past");
		  op.setCodeErreur(CodeErreurEnum.RJC004);
	  }
	  
	  // Transaction date > 13 month
	  long year = now.getYear() - t.getDate().getYear();
	  if (year > 0){
		  long month = (year * 12 - t.getDate().getMonth()) + now.getMonth();
		  if (month > 13){
			  System.out.println("outdates 13 mounth");
			  op.setCodeErreur(CodeErreurEnum.RJC005);
		  }
	  }
	  
	  long day = ((t.getDate().getTime() - now.getTime()) / (24 * 60 * 60 * 1000));
	  // RCUR & date < 2d
	  if (t.getSequence().equals("RCUR") && day < 2){
		  System.out.println("RCUR and day < 2");
		  op.setCodeErreur(CodeErreurEnum.RJC006);
	  }
	  
	  if (t.getSequence().equals("FRST") && day < 5){
		  op.setCodeErreur(CodeErreurEnum.RJC007);
	  }
	  
	  if (op.getCodeErreur() == null)
		  op.setCodeErreur(CodeErreurEnum.CORRECT);
	  
	  service.createOperationReport(op);
  }

}
