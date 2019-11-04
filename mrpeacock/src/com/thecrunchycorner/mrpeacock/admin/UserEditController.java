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
@SessionAttributes({"userList", "user", "userEditAction", "userStatusList", "orgCodeList"})
public class UserEditController
{
  private List<AdminUserModel> userList = new ArrayList<AdminUserModel>(); 
  
	private UserValidator userValidator;
  private UserMgmtSvc userMgmt;
	private OrgMgmtSvc orgMgmt;
	
  static Logger logger = Logger.getLogger(AdminLogonValidator.class.getName());



  public UserEditController()
  {
  }
  
  
  
  @RequestMapping(value="/orgShowAllUsers", method=RequestMethod.GET, params={"orgId"})
  public String setupForm(Model model, @RequestParam(value="orgId") String orgId) throws IOException
  {
    userList = userMgmt.getAllUsers(orgId);
    model.addAttribute("userList", userList);

    return "orgShowAllUsers";
  }

  
  
  @RequestMapping(value="/orgUserEdit", method=RequestMethod.GET, params={"formUserId"})
  public String setupUserEntryForm(Model model, @ModelAttribute("userList") List<AdminUserModel> userList, @RequestParam(value="formUserId") String formUserId)
  				throws IOException
  {
  	List<String> userStatusList = new ArrayList<String>();
  	List<OrgModel> orgList;
  	List<String> orgCodeList = new ArrayList<String>();

  	
  	AdminUserModel user = new AdminUserModel();

    String userEditAction = "Add";
    String userIdEditable = "false";
    userStatusList.add("UNKNOWN");
 	
  	for(AdminUserModel tmpUser : userList) {
  		if(tmpUser.getUserId().compareTo(formUserId) == 0) {
  			userStatusList.add("LOGGEDOUT");
  			userStatusList.add("ADMIN");
  			userStatusList.add("CLIENTADM");
  			userIdEditable = "true";
  	    userEditAction = "Update";
  	    user = tmpUser;
  		}
  	}
  	model.addAttribute("userStatusList", userStatusList);
  	model.addAttribute("userIdEditable", userIdEditable);
    model.addAttribute("userEditAction", userEditAction);
    model.addAttribute("user", user);

		orgList = orgMgmt.getAllOrgs();
		orgCodeList.add("");
		for(OrgModel org : orgList) {
			orgCodeList.add(org.getOrgCode());
		}
    model.addAttribute("orgCodeList", orgCodeList);
    
		Properties prop = new Properties();
		prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mrPeacock.properties"));

		String userIdSize = prop.get("userIdSize").toString();
		String orgCodeSize = prop.get("orgCodeSize").toString();
		String userFnameSize = prop.get("userFnameSize").toString();
		String userSnameSize = prop.get("userSnameSize").toString();
		String userPwSize = prop.get("userPwSize").toString();
		model.addAttribute("userIdSize", userIdSize);
		model.addAttribute("orgCodeSize", orgCodeSize);
		model.addAttribute("userFnameSize", userFnameSize);
		model.addAttribute("userSnameSize", userSnameSize);
		model.addAttribute("userPwSize", userPwSize);
  	
    return "orgUserEdit";
  }



	@RequestMapping(value="/orgUserEdit", method = RequestMethod.POST)
	public String userUpdateForm(@ModelAttribute("user") AdminUserModel user, BindingResult result, SessionStatus status)
	{
	  NDC.push(user.getUserId());
    logger.debug("User edit, selected: [" + user.getUserFname() + " " + user.getUserSname() + "]");
    userValidator.validate(user, result);
		NDC.pop();
		if (result.hasErrors()) {
			return "orgUserEdit";
		} else {
      if (user.getUserStatus().compareTo("UNKNOWN") == 0) {
        userMgmt.add(user);
      } else {
        userMgmt.update(user);
      }
      return "orgUserChange";
		}
	}


  @Autowired
  public void setUserValidator(UserValidator userValidator)
  {
    this.userValidator = userValidator;
  }


  @Autowired
  public void setUserMgmt(UserMgmtSvc userMgmt)
  {
    this.userMgmt = userMgmt;
  }


  @Autowired
  public void setOrgMgmt(OrgMgmtSvc orgMgmt)
  {
    this.orgMgmt = orgMgmt;
  }


}
