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
public class UserValidator implements Validator 
{

	static Logger logger = Logger.getLogger(UserValidator.class.getName());
	
	private UserDaoSvc userSvc;
	private OrgDaoSvc orgSvc;
	
	UserValidator(){
	}
	
	
	
	public boolean supports(Class<?> clazz)
	{
		return OrgModel.class.isAssignableFrom(clazz);
	}
	
	
	
	public void validate(Object target, Errors errors)
	{
		BasicConfigurator.configure();

		String existingOrgStatus;
		AdminUserModel user = (AdminUserModel) target;
		AdminUserModel tmpUser;
		OrgModel org;

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "required.userId");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userOrg", "required.userOrg");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPw", "required.userPw");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userFname", "required.userFname");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userSname", "required.userSname");
		
		if(user.getUserStatus().compareTo("UNKNOWN") == 0) {
      logger.debug("New user entered: " + user.getUserId());

      tmpUser = userSvc.findUser(user);

      //null is not found
      if (tmpUser.getUserId() != null) {
        logger.debug("But it already exists");
        errors.reject("invalid.userExists");
      } else {
        org = orgSvc.findOrg(user.getUserOrg());
        existingOrgStatus = org.getOrgStatus(); 
        if ((existingOrgStatus.compareTo("AUTH") != 0) && (existingOrgStatus.compareTo("ACTIVE") != 0) ){
          errors.reject("invalid.orgIsUnauth");  
        }
      }
      
            
		}
	}

  @Autowired
  public void setUserSvc(UserDaoSvc userSvc)
  {
    this.userSvc = userSvc;
  }

  @Autowired
  public void setOrgSvc(OrgDaoSvc orgSvc)
  {
    this.orgSvc = orgSvc;
  }
	
	
	
}
