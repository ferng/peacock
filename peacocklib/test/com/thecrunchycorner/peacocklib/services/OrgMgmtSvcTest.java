package com.thecrunchycorner.peacocklib.services;

import com.thecrunchycorner.peacocklib.models.*;

import java.util.ArrayList;
import java.util.List;

import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

//TODO add tests for find once all code moves to using Mgmt instead of DAO
@RunWith(MockitoJUnitRunner.class)
public class OrgMgmtSvcTest
{
	private OrgDaoSvc orgDao = mock(OrgDaoSvc.class);
	private OrgMgmtSvc mgmtSvc = new OrgMgmtSvc();
	
	@Before
	public void prepSvc()
	{
	  mgmtSvc.setOrgSvc(orgDao);
	}
	
	@Test
	public void testGetAllOrgs()
	{
		OrgModel orgModel = new OrgModel();

		List<OrgModel> orgList = new ArrayList<OrgModel>();
		List<OrgModel> retOrgList;

		orgModel.setOrgCode("CODE1");
		orgModel.setOrgName("Org Name 1");
		orgModel.setOrgStatus("NEW");
	
		orgList.add(orgModel);

		orgModel.setOrgCode("CODE2");
		orgModel.setOrgName("Org Name 2");
		orgModel.setOrgStatus("AUTH");

		orgList.add(orgModel);

		when(orgDao.getAllOrgs()).thenReturn(orgList);
		
		retOrgList = mgmtSvc.getAllOrgs();
		assertEquals(retOrgList.size(), 2);
	}

}
