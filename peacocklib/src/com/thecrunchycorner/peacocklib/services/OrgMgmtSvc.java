package com.thecrunchycorner.peacocklib.services;

import com.thecrunchycorner.peacocklib.models.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgMgmtSvc
{

	private OrgDaoSvc orgSvc;

	
	public boolean supports(Class<?> clazz) {
		return OrgModel.class.isAssignableFrom(clazz);
	}

	
	public void add(OrgModel org)	{
		orgSvc.addOrg(org);
	}

	
	public void update(OrgModel org) {
		orgSvc.updateOrg(org);
	}
	
	
	public List<OrgModel> getAllOrgs() {
		List<OrgModel> orgList;
		orgList = orgSvc.getAllOrgs();

		return orgList;
	}

  @Autowired
  public void setOrgSvc(OrgDaoSvc orgSvc)
  {
    this.orgSvc = orgSvc;
  }
	
}
