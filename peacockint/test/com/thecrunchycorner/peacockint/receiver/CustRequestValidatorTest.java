package com.thecrunchycorner.peacockint.receiver;

import com.thecrunchycorner.peacockint.dbservices.*;
import com.thecrunchycorner.peacockint.updatecustdbmodel.*;
import com.thecrunchycorner.peacockint.updatecustwsmodel.*;
import com.thecrunchycorner.peacocklib.models.*;
import com.thecrunchycorner.peacocklib.services.*;

import java.util.*;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class CustRequestValidatorTest
{
  OrgDaoSvc orgDao = mock(OrgDaoSvc.class);
  CustomerDaoImpl custDao =  mock(CustomerDaoImpl.class);
  MsgLogDaoImpl msgDao = mock(MsgLogDaoImpl.class);
  
  OrgModel testOrg = new OrgModel();
  CustomerModel dbCust = new CustomerModel();
  MsgLogModel msgLog = new MsgLogModel();
  CustomerType customer = new CustomerType();
  UpdateCustomerRequest custReq = new UpdateCustomerRequest();

  CustRequestValidator validator = new CustRequestValidator();

  
  ArrayList<String> errors = new ArrayList<String>();

  GregorianCalendar now = new GregorianCalendar();
  XMLGregorianCalendar msgGregDate;
  XMLGregorianCalendar msgBadGregDate;
  XMLGregorianCalendar dobGregDate;
  XMLGregorianCalendar startGregDate;
  XMLGregorianCalendar endGregDate;
  
  
  
  @Before
  public void prepDateShit() throws Exception
  {
    validator.setOrgSvc(orgDao);
    validator.setCustSvc(custDao);
    validator.setMsgLogSvc(msgDao);
    
    msgGregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    msgBadGregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    dobGregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    startGregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    endGregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();

    msgGregDate.setYear(now.get(Calendar.YEAR));
    msgGregDate.setMonth(now.get(Calendar.MONTH) + 1);
    msgGregDate.setDay(now.get(Calendar.DAY_OF_MONTH));
    msgGregDate.setHour(now.get(Calendar.HOUR_OF_DAY));
    msgGregDate.setMinute(now.get(Calendar.MINUTE));
    msgGregDate.setSecond(now.get(Calendar.SECOND));
    
    custReq.setRequestAction(RequestActionType.REGISTER);
    custReq.setRequestDateTime(msgGregDate);
    custReq.setRequestID("e6a9bb54-da25-102b-9a03-2db401e887ec");
    
    customer.setCustomerId("FG002");
    customer.setCustomerOrg("ORG001");
    customer.setCustomerStatus(CustomerStatusType.UNAUTH);
    customer.setCustomerFname("Pete");
    customer.setCustomerSname("Smith");

    dobGregDate = (XMLGregorianCalendar) msgGregDate.clone();
    dobGregDate.setYear(now.get(Calendar.YEAR) - 20);
    customer.setCustomerDOB(dobGregDate);

    startGregDate = (XMLGregorianCalendar) msgGregDate.clone();
    customer.setCustomerStartDate(startGregDate);
    endGregDate = (XMLGregorianCalendar) msgGregDate.clone();
    endGregDate.setYear(now.get(Calendar.YEAR) +3 );
    customer.setCustomerEndDate(endGregDate);

    testOrg.setOrgCode("ORG001");
    testOrg.setOrgName("Test Organization");
    testOrg.setOrgStatus("AUTH");
  }
  
  
  
  @Test
  public void validRequestTest() throws Exception
  {
    custReq.setNewCustDetails(customer);

    when(orgDao.findOrg("ORG001")).thenReturn(testOrg);
    when(custDao.findCustomerById("FG002")).thenReturn(dbCust);
    when(msgDao.findLogById("e6a9bb54-da25-102b-9a03-2db401e887ec")).thenReturn(msgLog);
    
    errors = validator.validate(custReq);

    assertTrue(errors.isEmpty());
  }
  
  
  
  @Test
  public void failMsgAgeTest() throws Exception
  {
    msgBadGregDate = (XMLGregorianCalendar) msgGregDate.clone();
    msgBadGregDate.setHour(now.get(Calendar.HOUR_OF_DAY) - 2);
    custReq.setRequestDateTime(msgBadGregDate);

    custReq.setNewCustDetails(customer);

    when(orgDao.findOrg("ORG001")).thenReturn(testOrg);
    when(custDao.findCustomerById("FG002")).thenReturn(dbCust);
    when(msgDao.findLogById("e6a9bb54-da25-102b-9a03-2db401e887ec")).thenReturn(msgLog);
    
    errors = validator.validate(custReq);

    assertTrue(errors.contains("message: lifetime expired")) ;
  }
  
  
  
  @Test
  public void failUniqueMsgTest() throws Exception
  {
    custReq.setNewCustDetails(customer);

    msgLog.setMsgId("e6a9bb54-da25-102b-9a03-2db401e887ec");
    msgLog.setMsgStatus(MsgStatus.RECEIVED);
    msgLog.setMsgSrcOrg("ORG001");
    msgLog.setMsgDestOrg("CRNCHY");
    msgLog.setMsgError("");
    
    when(orgDao.findOrg("ORG001")).thenReturn(testOrg);
    when(custDao.findCustomerById("FG002")).thenReturn(dbCust);
    when(msgDao.findLogById("e6a9bb54-da25-102b-9a03-2db401e887ec")).thenReturn(msgLog);

    errors = validator.validate(custReq);

    assertTrue(errors.contains("message: duplicate")) ;
  }
  
  
  
  @Test
  public void failCustAuthTest() throws Exception
  {
    customer.setCustomerStatus(CustomerStatusType.ACTIVE);

    custReq.setNewCustDetails(customer);

    when(orgDao.findOrg("ORG001")).thenReturn(testOrg);
    when(custDao.findCustomerById("FG002")).thenReturn(dbCust);
    when(msgDao.findLogById("e6a9bb54-da25-102b-9a03-2db401e887ec")).thenReturn(msgLog);
    
    errors = validator.validate(custReq);

    assertTrue(errors.contains("customer: invalid authorisation")) ;
  }
  
  
  
  @Test
  public void failOrgAuthTest() throws Exception
  {
    custReq.setNewCustDetails(customer);

    testOrg.setOrgStatus("NEW");

    when(orgDao.findOrg("ORG001")).thenReturn(testOrg);
    when(custDao.findCustomerById("FG002")).thenReturn(dbCust);
    when(msgDao.findLogById("e6a9bb54-da25-102b-9a03-2db401e887ec")).thenReturn(msgLog);
    
    errors = validator.validate(custReq);

    assertTrue(errors.contains("customer: invalid authorisation")) ;
  }
  
  
  
  @Test
  public void failCustIsRegTest() throws Exception
  {
    custReq.setNewCustDetails(customer);

    dbCust.setCustomerOrgCustId("FG002");

    when(orgDao.findOrg("ORG001")).thenReturn(testOrg);
    when(custDao.findCustomerById("FG002")).thenReturn(dbCust);
    when(msgDao.findLogById("e6a9bb54-da25-102b-9a03-2db401e887ec")).thenReturn(msgLog);
    
    errors = validator.validate(custReq);

    assertTrue(errors.contains("customer: already registered")) ;
  }

  
}
