package com.thecrunchycorner.peacocklib.services;

import com.thecrunchycorner.peacocklib.models.*;

import java.util.*;

public interface OrgDao
{
	public OrgModel findOrg(String code);
	public List<OrgModel> getAllOrgs();
	public void addOrg(OrgModel org);
	public void updateOrg(OrgModel org);
}
