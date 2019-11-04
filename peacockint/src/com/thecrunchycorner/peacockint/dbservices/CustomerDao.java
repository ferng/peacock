package com.thecrunchycorner.peacockint.dbservices;

import com.thecrunchycorner.peacockint.updatecustdbmodel.CustomerModel;

//TODO  Connection's setAutoCommit()
public interface CustomerDao
{
  public CustomerModel findCustomerById(String custId);
  
  public void insertCustomer(CustomerModel cust);
}
