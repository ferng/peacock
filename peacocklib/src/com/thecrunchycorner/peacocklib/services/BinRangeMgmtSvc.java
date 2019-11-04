package com.thecrunchycorner.peacocklib.services;

import com.thecrunchycorner.peacocklib.models.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BinRangeMgmtSvc 
{
  
	private BinDaoSvc binSvc;
	
	public BinRangeMgmtSvc() {
		this.binSvc = new BinDaoSvc();
	}
	
	
	public boolean supports(Class<?> clazz) {
		return AdminUserModel.class.isAssignableFrom(clazz);
	}
	
  public BinRangeModel findRange(String binRange) {
  	return binSvc.findBin(binRange);
  }
	
	
	public void add(BinRangeModel binRange) {
    binSvc.addBinRange(binRange);
  }

  
  public void update(BinRangeModel binRange) {
    binSvc.updateBinRange(binRange);
  }

  
  public List<BinRangeModel> getAllBinRanges(String orgId)	{
		List<BinRangeModel> binRangeList;
		binRangeList = binSvc.getAllBinRanges(orgId);

		return binRangeList;
	}

  
  @Autowired
  public void setBinSvc(BinDaoSvc binSvc)
  {
    this.binSvc = binSvc;
  }
	
}
