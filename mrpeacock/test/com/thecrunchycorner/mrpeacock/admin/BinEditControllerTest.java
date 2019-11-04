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
public class BinEditControllerTest
{
  @Resource
  @Autowired
  private WebApplicationContext wac;
  
  @Autowired
  private MockHttpSession session;
  
  private MockMvc mockMvc;
  private BinRangeModel range = new BinRangeModel();
  
  private List<BinRangeModel> binList = new ArrayList<BinRangeModel>(); 


  
  @Before
  public void setup()
  {
    this.mockMvc = webAppContextSetup(this.wac).build();
  }

  
  
  @Test
  public void setUpFormTest() throws Exception
  {
    this.mockMvc.perform(get("/orgShowAllBins").param("orgId", "CRNCHY").session(session).sessionAttr("binList", binList))
        .andExpect(status().isOk());
  }

  
  
  @Test
  public void selectBinTest() throws Exception
  {
    BinRangeModel bin1 = new BinRangeModel();
    BinRangeModel bin2 = new BinRangeModel();
    
    binList.clear();
    
    bin1.setBinRange("100001");
    bin1.setBinOrg("CRNCHY");
    binList.add(bin1);
    
    bin1.setBinRange("100002");
    bin1.setBinOrg("CRNCHY");
    binList.add(bin2);    
    
    this.mockMvc.perform(get("/orgBinEdit").param("formRange", "100001").session(session).sessionAttr("binList", binList))
    .andExpect(status().isOk())
    .andExpect(view().name("orgBinEdit"));
  }

  
  
  @Test
  public void insertExistingBinTest() throws Exception
  {
  	range.setBinRange("000001");
  	range.setBinOrg("CRNCHY");

  	this.mockMvc.perform(post("/orgBinEdit").session(session).sessionAttr("range", range))
  			.andExpect(status().isOk())
  			.andExpect(view().name("orgBinEdit"))
  			.andExpect(model().hasErrors());
  }

  
  
  @Test
  public void insertNewBinTest() throws Exception
  {
  	range.setBinRange("999999");
  	range.setBinOrg("CRNCHY");

  	this.mockMvc.perform(post("/orgBinEdit").session(session).sessionAttr("range", range))
  			.andExpect(status().isOk())
  			.andExpect(view().name("orgBinChange"))
  			.andExpect(model().hasNoErrors());
  }
  




}