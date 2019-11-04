package com.thecrunchycorner.peacocklib.models;

public class BinRangeModel
{
  private String binRange;
  private String binOrg;
  
  public BinRangeModel()
  {
  	binRange = "";
  	binOrg = "";
  }
  
  public String getBinRange()
  {
    return binRange;
  }

  public void setBinRange(String binRange)
  {
    this.binRange = binRange;
  }
  
  public String getBinOrg()
  {
    return binOrg;
  }
  
  public void setBinOrg(String binOrg)
  {
    this.binOrg = binOrg;
  }
  
}
