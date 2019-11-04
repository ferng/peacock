package com.thecrunchycorner.peacockint.regprocessor;

import com.thecrunchycorner.peacockint.receiver.*;
import com.thecrunchycorner.peacockint.testsupport.JmsStats;
import com.thecrunchycorner.peacockint.updatecustdbmodel.*;

import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.jms.JMSException;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration("file:WebContent/WEB-INF/peacockint-servlet.xml")
public class JmsCustRegListenerTest
{
  @Autowired
  private CustomerRegJmsImpl custJms;

  private JmsStats jmsStats = new JmsStats();
  private HashMap<String, Object> qDetails;

  private long msgsEnqueued = 0;
  private long msgsDequeued = 0;

 
  
  @Test
  public void readJmsMsgTest() throws JMSException
  {
    CustomerModel dbCust = new CustomerModel();
      
    dbCust.setCustomerId(1005);
    dbCust.setCustomerOrgCustId("FG007");
    dbCust.setCustomerOrg("CRNCHY");
    dbCust.setCustomerStatus(CustomerModelStatusType.UNAUTH);
    dbCust.setCustomerFname("Ati");
    dbCust.setCustomerSname("Radeon");
    dbCust.setCustomerDOB(new GregorianCalendar());
    dbCust.setCustomerStartDate(new GregorianCalendar());
    dbCust.setCustomerEndDate(new GregorianCalendar());
    
    qDetails = jmsStats.getQInfo("CUST.REGREQ");
    msgsEnqueued = (long) qDetails.get("enqueueCount");
    msgsDequeued = (long) qDetails.get("dequeueCount");
    
    custJms.postMsgToJms(dbCust);

    //not necessary but it allows all trheads to compete :-)
    try { Thread.sleep(500); } catch (Exception e) {}
    
    //one message was posted
    qDetails = jmsStats.getQInfo("CUST.REGREQ");
    assertEquals(msgsEnqueued + 1, qDetails.get("enqueueCount"));
    assertEquals(msgsDequeued + 1, qDetails.get("dequeueCount"));
    
  }

  
  
}
