package com.thecrunchycorner.peacockint.updatecustdbmodel;

public enum CustomerModelStatusType {

  UNAUTH,
  ACTIVE,
  SUSPENDED,
  CLOSED;
  
  
  public String value()
  {
    return name();
  }
  
  public static CustomerModelStatusType formValue(String v)
  {
    return valueOf(v);
  }
  
  
}
