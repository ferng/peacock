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
public class BinValidatorTest
{

	private BinDaoSvc binDao = mock(BinDaoSvc.class);
	private OrgDaoSvc orgDao = mock(OrgDaoSvc.class);
	private Errors errors;
	private BinValidator validator = new BinValidator();
	private BinRangeModel binRange = new BinRangeModel();
	private BinRangeModel returnBinRange = new BinRangeModel();
	private OrgModel org = new OrgModel();

	
	@Before
	public void prepResults()
	{
	  validator.setBinSvc(binDao);
	  validator.setOrgSvc(orgDao);
	  errors = new BeanPropertyBindingResult(binRange, "binRange");
	}



	@Test
	public void testAddExistingBinRange()
	{
    org.setOrgCode("CRNCHY");
    org.setOrgName("THE CRUNCHY CORNER");
    org.setOrgStatus("AUTH");
    
    binRange.setBinRange("123456");
    binRange.setBinOrg("CRNCHY");

    when(orgDao.findOrg("CRNCHY")).thenReturn(org);
    when(binDao.findBin("123456")).thenReturn(binRange);
    
		
		validator.validate(binRange, errors);

		assertTrue(errors.hasErrors());
	}

	
	
	@Test
	public void testAddNewAuthOrgBinRange()
	{
    org.setOrgCode("CRNCHY");
    org.setOrgName("THE CRUNCHY CORNER");
    org.setOrgStatus("AUTH");
    
    binRange.setBinRange("777888");
    binRange.setBinOrg("CRNCHY");

    returnBinRange.setBinOrg("");
    returnBinRange.setBinRange("");
    
    when(orgDao.findOrg("CRNCHY")).thenReturn(org);
    when(binDao.findBin("777888")).thenReturn(returnBinRange);
    
		
		validator.validate(binRange, errors);

		assertFalse(errors.hasErrors());
	}

	
	
	@Test
	public void testAddNewUnauthOrgBinRange()
	{
    org.setOrgCode("UNAUTH");
    org.setOrgName("UNAUTHORIZED ORG");
    org.setOrgStatus("NEW");
    
    binRange.setBinOrg("UNAUTH");
    binRange.setBinRange("888999");

    returnBinRange.setBinOrg("");
    returnBinRange.setBinRange("");
    
    when(orgDao.findOrg("UNAUTH")).thenReturn(org);
    when(binDao.findBin("888999")).thenReturn(returnBinRange);
    
		
		validator.validate(binRange, errors);

		assertTrue(errors.hasErrors());
	}

	
	
	
}
