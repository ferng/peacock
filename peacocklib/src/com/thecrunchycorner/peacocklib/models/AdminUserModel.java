package com.thecrunchycorner.peacocklib.models;

public class AdminUserModel
{
  private String userId;
	private String userOrg;
	private String userPw;
	private String userFname;
	private String userSname;
	private String userStatus;

	public AdminUserModel()
	{
		userStatus = "UNKOWN";
		userOrg = "";
	}
	
  public String getUserOrg()
  {
    return userOrg;
  }

  public void setUserOrg(String userOrg)
  {
    this.userOrg = userOrg;
  }

  public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getUserFname()
	{
		return userFname;
	}
	
	public void setUserFname(String userFname)
	{
		this.userFname = userFname;
	}

	public String getUserSname()
	{
		return userSname;
	}
	
	public void setUserSname(String userSname)
	{
		this.userSname = userSname;
	}

	public String getUserPw()
	{
		return userPw;
	}

	public void setUserPw(String userPw)
	{
		this.userPw = userPw;
	}
		
	public String getUserStatus()
	{
		return userStatus;
	}
	
	public void setUserStatus(String userStatus)
	{
		this.userStatus = userStatus;
	}
	
}
