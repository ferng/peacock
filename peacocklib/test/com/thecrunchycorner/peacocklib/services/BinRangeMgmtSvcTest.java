package com.thecrunchycorner.peacocklib.services;

import com.thecrunchycorner.peacocklib.models.*;

import java.util.ArrayList;
import java.util.List;

import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class BinRangeMgmtSvcTest
{
  private BinDaoSvc binDao = mock(BinDaoSvc.class);
  private BinRangeMgmtSvc mgmtSvc = new BinRangeMgmtSvc();
  
  @Before
  public void prepSvc()
  {
    mgmtSvc.setBinSvc(binDao);
  }
  
  @Test
  public void testGetAllBinRanges()
  {
    BinRangeModel binRange = new BinRangeModel();
    
    List<BinRangeModel> binRangeList = new ArrayList<BinRangeModel>();
    List<BinRangeModel> retBinRangeList;
    
    binRange.setBinOrg("CRNCHY");
    binRange.setBinRange("123456");
    binRangeList.add(binRange);
    
    binRange.setBinOrg("CRNCHY");
    binRange.setBinRange("987654");
    binRangeList.add(binRange);
    
    when(binDao.getAllBinRanges("CRNCHY")).thenReturn(binRangeList);
    
    retBinRangeList = mgmtSvc.getAllBinRanges("CRNCHY");
    assertEquals(retBinRangeList.size(), 2);

    retBinRangeList = mgmtSvc.getAllBinRanges("NOT");
    assertNotEquals(retBinRangeList.size(), 2);
  }

  

  @Test
  public void testGetExistingBinRange()
  {
    BinRangeModel binRange = new BinRangeModel();
    BinRangeModel retRange;
    
    binRange.setBinOrg("CRNCHY");
    binRange.setBinRange("123456");

    when(binDao.findBin("123456")).thenReturn(binRange);

    retRange = mgmtSvc.findRange("123456");
    assertEquals(retRange.getBinOrg(), "CRNCHY");
  }
  
  

  @Test
  public void testGetNonExistingBinRange()
  {
    BinRangeModel binRange = new BinRangeModel();
    BinRangeModel retRange;
    
    when(binDao.findBin("123456")).thenReturn(binRange);

    retRange = mgmtSvc.findRange("123456");

    assertTrue(retRange.getBinRange().isEmpty());
  }

}