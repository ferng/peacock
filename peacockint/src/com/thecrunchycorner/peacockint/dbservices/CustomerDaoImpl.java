package com.thecrunchycorner.peacockint.dbservices;

import com.thecrunchycorner.peacockint.updatecustdbmodel.CustomerModel;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class CustomerDaoImpl implements CustomerDao
{
  private SessionFactory sessionFactory;
  
  public CustomerDaoImpl()
  {
  }
  
  
  
  @Override
  @Transactional
  public CustomerModel findCustomerById(String custId)
  {
    Query query = sessionFactory.getCurrentSession().createQuery("from CustomerModel where customerOrgCustId = :reqCustId");
    query.setParameter("reqCustId", custId);

    if (query.list().isEmpty()) {
      return new CustomerModel();
    } else {
      return (CustomerModel) query.list().get(0);
    }
  }

  
  
  @Override
  @Transactional
  public void insertCustomer(CustomerModel cust)
  {
    sessionFactory.getCurrentSession().saveOrUpdate(cust);
  }

  
  
  @Autowired
  public void setSessionFactory(SessionFactory sessionFactory)
  {
    this.sessionFactory = sessionFactory;
  }

}
