package com.thecrunchycorner.peacockint.regprocessor;

import com.thecrunchycorner.peacockint.updatecustdbmodel.CustomerModel;

import java.util.*;
import java.util.concurrent.Callable;

import org.apache.log4j.NDC;
import org.apache.log4j.Logger;


public class RegReqValSender //implements Callable<String>
{
  static Logger logger = Logger.getLogger(RegReqValSender.class.getName());
  private CustomerModel dbCust;

  private RegReqValProxy senderProxy;
  
  public void setSenderProxy(RegReqValProxy senderProxy)
  {
    this.senderProxy = senderProxy;
  }
  
  public RegReqValSender()
  {
  }

  public void setCust(CustomerModel dbCust)
  {
    this.dbCust = dbCust; 
  }
  
  public String call() throws Exception
  {
    NDC.push("INT - " + dbCust.getCustomerOrgCustId());
    String msgId = UUID.randomUUID().toString();
    String URI = "https://localhost:8444/peacockSimi/";
    
    senderProxy.sendRegValidationReq(dbCust, msgId, URI);
    
    System.out.println("------------------>");
    return "------------------>made it to this bit of the thread:" + dbCust.getCustomerFname();

  }

}
