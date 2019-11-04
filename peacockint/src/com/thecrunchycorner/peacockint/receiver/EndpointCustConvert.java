package com.thecrunchycorner.peacockint.receiver;

import com.thecrunchycorner.peacockint.updatecustdbmodel.*;
import com.thecrunchycorner.peacockint.updatecustwsmodel.*;


public class EndpointCustConvert
{

  //TODO move this to RegReqJmsConverter: one converter per unit.  rename RegReqJmsConverter
  public EndpointCustConvert()
  {
  }

  public CustomerModel wsToDb(CustomerType wsCust){
    CustomerModel dbCust = new CustomerModel(
            wsCust.getCustomerId(),
            wsCust.getCustomerOrg(),
            CustomerModelStatusType.valueOf(wsCust.getCustomerStatus().toString()),
            wsCust.getCustomerFname(),
            wsCust.getCustomerSname(),
            wsCust.getCustomerDOB().toGregorianCalendar(),
            wsCust.getCustomerStartDate().toGregorianCalendar(),
            wsCust.getCustomerEndDate().toGregorianCalendar()
    );

    return dbCust;
  }
}
