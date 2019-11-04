package com.thecrunchycorner.peacockint.regprocessor;

import com.thecrunchycorner.peacockint.regvalwsmodel.*;
import com.thecrunchycorner.peacockint.updatecustdbmodel.*;



public class ProcessorCustConverter
{

  //TODO move this to RegReqJmsConverter: one converter per unit.  rename RegReqJmsConverter
  //TODO can converters be made static
  public ProcessorCustConverter()
  {
  }


//TODO is this doing anything?
  public CustomerModel wsToDb(CustomerType wsCust)
  {
    CustomerModel dbCust = new CustomerModel();
    return dbCust;
  }



  public CustomerType dbToWs(CustomerModel dbCust)
  {
    CustomerType wsCust = new CustomerType();
    
    wsCust.setCustomerId(dbCust.getCustomerOrgCustId());
    wsCust.setCustomerOrg(dbCust.getCustomerOrg());
    wsCust.setCustomerStatus(CustomerStatusType.valueOf(dbCust.getCustomerStatus().toString()));
    
    return wsCust;
  }

}
