package com.thecrunchycorner.mrpeacock.admin;

import com.thecrunchycorner.peacocklib.models.*;
import com.thecrunchycorner.peacocklib.services.*;

import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.springframework.validation.*;


@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest
{
	private UserDaoSvc userDao = mock(UserDaoSvc.class);
	private OrgDaoSvc orgDao = mock(OrgDaoSvc.class);
	private Errors errors;
	private UserValidator validator = new UserValidator();
	private AdminUserModel user = new AdminUserModel();
	private OrgModel org = new OrgModel();
	private AdminUserModel returnUser = new AdminUserModel();
	
	@Before
	public void prepResults()
	{
	  errors = new BeanPropertyBindingResult(user, "user");
	  validator.setUserSvc(userDao);
	  validator.setOrgSvc(orgDao);
	}

	
	
	@Test
	public void testAddExistingUser()
	{
    org.setOrgCode("CRNCHY");
    org.setOrgName("THE CRUNCHY CORNER");
    org.setOrgStatus("AUTH");
    
    user.setUserId("HHH1");
    user.setUserOrg("CRNCHY");
    user.setUserPw("password");
    user.setUserFname("isaac");
    user.setUserSname("asimov");
    user.setUserStatus("UNKNOWN");
	

    when(orgDao.findOrg("CRNCHY")).thenReturn(org);
    when(userDao.findUser(user)).thenReturn(user);
		
		validator.validate(user, errors);

		assertTrue(errors.hasErrors());
	}
	
	
	
	@Test
	public void testAddNewAuthOrgUser()
	{
    org.setOrgCode("CRNCHY");
    org.setOrgName("THE CRUNCHY CORNER");
    org.setOrgStatus("AUTH");
    
    user.setUserId("HHH1");
    user.setUserOrg("CRNCHY");
    user.setUserPw("password");
    user.setUserFname("isaac");
    user.setUserSname("asimov");
    user.setUserStatus("UNKNOWN");
    
    returnUser.setUserId(null);
    returnUser.setUserOrg(null);
    returnUser.setUserPw(null);
    returnUser.setUserFname(null);
    returnUser.setUserSname(null);
    returnUser.setUserStatus("UNKNOWN");


    when(orgDao.findOrg("CRNCHY")).thenReturn(org);
    when(userDao.findUser(user)).thenReturn(returnUser);
		
		validator.validate(user, errors);
		
    assertFalse(errors.hasErrors());
	}

  
  
  @Test
  public void testAddNewUnauthOrgUser()
  {
    org.setOrgCode("UNAUTH");
    org.setOrgName("UNATHORIZED ORG");
    org.setOrgStatus("NEW");
    
    user.setUserId("HHH1");
    user.setUserOrg("CRNCHY");
    user.setUserPw("password");
    user.setUserFname("isaac");
    user.setUserSname("asimov");
    user.setUserStatus("UNKNOWN");
    
    returnUser.setUserId(null);
    returnUser.setUserOrg(null);
    returnUser.setUserPw(null);
    returnUser.setUserFname(null);
    returnUser.setUserSname(null);
    returnUser.setUserStatus("UNKNOWN");


    when(orgDao.findOrg("CRNCHY")).thenReturn(org);
    when(userDao.findUser(user)).thenReturn(returnUser);
    
    validator.validate(user, errors);
    
    assertTrue(errors.hasErrors());
  }
}
