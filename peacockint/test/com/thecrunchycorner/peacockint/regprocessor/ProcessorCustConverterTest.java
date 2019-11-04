package com.thecrunchycorner.peacockint.regprocessor;

import com.thecrunchycorner.peacockint.regvalwsmodel.*;
import com.thecrunchycorner.peacockint.updatecustdbmodel.*;

import java.util.GregorianCalendar;

import org.junit.Test;
import static org.junit.Assert.*;



public class ProcessorCustConverterTest
{
  ProcessorCustConverter converter = new ProcessorCustConverter();
  CustomerType wsCust = new CustomerType();
  
  @Test
  public final void testDbToWs()
  {
    CustomerModel cust = new CustomerModel();
    cust.setCustomerId(1001);
    cust.setCustomerOrgCustId("FG008");
    cust.setCustomerOrg("CRNCHY");
    cust.setCustomerStatus(CustomerModelStatusType.ACTIVE);
    cust.setCustomerFname("Fern");
    cust.setCustomerSname("Gonzalez");
    cust.setCustomerDOB(new GregorianCalendar());
    cust.setCustomerStartDate(new GregorianCalendar());
    cust.setCustomerEndDate(new GregorianCalendar());
  
    wsCust = converter.dbToWs(cust);
    
    assertEquals("FG008", wsCust.getCustomerId());
    assertEquals("CRNCHY", wsCust.getCustomerOrg());
    assertEquals(CustomerStatusType.ACTIVE, wsCust.getCustomerStatus());
  }

}
