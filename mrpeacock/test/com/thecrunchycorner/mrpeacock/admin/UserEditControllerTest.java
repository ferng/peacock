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
public class UserEditControllerTest
{
  @Resource
  @Autowired
  private WebApplicationContext wac;
  
  @Autowired
  private MockHttpSession session;
  
  private MockMvc mockMvc;
  private AdminUserModel user = new AdminUserModel();
  
  private List<AdminUserModel> userList = new ArrayList<AdminUserModel>(); 
  

  
  @Before
  public void setup()
  {
    this.mockMvc = webAppContextSetup(this.wac).build();
  }

  
  
  @Test
  public void setUpFormTest() throws Exception
  {
    this.mockMvc.perform(get("/orgShowAllUsers").param("orgId", "CRNCHY").session(session).sessionAttr("userList", userList))
        .andExpect(status().isOk());
  }

  
  
  @Test
  public void selectUserTest() throws Exception
  {
    AdminUserModel user1 = new AdminUserModel();
    AdminUserModel user2 = new AdminUserModel();
    
    userList.clear();
    
    user1.setUserId("HHH1");
    user1.setUserOrg("CRNCHY");
    user1.setUserPw("password");
    user1.setUserFname("isaac");
    user1.setUserSname("asimov");
    user1.setUserStatus("CLIENTADM");
    userList.add(user1);
    
    user2.setUserId("HHH2");
    user2.setUserOrg("CRNCHY");
    user2.setUserPw("password");
    user2.setUserFname("pg");
    user2.setUserSname("woodhouse");
    user2.setUserStatus("CLIENTADM");
    userList.add(user2);
    
    this.mockMvc.perform(get("/orgUserEdit").param("formUserId", "HHH1").session(session).sessionAttr("userList", userList))
    .andExpect(status().isOk())
    .andExpect(view().name("orgUserEdit"));
  }



  @Test
  public void insertExistingUserTest() throws Exception
  {
    user.setUserId("admin");
    user.setUserOrg("CRNCHY");
    user.setUserPw("password");
    user.setUserFname("Nina");
    user.setUserSname("Gonzalez");
    user.setUserStatus("UNKNOWN");

    this.mockMvc.perform(post("/orgUserEdit").session(session).sessionAttr("user", user))
    		.andExpect(status().isOk())
    		.andExpect(view().name("orgUserEdit"))
    		.andExpect(model().hasErrors());
  }

  
  
  @Test 
  public void insertNewUserAuthOrgTest() throws Exception
  {
    user.setUserId("fg001");
    user.setUserOrg("CRNCHY");
    user.setUserFname("Fern");
    user.setUserSname("Gonzalez");
    user.setUserPw("password");
    user.setUserStatus("UNKNOWN");

    this.mockMvc.perform(post("/orgUserEdit").session(session).sessionAttr("user", user))
        .andExpect(status().isOk())
        .andExpect(view().name("orgUserChange"))
        .andExpect(model().hasNoErrors());
  }

  
  
  @Test 
  public void insertNewUserUnauthOrgTest() throws Exception
  {
    user.setUserId("fg002");
    user.setUserOrg("UNAUTH");
    user.setUserFname("Isaac");
    user.setUserSname("Assimov");
    user.setUserPw("password");
    user.setUserStatus("UNKNOWN");

    this.mockMvc.perform(post("/orgUserEdit").session(session).sessionAttr("user", user))
        .andExpect(status().isOk())
        .andExpect(view().name("orgUserEdit"))
        .andExpect(model().hasErrors());
   }

  
  
  @Test
  public void missingUserFieldTest() throws Exception
  {
    user.setUserId("fg003");
    user.setUserOrg("CRNCHY");
    user.setUserFname("Paul");
    user.setUserSname("Erdos");
    user.setUserPw("");
    user.setUserStatus("UNKNOWN");

    this.mockMvc.perform(post("/orgUserEdit").session(session).sessionAttr("user", user))
        .andExpect(status().isOk())
        .andExpect(view().name("orgUserEdit"))
        .andExpect(model().hasErrors());
  }

  
  
  @Test
  public void cancelButtonTest() throws Exception
  {
    this.mockMvc.perform(get("/orgShowAllUsers?orgId=CRNCHY").session(session))
    .andExpect(status().isOk())
    .andExpect(view().name("orgShowAllUsers"))
    .andExpect(model().hasNoErrors());
  }
  

}
