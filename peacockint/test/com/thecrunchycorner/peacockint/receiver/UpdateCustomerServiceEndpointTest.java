package com.thecrunchycorner.peacockint.receiver;

import com.thecrunchycorner.peacockint.testsupport.JmsStats;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.UUID;

import javax.xml.transform.Source;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import static org.springframework.ws.test.server.RequestCreators.*;
import static org.springframework.ws.test.server.ResponseMatchers.*;

import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.core.io.*;

import org.springframework.context.ApplicationContext;
import org.springframework.xml.transform.StringSource;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration("file:WebContent/WEB-INF/peacockint-servlet.xml")
public class UpdateCustomerServiceEndpointTest
{
  @Autowired
  private ApplicationContext appContext;

  @Autowired
  private CustomerRegJmsImpl custJms;
  //TODO: see if receive timeout can be added to JNDI rather than hard coded in xml which is not good.  Although i remember that problem in matm :-)

  private JmsStats jmsStats = new JmsStats();
  private HashMap<String, Object> qDetails;

  private long msgsEnqueued = 0;
  private long msgsDequeued = 0;
  
  private Resource schema = new ClassPathResource("CustomerReg.xsd");
  
  private MockWebServiceClient wsClient;

  
  
  @Before
  public void createClient() {
    wsClient = MockWebServiceClient.createClient(appContext);
  }
  
//TODO: autogenerate test data (too many tests might step over each other)

  
  @Test
  public void customerEndpointCleanDataTest() throws Exception {
    XMLGregorianCalendar msgGregDate;
    GregorianCalendar now = new GregorianCalendar();
   
    msgGregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    
    msgGregDate.setYear(now.get(Calendar.YEAR));
    msgGregDate.setMonth(now.get(Calendar.MONTH) + 1);
    msgGregDate.setDay(now.get(Calendar.DAY_OF_MONTH));
    msgGregDate.setHour(now.get(Calendar.HOUR_OF_DAY));
    msgGregDate.setMinute(now.get(Calendar.MINUTE));
    msgGregDate.setSecond(now.get(Calendar.SECOND));
  
    Source requestPayload = new StringSource(
      "<pck:UpdateCustomerRequest xmlns:pck='http://www.thecrunchycorner.com/peacockInt/schemas' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' " +
      "       xsi:schemaLocation='http://www.thecrunchycorner.com/peacockInt/schemas ../WebContent/WEB-INF/classes/CustomerReg.xsd '> " +
      "  <pck:RequestID>" + UUID.randomUUID().toString() + "</pck:RequestID>" +
      "  <pck:RequestDateTime>" + msgGregDate.toString() + "</pck:RequestDateTime>" +
      "  <pck:RequestAction>REGISTER</pck:RequestAction>" +
      "  <pck:NewCustDetails>" +
      "    <pck:CustomerId>FG004</pck:CustomerId>" +
      "    <pck:CustomerOrg>CRNCHY</pck:CustomerOrg>" +
      "    <pck:CustomerStatus>UNAUTH</pck:CustomerStatus>" +
      "    <pck:CustomerFname>John</pck:CustomerFname>" +
      "    <pck:CustomerSname>Smith</pck:CustomerSname>" +
      "    <pck:CustomerDOB>1999-09-19</pck:CustomerDOB>" +
      "    <pck:CustomerStartDate>2013-08-15</pck:CustomerStartDate>" +
      "    <pck:CustomerEndDate>2014-08-15</pck:CustomerEndDate>" +
      "  </pck:NewCustDetails>" +
      "</pck:UpdateCustomerRequest>"
    );

    qDetails = jmsStats.getQInfo("CUST.REGREQ");
    msgsEnqueued = (long) qDetails.get("enqueueCount");
    msgsDequeued = (long) qDetails.get("dequeueCount");

    wsClient.sendRequest(withPayload(requestPayload)).andExpect(validPayload(schema));

    //one message was posted
    qDetails = jmsStats.getQInfo("CUST.REGREQ");
    assertEquals(msgsEnqueued + 1, qDetails.get("enqueueCount"));
    assertEquals(msgsDequeued + 1, qDetails.get("dequeueCount"));
  }

  
  
  @Test
  public void customerEndpointMessageErrorTest() throws Exception {
    
    XMLGregorianCalendar msgGregDate;
    GregorianCalendar now = new GregorianCalendar();
    msgGregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        
    msgGregDate.setYear(now.get(Calendar.YEAR));
    msgGregDate.setMonth(now.get(Calendar.MONTH) + 1);
    msgGregDate.setDay(now.get(Calendar.DAY_OF_MONTH));
    msgGregDate.setHour(now.get(Calendar.HOUR_OF_DAY));
    msgGregDate.setMinute(now.get(Calendar.MINUTE));
    msgGregDate.setSecond(now.get(Calendar.SECOND));
  
    Source requestPayload = new StringSource(
      "<pck:UpdateCustomerRequest xmlns:pck='http://www.thecrunchycorner.com/peacockInt/schemas' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' " +
      "       xsi:schemaLocation='http://www.thecrunchycorner.com/peacockInt/schemas ../WebContent/WEB-INF/classes/CustomerReg.xsd '> " +
      "  <pck:RequestID>" + UUID.randomUUID().toString() + "</pck:RequestID>" +
      "  <pck:RequestDateTime>" + msgGregDate.toString() + "</pck:RequestDateTime>" +
      "  <pck:RequestAction>REGISTER</pck:RequestAction>" +
      "  <pck:NewCustDetails>" +
      "    <pck:CustomerId>FG005</pck:CustomerId>" +
      "    <pck:CustomerOrg>CRNCHY</pck:CustomerOrg>" +
      "    <pck:CustomerStatus>UNAUTH</pck:CustomerStatus>" +
      "    <pck:CustomerFname>PeterJJ</pck:CustomerFname>" +
      "    <pck:CustomerSname>Jones</pck:CustomerSname>" +
      "    <pck:CustomerDOB>1988-08-18</pck:CustomerDOB>" +
      "    <pck:CustomerStartDate>2013-08-15</pck:CustomerStartDate>" +
      "    <pck:CustomerEndDate>2014-08-15</pck:CustomerEndDate>" +
      "  </pck:NewCustDetails>" +
      "</pck:UpdateCustomerRequest>"
    );
    
    qDetails = jmsStats.getQInfo("CUST.REGREQ");
    msgsEnqueued = (long) qDetails.get("enqueueCount");
    msgsDequeued = (long) qDetails.get("dequeueCount");
    
    wsClient.sendRequest(withPayload(requestPayload));

    //first message was posted
    qDetails = jmsStats.getQInfo("CUST.REGREQ");
    assertEquals(msgsEnqueued + 1, qDetails.get("enqueueCount"));
    assertEquals(msgsDequeued + 1, qDetails.get("dequeueCount"));

    wsClient.sendRequest(withPayload(requestPayload)).andExpect(serverOrReceiverFault("Message error"));

    //message with the same ID was rejected so altogether only one message got posted
    qDetails = jmsStats.getQInfo("CUST.REGREQ");
    assertEquals(msgsEnqueued + 1, qDetails.get("enqueueCount"));
    assertEquals(msgsDequeued + 1, qDetails.get("dequeueCount"));
  }

  
  
  @Test
  public void customerEndpointUpdateErrorTest() throws Exception {
    
    XMLGregorianCalendar msgGregDate;
    GregorianCalendar now = new GregorianCalendar();
    msgGregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        
    msgGregDate.setYear(now.get(Calendar.YEAR));
    msgGregDate.setMonth(now.get(Calendar.MONTH) + 1);
    msgGregDate.setDay(now.get(Calendar.DAY_OF_MONTH));
    msgGregDate.setHour(now.get(Calendar.HOUR_OF_DAY));
    msgGregDate.setMinute(now.get(Calendar.MINUTE));
    msgGregDate.setSecond(now.get(Calendar.SECOND));
  
    Source requestPayload = new StringSource(
      "<pck:UpdateCustomerRequest xmlns:pck='http://www.thecrunchycorner.com/peacockInt/schemas' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' " +
      "       xsi:schemaLocation='http://www.thecrunchycorner.com/peacockInt/schemas ../WebContent/WEB-INF/classes/CustomerReg.xsd '> " +
      "  <pck:RequestID>" + UUID.randomUUID().toString() + "</pck:RequestID>" +
      "  <pck:RequestDateTime>" + msgGregDate.toString() + "</pck:RequestDateTime>" +
      "  <pck:RequestAction>REGISTER</pck:RequestAction>" +
      "  <pck:NewCustDetails>" +
      "    <pck:CustomerId>FG006</pck:CustomerId>" +
      "    <pck:CustomerOrg>CRNCHY</pck:CustomerOrg>" +
      "    <pck:CustomerStatus>UNAUTH</pck:CustomerStatus>" +
      "    <pck:CustomerFname>ClairJohn</pck:CustomerFname>" +
      "    <pck:CustomerSname>Jones</pck:CustomerSname>" +
      "    <pck:CustomerDOB>1988-08-18</pck:CustomerDOB>" +
      "    <pck:CustomerStartDate>2013-08-15</pck:CustomerStartDate>" +
      "    <pck:CustomerEndDate>2014-08-15</pck:CustomerEndDate>" +
      "  </pck:NewCustDetails>" +
      "</pck:UpdateCustomerRequest>"
    );
    
    qDetails = jmsStats.getQInfo("CUST.REGREQ");
    msgsEnqueued = (long) qDetails.get("enqueueCount");
    msgsDequeued = (long) qDetails.get("dequeueCount");
    
    wsClient.sendRequest(withPayload(requestPayload));

    //first message was posted
    qDetails = jmsStats.getQInfo("CUST.REGREQ");
    assertEquals(msgsEnqueued + 1, qDetails.get("enqueueCount"));
    assertEquals(msgsDequeued + 1, qDetails.get("dequeueCount"));

    requestPayload = new StringSource(
      "<pck:UpdateCustomerRequest xmlns:pck='http://www.thecrunchycorner.com/peacockInt/schemas' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' " +
      "       xsi:schemaLocation='http://www.thecrunchycorner.com/peacockInt/schemas ../WebContent/WEB-INF/classes/CustomerReg.xsd '> " +
      "  <pck:RequestID>" + UUID.randomUUID().toString() + "</pck:RequestID>" +
      "  <pck:RequestDateTime>" + msgGregDate.toString() + "</pck:RequestDateTime>" +
      "  <pck:RequestAction>REGISTER</pck:RequestAction>" +
      "  <pck:NewCustDetails>" +
      "    <pck:CustomerId>FG006</pck:CustomerId>" +
      "    <pck:CustomerOrg>CRNCHY</pck:CustomerOrg>" +
      "    <pck:CustomerStatus>UNAUTH</pck:CustomerStatus>" +
      "    <pck:CustomerFname>ClairJohn</pck:CustomerFname>" +
      "    <pck:CustomerSname>Jones</pck:CustomerSname>" +
      "    <pck:CustomerDOB>1988-08-18</pck:CustomerDOB>" +
      "    <pck:CustomerStartDate>2013-08-15</pck:CustomerStartDate>" +
      "    <pck:CustomerEndDate>2014-08-15</pck:CustomerEndDate>" +
      "  </pck:NewCustDetails>" +
      "</pck:UpdateCustomerRequest>"
    );

    wsClient.sendRequest(withPayload(requestPayload)).andExpect(serverOrReceiverFault("Update error"));
    
    //message with different ID but same customer ID rejected so altogether only one message got posted
    qDetails = jmsStats.getQInfo("CUST.REGREQ");
    assertEquals(msgsEnqueued + 1, qDetails.get("enqueueCount"));
    assertEquals(msgsDequeued + 1, qDetails.get("dequeueCount"));
  }

}
