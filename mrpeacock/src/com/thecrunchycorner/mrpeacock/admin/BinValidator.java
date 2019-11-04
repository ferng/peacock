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
public class BinValidator implements Validator 
{

	static Logger logger = Logger.getLogger(BinValidator.class.getName());
	
	private BinDaoSvc binSvc;
	private OrgDaoSvc orgSvc;
	
	BinValidator(){
	}
	
	public boolean supports(Class<?> clazz)
	{
		return OrgModel.class.isAssignableFrom(clazz);
	}
	
	
	
	public void validate(Object target, Errors errors)
	{
		BasicConfigurator.configure();

		String existingOrgStatus;
		BinRangeModel newBinRange = (BinRangeModel) target;
		BinRangeModel tmpBinRange; 
		OrgModel org;

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "binRange", "required.binRange");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "binOrg", "required.binOrg");
		
		logger.debug("New bin range entered: " + newBinRange.getBinRange());
		
		tmpBinRange = binSvc.findBin(newBinRange.getBinRange());

		if (!(tmpBinRange.getBinRange().isEmpty())) {
			logger.debug("But it already exists");
			errors.reject("required.binRange");
		} else {
      org = orgSvc.findOrg(newBinRange.getBinOrg());
      existingOrgStatus = org.getOrgStatus(); 
      if ((existingOrgStatus.compareTo("AUTH") != 0) && (existingOrgStatus.compareTo("ACTIVE") != 0) ){
        errors.reject("invalid.orgIsUnauth"); 
      }
		}
	}

  @Autowired
  public void setBinSvc(BinDaoSvc binSvc)
  {
    this.binSvc = binSvc;
  }

  @Autowired
  public void setOrgSvc(OrgDaoSvc orgSvc)
  {
    this.orgSvc = orgSvc;
  }
		
	
}
