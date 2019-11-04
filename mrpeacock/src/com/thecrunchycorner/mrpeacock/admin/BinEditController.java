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
@SessionAttributes({"binList", "range", "user", "userEditAction", "orgCodeList"})
public class BinEditController
{
  private List<BinRangeModel> binList = new ArrayList<BinRangeModel>(); 
  
	private BinValidator binValidator;
  private BinRangeMgmtSvc binMgmt;
	private OrgMgmtSvc orgMgmt;
	
  static Logger logger = Logger.getLogger(AdminLogonValidator.class.getName());

  public BinEditController()
  {
  }

  
  
  @RequestMapping(value="/orgShowAllBins", method=RequestMethod.GET, params={"orgId"})
  public String setupForm(Model model, @RequestParam(value="orgId") String orgId) throws IOException
  {
    binList = binMgmt.getAllBinRanges(orgId);
    model.addAttribute("binList", binList);

    return "orgShowAllBins";
  }

  
  
  @RequestMapping(value="/orgBinEdit", method=RequestMethod.GET, params={"formRange"})
  public String setupUserEntryForm(Model model, @ModelAttribute("binList") List<BinRangeModel> binList, @RequestParam(value="formRange") String formRange)
  				throws IOException
  {
  	List<OrgModel> orgList;
  	List<String> orgCodeList = new ArrayList<String>();

 	
  	BinRangeModel range = new BinRangeModel();

    String binRangeEditAction = "Add";
    String binRangeEditable = "false";
 	
  	for(BinRangeModel tmpBin : binList) {
  		if(tmpBin.getBinRange().compareTo(formRange) == 0) {
  			binRangeEditable = "true";
  	    binRangeEditAction = "Update";
  	    range = tmpBin;
  		}
  	}
  	model.addAttribute("binRangeEditable", binRangeEditable);
    model.addAttribute("binRangeEditAction", binRangeEditAction);
    model.addAttribute("range", range);

		orgList = orgMgmt.getAllOrgs();
		orgCodeList.add("");
		for(OrgModel org : orgList) {
			orgCodeList.add(org.getOrgCode());
		}
    model.addAttribute("orgCodeList", orgCodeList);
    
		Properties prop = new Properties();
		prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mrPeacock.properties"));

		String binRangeSize = prop.get("binRangeSize").toString();
		model.addAttribute("binRangeSize", binRangeSize);
  	
    return "orgBinEdit";
  }



	@RequestMapping(value="/orgBinEdit", method = RequestMethod.POST)
	public String userUpdateForm(@ModelAttribute("range") BinRangeModel range, BindingResult result, SessionStatus status)
	{
		String orgBinRange = range.getBinRange();
		
		NDC.push(orgBinRange);
    logger.debug("Bin range edit, selected: [" + orgBinRange);
    binValidator.validate(range, result);
		NDC.pop();
		if (result.hasErrors()) {
			return "orgBinEdit";
		} else {
			if ((binMgmt.findRange(orgBinRange)).getBinOrg().isEmpty()) {
				binMgmt.add(range);
			} else {
				binMgmt.update(range);
			}
      return "orgBinChange";
		}
	}


	@Autowired
  public void setBinValidator(BinValidator binValidator)
  {
    this.binValidator = binValidator;
  }


  @Autowired
  public void setBinMgmt(BinRangeMgmtSvc binMgmt)
  {
    this.binMgmt = binMgmt;
  }


  @Autowired
  public void setOrgMgmt(OrgMgmtSvc orgMgmt)
  {
    this.orgMgmt = orgMgmt;
  }

}
