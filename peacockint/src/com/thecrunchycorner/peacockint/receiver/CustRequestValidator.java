package com.thecrunchycorner.peacockint.receiver;

import com.thecrunchycorner.peacockint.dbservices.*;
import com.thecrunchycorner.peacockint.updatecustdbmodel.*;
import com.thecrunchycorner.peacockint.updatecustwsmodel.*;
import com.thecrunchycorner.peacocklib.models.*;
import com.thecrunchycorner.peacocklib.services.*;

import java.util.*;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Autowired;

public class CustRequestValidator 
{

  @Autowired
  private OrgDaoSvc orgSvc;
  @Autowired
  private CustomerDao custSvc;
  @Autowired
  private MsgLogDao msgLogSvc;

  static Logger logger = Logger.getLogger(CustRequestValidator.class.getName());

  private static final boolean PASS = true;
  private static final boolean FAIL = false;

  
  
  
  CustRequestValidator() {
    this.orgSvc = new OrgDaoSvc();
    this.custSvc = new CustomerDaoImpl();
    this.msgLogSvc = new MsgLogDaoImpl();
  }
  
  
  
  public CustRequestValidator(OrgDao orgDao, CustomerDao custDao, MsgLogDao msgLogDao) {
    this.orgSvc = (OrgDaoSvc) orgDao;
    this.custSvc = (CustomerDaoImpl) custDao;
    this.msgLogSvc = (MsgLogDaoImpl) msgLogDao;
  }
  
  
  
  public ArrayList<String> validate(UpdateCustomerRequest custReq) throws Exception
  {
    BasicConfigurator.configure();
    ArrayList<String> errors = new ArrayList<String>();
    
    if (requestDateTimeRecentValid(custReq.getRequestDateTime()) == FAIL) {
      errors.add("message: lifetime expired");
    }

    if (msgUniqueValid(custReq.getRequestID()) == FAIL) {
      errors.add("message: duplicate");
    }

    if (custReq.getRequestAction().equals(RequestActionType.REGISTER)) {
      if (registerUnAuthValid(custReq.getNewCustDetails()) == FAIL) {
        errors.add("customer: invalid authorisation");
      }

      if (registerNewValid(custReq.getNewCustDetails()) == FAIL) {
        errors.add("customer: already registered");
      }

    }

    logger.debug("Validated " + custReq.getRequestID() + " with these errors " + errors.toString());
    return errors;
  }

  
    
  private boolean requestDateTimeRecentValid(XMLGregorianCalendar reqDateTime) throws Exception
  {
    GregorianCalendar validDate = new GregorianCalendar();
    GregorianCalendar messageDate;
    long dateRange;
    long clientConfRegMsgLife;
    
    messageDate = reqDateTime.toGregorianCalendar(java.util.TimeZone.getDefault(),Locale.getDefault(),null);
    validDate.set(validDate.get(Calendar.YEAR), validDate.get(Calendar.MONTH), validDate.get((Calendar.DAY_OF_MONTH)), validDate.get(Calendar.HOUR_OF_DAY), validDate.get(Calendar.MINUTE), validDate.get(Calendar.SECOND));
    dateRange = validDate.getTimeInMillis() - messageDate.getTimeInMillis();

    //TODO clientConfRegMsgLife from DB for this client's profile
    clientConfRegMsgLife = 3600000;
    if (dateRange < clientConfRegMsgLife)
      return PASS;
    else
      return FAIL;
  }
  
  

  private boolean msgUniqueValid(String reqId) 
  {
    MsgLogModel existing = msgLogSvc.findLogById(reqId);

    if (existing.getMsgId().compareTo("") == 0) {
      return PASS;
    } else {
      return FAIL;
    }
    
  }

  
  
  private boolean registerUnAuthValid(CustomerType reqCust) throws Exception
  {
    OrgModel org;
    
    //so if customer is not UNAUTH, org is not AUTH, or org doesn't exist fail
    if (reqCust.getCustomerStatus().equals(CustomerStatusType.UNAUTH)) {
      org = orgSvc.findOrg(reqCust.getCustomerOrg());
      if (org.getOrgStatus().compareTo("AUTH") == 0) {
        return PASS;
      }
    }
    return FAIL;
  }

  
  
  private boolean registerNewValid(CustomerType reqCust) 
  {
    CustomerModel existing = custSvc.findCustomerById(reqCust.getCustomerId());

    if (existing.getCustomerOrgCustId().compareTo("") == 0) {
      return PASS;
    } else {
      return FAIL;
    }
  }
  
    

  public void setOrgSvc(OrgDaoSvc orgSvc)
  {
    this.orgSvc = orgSvc;
  }

  public void setCustSvc(CustomerDao custSvc)
  {
    this.custSvc = custSvc;
  }

  public void setMsgLogSvc(MsgLogDao msgLogSvc)
  {
    this.msgLogSvc = msgLogSvc;
  }

}

