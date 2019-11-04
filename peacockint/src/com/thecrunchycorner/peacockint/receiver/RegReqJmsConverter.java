package com.thecrunchycorner.peacockint.receiver;

import com.thecrunchycorner.peacockint.updatecustdbmodel.*;

import java.util.GregorianCalendar;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;



public class RegReqJmsConverter implements MessageConverter
{
  @Override
  public Object fromMessage(Message message) throws JMSException, MessageConversionException
  {
    MapMessage mapMessage = (MapMessage) message;
    
    CustomerModel cust = new CustomerModel();
    GregorianCalendar cal = new GregorianCalendar();
    
    cust.setCustomerOrgCustId(mapMessage.getString("CUST_ORG_CUST_ID"));
    cust.setCustomerOrg(mapMessage.getString("CUST_ORG_ID"));
    cust.setCustomerStatus(CustomerModelStatusType.valueOf(mapMessage.getString("CUST_STATUS")));
    cust.setCustomerFname(mapMessage.getString("CUST_FNAME"));
    cust.setCustomerSname(mapMessage.getString("CUST_SNAME"));
    
    cal.setTimeInMillis(mapMessage.getLong("CUST_DOB"));
    cust.setCustomerDOB(cal);

    cal.setTimeInMillis(mapMessage.getLong("CUST_START_DATE"));
    cust.setCustomerStartDate(cal);

    cal.setTimeInMillis(mapMessage.getLong("CUST_END_DATE"));
    cust.setCustomerEndDate(cal);
    
    return cust;
  }

  
  
  @Override
  public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException
  {
    CustomerModel cust = (CustomerModel) object;
    MapMessage message = session.createMapMessage();

    message.setString("CUST_ORG_CUST_ID",cust.getCustomerOrgCustId());
    message.setString("CUST_ORG_ID", cust.getCustomerOrg());
    message.setString("CUST_STATUS", cust.getCustomerStatus().toString());
    message.setString("CUST_FNAME",cust.getCustomerFname());
    message.setString("CUST_SNAME",cust.getCustomerSname());
    message.setLong("CUST_DOB",cust.getCustomerDOB().getTimeInMillis());
    message.setLong("CUST_START_DATE",cust.getCustomerStartDate().getTimeInMillis());
    message.setLong("CUST_END_DATE",cust.getCustomerEndDate().getTimeInMillis());
    
    return message;
  }

}
