package com.thecrunchycorner.peacocklib.services;

import com.thecrunchycorner.peacocklib.models.*;

import java.util.*;

public interface BinDao
{
	public BinRangeModel findBin(String binRange);
	public List<BinRangeModel> getAllBinRanges(String orgId);
	public void addBinRange(BinRangeModel binRange);
	public void updateBinRange(BinRangeModel binRange);
}
