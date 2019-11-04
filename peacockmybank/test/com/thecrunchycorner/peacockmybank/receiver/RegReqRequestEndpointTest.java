package com.thecrunchycorner.peacockmybank.receiver;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.thecrunchycorner.peacockmybank.regvalwsmodel.*;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import org.springframework.ws.soap.SoapHeader;

@RunWith(MockitoJUnitRunner.class)
public class RegReqRequestEndpointTest
{
  private RegReqRequestEndpoint reqEndpoint = new RegReqRequestEndpoint();
  private CustRegValidationRequest request= new CustRegValidationRequest();
  private CustRegValidationResponse response= new CustRegValidationResponse();
  private SoapHeader soapHeader= mock(SoapHeader.class);
  
  @Test
  public final void testHandleRegReqValRequest() throws Exception
  {
    XMLGregorianCalendar msgGregDate;
    GregorianCalendar now = new GregorianCalendar();
    msgGregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    msgGregDate.setYear(now.get(Calendar.YEAR));
    msgGregDate.setMonth(now.get(Calendar.MONTH) + 1);
    msgGregDate.setDay(now.get(Calendar.DAY_OF_MONTH));
    msgGregDate.setHour(now.get(Calendar.HOUR_OF_DAY));
    msgGregDate.setMinute(now.get(Calendar.MINUTE));
    msgGregDate.setSecond(now.get(Calendar.SECOND));
    
    CustomerType cust = new CustomerType();
    cust.setCustomerId("BN001");
    cust.setCustomerOrg("MYBNK");
    cust.setCustomerStatus(CustomerStatusType.UNAUTH);

    request.setRequestID(UUID.randomUUID().toString());
    request.setRequestAction(RequestActionType.VALIDATION);
    request.setRequestDateTime(msgGregDate);
    request.setNewCustDetails(cust);
    
    response = reqEndpoint.handleRegReqValRequest(request, soapHeader);
    
    assertEquals(request.getRequestID(), response.getOriginalRequestID());
    assertNotEquals(request.getRequestID(), response.getResponseID());
    assertEquals(response.getRequestResponse(), RequestResponseType.RECEIVED);
  }

}
