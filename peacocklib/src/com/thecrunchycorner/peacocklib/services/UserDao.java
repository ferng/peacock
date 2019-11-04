package com.thecrunchycorner.peacocklib.services;

import com.thecrunchycorner.peacocklib.models.*;

import java.util.*;

public interface UserDao
{
	public AdminUserModel findUser(AdminUserModel user);
	public List<AdminUserModel> getAllUsers(String orgId);
	public void addUser(AdminUserModel user);
	public void updateUser(AdminUserModel user);
}
