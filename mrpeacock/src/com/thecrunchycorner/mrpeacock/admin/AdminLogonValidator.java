package com.thecrunchycorner.mrpeacock.admin;

import com.thecrunchycorner.peacocklib.models.*;
import com.thecrunchycorner.peacocklib.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

@Component
public class AdminLogonValidator implements Validator 
{

	static Logger logger = Logger.getLogger(AdminLogonValidator.class.getName());
	
	/* when this was getting called in findUser there was no DAO svc in context so trying to open the connection failed, setDataSource it was in context as it had
	 just been created so it was fine */

	private UserDaoSvc userSvc;
	
  AdminLogonValidator()
  {
  }

	
	public boolean supports(Class<?> clazz)
	{
		return AdminUserModel.class.isAssignableFrom(clazz);
	}
	
	
	
	public void validate(Object target, Errors errors)
	{
		BasicConfigurator.configure();

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "required.userId");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPw", "required.userPw");

		AdminUserModel user = (AdminUserModel) target;
		
		AdminUserModel checkedUser;
		
		checkedUser = userSvc.findUser(user);

		if (checkedUser.getUserId() == null) {
			user.setUserStatus("UNKNOWN");
			errors.reject("invalid.userNotFound");
			logger.debug("Validating user failure - unknown user: " + checkedUser.getUserId());
		} else {
			if (checkedUser.getUserPw().compareTo(user.getUserPw()) != 0) {
				errors.reject("invalid.invalidPassword");
        user.setUserStatus("LOGGEDOUT");
        logger.debug("Validating user success - invalid password: " + checkedUser.getUserId());
			} else {
				user.setUserStatus("ADMIN");
			}
		}
	}

	@Autowired
  public void setUserSvc(UserDaoSvc userSvc)
  {
    this.userSvc = userSvc;
  }
	
	
}
