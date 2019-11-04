package com.thecrunchycorner.mrpeacock.admin;

import com.thecrunchycorner.peacocklib.models.*;

import org.apache.log4j.NDC;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;


@Controller
@SessionAttributes({"user"})
public class AdminConsoleController
{
	private AdminLogonValidator adminLogonValidator;
	
	public AdminConsoleController()
	{
	}
	
	//TODO: make sure the DB access is via the manager not the dao directly (in case there's future business logic changes
	
	@RequestMapping(value="/adminConsole", method=RequestMethod.GET)
	public String setupForm(Model model)
	{
		AdminUserModel user = new AdminUserModel();
		model.addAttribute("user", user);
		return "adminConsole";
	}
	
	
	
	@RequestMapping(value="/adminConsole", method=RequestMethod.POST)
	public String submitLogonForm(@ModelAttribute("user") AdminUserModel user, BindingResult result, SessionStatus status)
	{
		NDC.push(user.getUserId());
		adminLogonValidator.validate(user, result);
		NDC.pop();
		
		if (result.hasErrors()) {
			return "adminConsole";
		}
		
		return "adminBase";
	}


	
	@RequestMapping(value="/adminLoggedOut", method=RequestMethod.GET)
	public String displayLoggedOutForm()
	{
		NDC.remove();
		return "adminLoggedOut";
	}


	@Autowired
  public void setAdminLogonValidator(AdminLogonValidator adminLogonValidator)
  {
    this.adminLogonValidator = adminLogonValidator;
  }

}
