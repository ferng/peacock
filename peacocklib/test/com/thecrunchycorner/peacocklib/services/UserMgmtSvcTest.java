package com.thecrunchycorner.peacocklib.services;

import com.thecrunchycorner.peacocklib.models.*;

import java.util.ArrayList;
import java.util.List;

import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

//TODO add tests for find once all code moves to using Mgmt instead of DAO
@RunWith(MockitoJUnitRunner.class)
public class UserMgmtSvcTest
{
  private UserDaoSvc userDao = mock(UserDaoSvc.class);
  private UserMgmtSvc mgmtSvc = new UserMgmtSvc();
    
  @Before
  public void prepSvcs()
  {
    mgmtSvc.setUserSvc(userDao);
  }

  
  @Test
  public void testGetAllUsers()
  {
    AdminUserModel user = new AdminUserModel();
    
    List<AdminUserModel> userList = new ArrayList<AdminUserModel>();
    List<AdminUserModel> retUserList;
    
    user.setUserOrg("CRNCHY");
    user.setUserId("HHH1");
    user.setUserPw("password");
    user.setUserFname("isaac");
    user.setUserSname("asimov");
    user.setUserStatus("CLIENTADM");
    
    userList.add(user);

    user.setUserOrg("CRNCHY");
    user.setUserId("HHH2");
    user.setUserPw("password");
    user.setUserFname("pg");
    user.setUserSname("woodhouse");
    user.setUserStatus("CLIENTADM");
    
    userList.add(user);
    
    when(userDao.getAllUsers("CRNCHY")).thenReturn(userList);
    
    retUserList = mgmtSvc.getAllUsers("CRNCHY");
    assertEquals(retUserList.size(), 2);

    retUserList = mgmtSvc.getAllUsers("NOT");
    assertNotEquals(retUserList.size(), 2);
  
  }
  
}
