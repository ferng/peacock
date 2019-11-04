package com.thecrunchycorner.peacocklib.models;

public class OrgModel
{
	private String orgCode;
	private String orgName;
	private String orgStatus;

	public OrgModel() {
		this.orgCode = ("");
		this.orgName = ("");
		this.orgStatus = ("");
	}
	
	public String getOrgCode()
	{
		return orgCode;
	}

	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public String getOrgStatus()
	{
		return orgStatus;
	}

	public void setOrgStatus(String orgStatus)
	{
		this.orgStatus = orgStatus;
	}

}
