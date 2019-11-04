package com.thecrunchycorner.peacocklib.services;

import com.thecrunchycorner.peacocklib.models.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserMgmtSvc 
{
  
	private UserDaoSvc userSvc;

	
	public boolean supports(Class<?> clazz) {
		return AdminUserModel.class.isAssignableFrom(clazz);
	}
	
  
  public void add(AdminUserModel user) {
    userSvc.addUser(user);
  }

  
  public void update(AdminUserModel user) {
    userSvc.updateUser(user);
  }

  
  public List<AdminUserModel> getAllUsers(String orgId)	{
		List<AdminUserModel> userList;
		userList = userSvc.getAllUsers(orgId);

		return userList;
	}


  @Autowired
  public void setUserSvc(UserDaoSvc userSvc)
  {
    this.userSvc = userSvc;
  }
	
}
