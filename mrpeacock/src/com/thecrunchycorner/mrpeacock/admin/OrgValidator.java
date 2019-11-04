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
public class OrgValidator implements Validator 
{

	static Logger logger = Logger.getLogger(OrgValidator.class.getName());
	
	private OrgDaoSvc orgSvc;
	
	OrgValidator(){
	}
	
	
	public boolean supports(Class<?> clazz)
	{
		return OrgModel.class.isAssignableFrom(clazz);
	}
	
	
	
	public void validate(Object target, Errors errors)
	{
		BasicConfigurator.configure();

		OrgModel org = (OrgModel) target;
    OrgModel tmpOrg;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "orgCode", "required.orgCode");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "orgName", "required.orgName");

		if (org.getOrgStatus().compareTo("NEW") == 0) {
			logger.debug("New organization entered: " + org.getOrgName());

			tmpOrg = orgSvc.findOrg(org.getOrgCode());

			if (tmpOrg.getOrgCode().compareTo("") != 0) {
			  logger.debug("But it already exists");
				errors.reject("invalid.orgExists");
			}
		}
	}

	@Autowired
  public void setOrgSvc(OrgDaoSvc orgSvc)
  {
    this.orgSvc = orgSvc;
  }
	
	
}
