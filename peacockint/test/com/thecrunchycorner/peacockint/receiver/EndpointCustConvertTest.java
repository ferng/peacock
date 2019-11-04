package com.thecrunchycorner.peacockint.receiver;

import com.thecrunchycorner.peacockint.updatecustdbmodel.*;
import com.thecrunchycorner.peacockint.updatecustwsmodel.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;



public class EndpointCustConvertTest
{
  CustomerType wsCust = new CustomerType();
  GregorianCalendar now = new GregorianCalendar();
  XMLGregorianCalendar msgGregDate;
  EndpointCustConvert converter = new EndpointCustConvert();
  CustomerModel dbCust = new CustomerModel();
  
  @Before
  public void prepDateShit() throws Exception
  {
    msgGregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    msgGregDate.setYear(now.get(Calendar.YEAR));
    msgGregDate.setMonth(now.get(Calendar.MONTH) + 1);
    msgGregDate.setDay(now.get(Calendar.DAY_OF_MONTH));
    msgGregDate.setHour(now.get(Calendar.HOUR_OF_DAY));
    msgGregDate.setMinute(now.get(Calendar.MINUTE));
    msgGregDate.setSecond(now.get(Calendar.SECOND));
  }
  
  @Test
  public final void testWsToDb()
  {
    
    wsCust.setCustomerId("FG003");
    wsCust.setCustomerOrg("CRNCHY");
    
    wsCust.setCustomerStatus(CustomerStatusType.ACTIVE);
    wsCust.setCustomerFname("Fern");
    wsCust.setCustomerSname("Gonzalez");
    wsCust.setCustomerDOB(msgGregDate);
    wsCust.setCustomerStartDate(msgGregDate);
    wsCust.setCustomerEndDate(msgGregDate);

    dbCust = converter.wsToDb(wsCust);
    
    assertEquals("FG003", dbCust.getCustomerOrgCustId());
    assertEquals("CRNCHY", dbCust.getCustomerOrg());
    assertEquals(CustomerModelStatusType.ACTIVE, dbCust.getCustomerStatus());
    assertEquals("Fern", dbCust.getCustomerFname());
    assertEquals("Gonzalez", dbCust.getCustomerSname());
    assertEquals(msgGregDate.toGregorianCalendar(), dbCust.getCustomerDOB());
    assertEquals(msgGregDate.toGregorianCalendar(), dbCust.getCustomerStartDate());
    assertEquals(msgGregDate.toGregorianCalendar(), dbCust.getCustomerEndDate());
  }

}
