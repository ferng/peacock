package com.thecrunchycorner.mrpeacock.admin;

import com.thecrunchycorner.peacocklib.models.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.*;
import org.junit.runner.RunWith;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("dev")
@ContextConfiguration("file:WebContent/WEB-INF/admin-servlet.xml")
public class AdminOrgEditControllerTest
{
	@Resource
	@Autowired
	private WebApplicationContext wac;
	
  @Autowired
  private MockHttpSession session;
	
	private MockMvc mockMvc;
	private OrgModel org = new OrgModel();
	
	@Before
  public void setup()
	{
    this.mockMvc = webAppContextSetup(this.wac).build();
	}


	
  @Test
  public void setUpFormTest() throws Exception
  {
    this.mockMvc.perform(get("/orgShowAllOrgs"))
        .andExpect(status().isOk());
  }
  

  
  @Test
  public void selectBank() throws Exception
  {
    List<OrgModel> orgList = new ArrayList<OrgModel>(); 
    
    OrgModel orgModel1 = new OrgModel();
    OrgModel orgModel2 = new OrgModel();
    OrgModel orgModel3 = new OrgModel();

    orgModel1.setOrgCode("CODE1");
    orgModel1.setOrgName("Org Name 1");
    orgModel1.setOrgStatus("NEW");
    orgList.add(orgModel1);

    orgModel2.setOrgCode("CODE2");
    orgModel2.setOrgName("Org Name 2");
    orgModel2.setOrgStatus("AUTH");
    orgList.add(orgModel2);

    orgModel3.setOrgCode("CODE3");
    orgModel3.setOrgName("Org Name 3");
    orgModel3.setOrgStatus("ACTIVE");
    orgList.add(orgModel3);

    this.mockMvc.perform(get("/orgEdit").param("id", "CODE2").session(session).sessionAttr("orgList", orgList))
  			.andExpect(status().isOk())
  			.andExpect(view().name("orgEdit"))
  			.andExpect(model().attribute("orgEditAction", "Update"));
  }
 
 
 
  @Test
  public void insertExistingOrgTest() throws Exception
  {
  	org.setOrgCode("CRNCHY");
  	org.setOrgName("Home Bank");
  	org.setOrgStatus("NEW");
  	
  	this.mockMvc.perform(post("/orgEdit").session(session).sessionAttr("org", org))
  			.andExpect(status().isOk())
  			.andExpect(view().name("orgEdit"))
  			.andExpect(model().hasErrors());
  } 

  
  
  @Test
  public void insertNewOrgTest() throws Exception
  {
  	org.setOrgCode("FXFX");
  	org.setOrgName("New Organisation");
  	org.setOrgStatus("NEW");
  	
  	this.mockMvc.perform(post("/orgEdit").session(session).sessionAttr("org", org))
  			.andExpect(status().isOk())
  			.andExpect(view().name("orgChange"))
  			.andExpect(model().hasNoErrors());
  }

  
  
  @Test
  public void insertIncompleteOrgTest() throws Exception
  {
  	org.setOrgCode("FX2");
  	org.setOrgName("");
  	org.setOrgStatus("NEW");
  	
  	this.mockMvc.perform(post("/orgEdit").session(session).sessionAttr("org", org))
  			.andExpect(status().isOk())
  			.andExpect(view().name("orgEdit"))
  			.andExpect(model().hasErrors());
  }


}