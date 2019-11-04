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
public class OrgValidatorTest
{
	private OrgDaoSvc orgDao = mock(OrgDaoSvc.class);
	private Errors errors;
	private OrgValidator validator = new OrgValidator();
	private OrgModel org = new OrgModel();
	private OrgModel returnOrg = new OrgModel();
	
	@Before
	public void prepResults()
	{
	  errors = new BeanPropertyBindingResult(org, "org");
	  validator.setOrgSvc(orgDao);
	}

	
	
	@Test
	public void testAddExistingOrg()
	{
		org.setOrgCode("JIJI");
		org.setOrgName("New Organization");
		org.setOrgStatus("NEW");

		when(orgDao.findOrg("JIJI")).thenReturn(org);
		
		validator.validate(org, errors);

		assertTrue(errors.hasErrors());
	}
	
	
	
	@Test
	public void testAddNewOrg()
	{
		org.setOrgCode("JIJI");
		org.setOrgName("New Organization");
		org.setOrgStatus("NEW");

		returnOrg.setOrgCode("");
		returnOrg.setOrgName("");
		returnOrg.setOrgStatus("");
		
		when(orgDao.findOrg("JIJI")).thenReturn(returnOrg);
		
		validator.validate(org, errors);
		
    assertFalse(errors.hasErrors());
	}


}
