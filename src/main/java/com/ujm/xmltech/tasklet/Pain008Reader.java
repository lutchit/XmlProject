package com.ujm.xmltech.tasklet;

import iso.std.iso._20022.tech.xsd.pain_008_001.Document;
import iso.std.iso._20022.tech.xsd.pain_008_001.GroupHeader39;
import iso.std.iso._20022.tech.xsd.pain_008_001.ObjectFactory;
import iso.std.iso._20022.tech.xsd.pain_008_001.PaymentInstructionInformation4;

import java.io.File;
import java.io.FileReader;
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

import com.ujm.xmltech.utils.BankSimulationConstants;

public class Pain008Reader implements Tasklet {

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
      System.out.println(header.getMsgId()); // test si msgId et idCreancier = une ligne dans la table identifiant
      Iterator<PaymentInstructionInformation4> it = document.getCstmrDrctDbtInitn().getPmtInf().iterator();
      while (it.hasNext()) {
    	// vérifier banque destination
        PaymentInstructionInformation4 collecTransaction = it.next();
        // boucler sur toutes les transactions (DrctDbtTxInf)
        // vérifier chaque transaction 
        // service.createTransaction();
        System.out.println(collecTransaction.getPmtInfId());
      }
      return document.getCstmrDrctDbtInitn();
    } catch (JAXBException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return RepeatStatus.FINISHED;
  }

}
