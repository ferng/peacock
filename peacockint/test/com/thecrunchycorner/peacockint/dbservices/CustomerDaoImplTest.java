package com.thecrunchycorner.peacockint.dbservices;

import com.thecrunchycorner.peacockint.updatecustdbmodel.*;

import java.util.*;

import org.hibernate.*;

import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.*;

import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CustomerDaoImplTest
{
  CustomerDaoImpl daoImpl = new CustomerDaoImpl();
  
  private SessionFactory factory = mock(SessionFactory.class);
  private Session session = mock(Session.class);

  private Random rnd = new Random();
  
  private Query query = mock(Query.class);
  
  @Test
  public final void findCustomerByIdFoundTest()
  {
    long custId = rnd.nextLong();
    daoImpl.setSessionFactory(factory);
    
    when(factory.getCurrentSession()).thenReturn(session);
    when(session.createQuery("from CustomerModel where customerOrgCustId = :reqCustId")).thenReturn(query);
    
    CustomerModel cust = new CustomerModel();
    cust.setCustomerId(custId);
    cust.setCustomerOrgCustId("FG001");
    cust.setCustomerOrg("CRNCHY");
    cust.setCustomerDOB(new GregorianCalendar());
    cust.setCustomerStartDate(new GregorianCalendar());
    cust.setCustomerEndDate(new GregorianCalendar());
    
    ArrayList<CustomerModel> queryReplyList = new ArrayList<CustomerModel>();
    queryReplyList.add(cust);
    
    when(query.list()).thenReturn(queryReplyList);
    
    assertEquals(custId, cust.getCustomerId());
  }
  
  
  
  @Test
  public final void findCustomerByIdNotFoundTest()
  {
    daoImpl.setSessionFactory(factory);
    
    when(factory.getCurrentSession()).thenReturn(session);
    when(session.createQuery("from CustomerModel where customerOrgCustId = :reqCustId")).thenReturn(query);
    
    CustomerModel cust = new CustomerModel();
    ArrayList<CustomerModel> queryReplyList = new ArrayList<CustomerModel>();
    queryReplyList.add(cust);
    
    when(query.list()).thenReturn(queryReplyList);
    
    assertEquals(cust.getCustomerOrgCustId(), "");
  }

  
}
