package com.thecrunchycorner.mrpeacock.admin;

import com.thecrunchycorner.peacocklib.models.*;

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
public class AdminConsoleControllerTest
{
	@Resource
	@Autowired
	private WebApplicationContext wac;
	
  @Autowired
  private MockHttpSession session;
	
	private MockMvc mockMvc;
	private AdminUserModel user = new AdminUserModel();
	

	
	@Before
  public void setup()
	{
    this.mockMvc = webAppContextSetup(this.wac).build();
  }

	
	
  @Test
  public void setUpFormTest() throws Exception
  {
    this.mockMvc.perform(get("/adminConsole"))
        .andExpect(status().isOk());
  }


  
  @Test
  public void logonSubmitFormItsAdminTest() throws Exception
  {
  	user.setUserId("admin");
  	user.setUserPw("password");
  	
  	this.mockMvc.perform(post("/adminConsole").session(session).sessionAttr("user", user))
  			.andExpect(status().isOk())
  			.andExpect(view().name("adminBase"));
  }
  
  

  @Test
  public void logonSubmitFormItsNotAdminTest() throws Exception
  {
  	user.setUserId("fern");
  	user.setUserPw("password");
  	
  	this.mockMvc.perform(post("/adminConsole").session(session).sessionAttr("user", user))
  			.andExpect(status().isOk())
  			.andExpect(view().name("adminConsole"));
  }

  

  @Test
  public void logonSubmitFormMissingCredentialsTest() throws Exception
  {
  	user.setUserId("");
  	user.setUserPw("");
  	
  	this.mockMvc.perform(post("/adminConsole").session(session).sessionAttr("user", user))
  			.andExpect(status().isOk())
  			.andExpect(view().name("adminConsole"));

  }

}
