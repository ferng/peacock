package com.thecrunchycorner.peacockint.receiver;

import com.thecrunchycorner.peacockint.updatecustdbmodel.CustomerModel;

public interface CustomerRegJms
{
  public void postMsgToJms(CustomerModel customer);
}
