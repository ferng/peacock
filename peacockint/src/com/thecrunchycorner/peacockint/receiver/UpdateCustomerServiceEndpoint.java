package com.thecrunchycorner.peacockint.receiver;

import com.thecrunchycorner.peacockint.dbservices.*;
import com.thecrunchycorner.peacockint.updatecustdbmodel.*;
import com.thecrunchycorner.peacockint.updatecustwsmodel.*;

import java.util.*;

import org.apache.log4j.NDC;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.*;
import org.springframework.ws.soap.SoapHeader;

import org.springframework.xml.validation.XmlValidationException;
//TODO: make sure the DB access is via the manager not the dao directly (in case there's future business logic changes

@Endpoint
public class UpdateCustomerServiceEndpoint  
{
  static Logger logger = Logger.getLogger(UpdateCustomerServiceEndpoint.class.getName());
  private static final String NAMESPACE_URI = "http://www.thecrunchycorner.com/peacockInt/schemas";

  @Autowired
  private CustRequestValidator custRequestValidator;
  
  @Autowired
  private MsgLogDao msgLogSvc;
  
  @Autowired
  private CustomerDao custSvc;
  
  @Autowired
  private CustomerRegJmsImpl custJms;
  
  //TODO: add enpoint interceptors to: log message and later on deal with soap security
  //TODO: add exception handler in app context (my own fault with detail http://www.stevideter.com/2009/02/18/of-exceptionresolvers-and-xmlbeans/)
  @ResponsePayload
  @PayloadRoot(namespace=NAMESPACE_URI, localPart="UpdateCustomerRequest")
  public UpdateCustomerResponse handleUpdateCustomer(@RequestPayload final UpdateCustomerRequest custRequest, SoapHeader header) throws Exception
  {
    NDC.push("INT" + custRequest.getNewCustDetails().getCustomerId());
    logger.info("Received request " + custRequest.getRequestID());
    UpdateCustomerResponse custResponse = new UpdateCustomerResponse();
    
    MsgLogModel msgLog = new MsgLogModel();
    msgLog.setMsgDateTime(new GregorianCalendar());
    msgLog.setMsgSrcOrg(custRequest.getNewCustDetails().getCustomerOrg());
    msgLog.setMsgDestOrg("CRNCHY");                                 //TODO: get this orgId from some singleton entity that reads system_attribute (and is always available) (using private constructor)
    msgLog.setMsgId(custRequest.getRequestID());

    ArrayList<String> errors;
    errors = custRequestValidator.validate(custRequest);

    if (errors.isEmpty()) {
      msgLog.setMsgStatus(MsgStatus.RECEIVED);

      CustomerModel dbCust;
      EndpointCustConvert custConv = new EndpointCustConvert();
      dbCust = custConv.wsToDb(custRequest.getNewCustDetails());

      //TODO these three (db: msgLog, db: customer, jms: customer) should be part of one transaction.  using xa transactions
      try {
        custSvc.insertCustomer(dbCust);
        msgLogSvc.insertMsgLog(msgLog);
        try {
          custJms.postMsgToJms(dbCust);
        } catch (Exception e) {
          throw new XmlValidationException("Unkown error");
        }
      } catch (Exception e) {
          throw new XmlValidationException("Unknown error");
      }

      //TODO build correct response

      CustomerType outCust = new CustomerType();
      
      outCust.setCustomerId(custRequest.getNewCustDetails().getCustomerId());
      outCust.setCustomerOrg(custRequest.getNewCustDetails().getCustomerOrg());
      outCust.setCustomerStatus(custRequest.getNewCustDetails().getCustomerStatus());
      outCust.setCustomerFname(custRequest.getNewCustDetails().getCustomerFname());
      outCust.setCustomerSname(custRequest.getNewCustDetails().getCustomerFname());
      outCust.setCustomerDOB(custRequest.getNewCustDetails().getCustomerDOB());
      outCust.setCustomerStartDate(custRequest.getNewCustDetails().getCustomerStartDate());
      outCust.setCustomerEndDate(custRequest.getNewCustDetails().getCustomerEndDate());
      custResponse.setRequestID(custRequest.getRequestID());
      custResponse.setResponseDateTime(custRequest.getRequestDateTime());
      custResponse.setRequestAction(custRequest.getRequestAction());
      custResponse.setNewCustDetails(outCust);
      custResponse.setPrevCustDetails(outCust);
      
      
    } else {
      for (String errMsg: errors) {
        if (errMsg.contains("message")) {
          throw new XmlValidationException("Message error");
        } else if (errMsg.contains("customer")) {
          msgLog.setMsgStatus(MsgStatus.REJECTED);
          msgLogSvc.insertMsgLog(msgLog);
          throw new XmlValidationException("Update error");
        }
      }
    }
 
    //TODO: DAOimpl must do updates
    msgLog.setMsgStatus(MsgStatus.PROCESSED);
    msgLogSvc.insertMsgLog(msgLog);

    return custResponse;      
  }
  
}
