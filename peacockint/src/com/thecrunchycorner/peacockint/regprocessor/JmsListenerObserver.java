package com.thecrunchycorner.peacockint.regprocessor;

import com.thecrunchycorner.peacockint.updatecustdbmodel.CustomerModel;

public interface JmsListenerObserver
{
  public void update(CustomerModel dbCust);
}
