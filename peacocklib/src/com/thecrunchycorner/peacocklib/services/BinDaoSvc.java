package com.thecrunchycorner.peacocklib.services;

import com.thecrunchycorner.peacocklib.models.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;

@Repository
public class BinDaoSvc implements BinDao
{

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	

	
	public BinRangeModel findBin(String binRange)
  {
		BinRangeModel binRangeInDb = new BinRangeModel();
		
		try {
			BeanPropertyRowMapper<BinRangeModel> binRowMapper = BeanPropertyRowMapper.newInstance(BinRangeModel.class);
			binRangeInDb = jdbcTemplate.queryForObject("SELECT * FROM APP_SCHEMA.BIN_RANGE WHERE BIN_RANGE = ?", binRowMapper, binRange);
		} catch (EmptyResultDataAccessException e) {
  	}	catch (Exception e) {
			throw new RuntimeException(e);
		}
		return binRangeInDb;
  }



	public List<BinRangeModel> getAllBinRanges(String orgId)
	{
		List<BinRangeModel> binRangeList;

    try {
      RowMapper<BinRangeModel> binRangeRowMapper = BeanPropertyRowMapper.newInstance(BinRangeModel.class);
      binRangeList = jdbcTemplate.query("SELECT * FROM APP_SCHEMA.BIN_RANGE WHERE BIN_ORG = ?", binRangeRowMapper, orgId);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return binRangeList;
	}



	public void addBinRange(BinRangeModel binRange)
	{
	  jdbcTemplate.update("INSERT INTO APP_SCHEMA.BIN_RANGE VALUES(NULL, ?, ?)",
    		binRange.getBinRange(), binRange.getBinOrg()); 
	}



	public void updateBinRange(BinRangeModel binRange)
	{
	  jdbcTemplate.update(
	      "UPDATE APP_SCHEMA.BIN_RANGE SET BIN_RANGE = ?, BIN_ORG = ? WHERE BIN_RANGE = ?", binRange.getBinRange(), binRange.getBinOrg(), binRange.getBinRange());
	}	
	
}
