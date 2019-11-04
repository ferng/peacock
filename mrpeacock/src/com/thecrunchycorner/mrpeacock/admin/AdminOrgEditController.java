package com.thecrunchycorner.mrpeacock.admin;

import com.thecrunchycorner.peacocklib.models.*;
import com.thecrunchycorner.peacocklib.services.*;

import java.util.*;
import java.io.*;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;


@Controller
@SessionAttributes({"org", "orgEditAction", "orgStatusList", "orgList", "user"})
public class AdminOrgEditController
{
  static Logger logger = Logger.getLogger(AdminLogonValidator.class.getName());

  private List<OrgModel> orgList = new ArrayList<OrgModel>();	

	private OrgValidator orgValidator;
	private OrgMgmtSvc orgMgmt;
	

  public AdminOrgEditController()
  {
  }

	
	@RequestMapping(value="/orgShowAllOrgs", method=RequestMethod.GET)
	public String setupForm(Model model)
	{
		orgList = orgMgmt.getAllOrgs();
		model.addAttribute("orgList", orgList);
		
		return "orgShowAllOrgs";
	}



	@RequestMapping(value="/orgEdit", method=RequestMethod.GET, params={"id"})
	public String setupOrgEntryForm(Model model, @ModelAttribute("orgList") List<OrgModel> orgList, @RequestParam(value="id") String id) throws IOException
	{
		List<String> orgStatusList = new ArrayList<String>();
			
		model.addAttribute("orgStatusList", orgStatusList);
    OrgModel org = new OrgModel();

		String orgEditAction = "Add";
		String orgCodeEditable = "false";
		orgStatusList.add("NEW");

		for (OrgModel tmpOrg : orgList) {
			if (tmpOrg.getOrgCode().compareTo(id) == 0) {
				orgStatusList.add("ACTIVE");
				orgStatusList.add("AUTH");
				orgStatusList.add("SUSPENDED");
				orgEditAction = "Update";
				orgCodeEditable = "true";
				org = tmpOrg;
			} 
		}
		model.addAttribute("orgEditAction", orgEditAction);
		model.addAttribute("orgCodeEditable", orgCodeEditable);
		model.addAttribute("org", org);

		logger.debug("Organization edit, selected: [" + org.getOrgName() + "]");

		Properties prop = new Properties();
		prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mrPeacock.properties"));

		String orgCodeSize = prop.get("orgCodeSize").toString();
		String orgNameSize = prop.get("orgNameSize").toString();

		model.addAttribute("orgCodeSize", orgCodeSize);
		model.addAttribute("orgNameSize", orgNameSize);

		return "orgEdit";
	}		



	@RequestMapping(value="/orgEdit", method = RequestMethod.POST)
	public String orgUpdateForm(@ModelAttribute("org") OrgModel org, BindingResult result, SessionStatus status)
	{
		NDC.push(org.getOrgName());
		orgValidator.validate(org, result);
		NDC.pop();
		
		if (result.hasErrors()) {
			return "orgEdit";
		} else {
		  if (org.getOrgStatus().compareTo("NEW") == 0) {
		    orgMgmt.add(org);
		  } else {
		    orgMgmt.update(org);
		  }

		  return "orgChange";
		}
	}


	@Autowired
  public void setOrgValidator(OrgValidator orgValidator)
  {
    this.orgValidator = orgValidator;
  }

	@Autowired
  public void setOrgMgmt(OrgMgmtSvc orgMgmt)
  {
    this.orgMgmt = orgMgmt;
  }

	
		
}
