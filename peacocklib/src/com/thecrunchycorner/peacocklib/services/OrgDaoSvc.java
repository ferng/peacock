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
public class OrgDaoSvc implements OrgDao
{
	private JdbcTemplate jdbcTemplate;


	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	
	public OrgModel findOrg(String code)
	{
		OrgModel orgInDb = new OrgModel();

		try {
			BeanPropertyRowMapper<OrgModel> orgRowMapper = BeanPropertyRowMapper.newInstance(OrgModel.class);
			orgInDb = jdbcTemplate.queryForObject("SELECT * FROM APP_SCHEMA.ORGS WHERE ORG_CODE = ?", orgRowMapper, code);
		} catch (EmptyResultDataAccessException e) {
  	}	catch (Exception e) {
			throw new RuntimeException(e);
		}
		return orgInDb;
  }	

	
	
	public List<OrgModel> getAllOrgs()
	{
		List<OrgModel> orgList;
		
		try {
			RowMapper<OrgModel> orgRowMapper = BeanPropertyRowMapper.newInstance(OrgModel.class);
			orgList = jdbcTemplate.query("SELECT * FROM APP_SCHEMA.ORGS", orgRowMapper);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return orgList;
	}

	
	
	public void addOrg(OrgModel org)
	{
		jdbcTemplate.update("INSERT INTO APP_SCHEMA.ORGS VALUES(NULL, ?, ?, ?)",
				org.getOrgCode(), org.getOrgName(), org.getOrgStatus()); 
	}

	

	public void updateOrg(OrgModel org)
	{
		jdbcTemplate.update("UPDATE APP_SCHEMA.ORGS SET ORG_NAME = ?, ORG_STATUS = ? WHERE ORG_CODE = ?", org.getOrgName(), org.getOrgStatus(), org.getOrgCode()); 
	}

}
