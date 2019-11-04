package com.thecrunchycorner.mrpeacock.admin;

import com.thecrunchycorner.peacocklib.models.*;
import com.thecrunchycorner.peacocklib.services.*;

import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.springframework.validation.AbstractErrors;


@RunWith(MockitoJUnitRunner.class)
public class AdminLogonValidatorTest
{
  private UserDaoSvc userDao = mock(UserDaoSvc.class);
  private AbstractErrors errors = mock(AbstractErrors.class);
	private AdminLogonValidator validator = new AdminLogonValidator();
	private AdminUserModel user = new AdminUserModel();
	private AdminUserModel returnUser = new AdminUserModel();
	
	
	@Before
	public void prepEnv()
	{
	  validator.setUserSvc(userDao);
	}
	
	
	
	@Test
	public void logonSubmitFormItsNotAdminTest()
	{
		user.setUserId(null);
		user.setUserPw(null);
		returnUser.setUserId(null);
		returnUser.setUserPw(null);
		
		when(userDao.findUser(user)).thenReturn(returnUser);

		validator.validate(user, errors);		
		
		assertEquals(user.getUserStatus(),"UNKNOWN");
	}

	
	
	@Test
	public void logonSubmitFormItsAdminTest()
	{
		user.setUserId("admin");
		user.setUserPw("password");
		
		returnUser.setUserId("admin");
		returnUser.setUserPw("password");
		
		when(userDao.findUser(user)).thenReturn(returnUser);

		validator.validate(user, errors);
		
		assertEquals(user.getUserStatus(),"ADMIN");
	}
	
	
	
	@Test
	public void logonSubmitFormItsAdminBadPwTest()
	{
		user.setUserId("admin");
		user.setUserPw("poop");
		
		returnUser.setUserId("admin");
		returnUser.setUserPw("password");
		
		when(userDao.findUser(user)).thenReturn(returnUser);

		validator.validate(user, errors);		
		
		assertEquals(user.getUserStatus(),"LOGGEDOUT");
	}
	
}
