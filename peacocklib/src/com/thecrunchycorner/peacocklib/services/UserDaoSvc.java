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
public class UserDaoSvc implements UserDao
{

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	

	
	public AdminUserModel findUser(AdminUserModel user)
  {
		AdminUserModel userInDb = new AdminUserModel();
		
		try {
			BeanPropertyRowMapper<AdminUserModel> userRowMapper = BeanPropertyRowMapper.newInstance(AdminUserModel.class);
			userInDb = jdbcTemplate.queryForObject("SELECT * FROM APP_SCHEMA.PEACOCK_USERS WHERE USER_ID = ?", userRowMapper, user.getUserId());
		} catch (EmptyResultDataAccessException e) {
  	}	catch (Exception e) {
			throw new RuntimeException(e);
		}
		return userInDb;
  }



	public List<AdminUserModel> getAllUsers(String orgId)
	{
		List<AdminUserModel> userList;

    try {
      RowMapper<AdminUserModel> userRowMapper = BeanPropertyRowMapper.newInstance(AdminUserModel.class);
      userList = jdbcTemplate.query("SELECT * FROM APP_SCHEMA.PEACOCK_USERS WHERE USER_ORG = ?", userRowMapper, orgId);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return userList;
	}



	public void addUser(AdminUserModel user)
	{
    jdbcTemplate.update("INSERT INTO APP_SCHEMA.PEACOCK_USERS VALUES(NULL, ?, ?, ?, ?, ?, ?)",
        user.getUserId(), user.getUserOrg(), user.getUserFname(), user.getUserSname(), user.getUserPw(), user.getUserStatus()); 
	}



	public void updateUser(AdminUserModel user)
	{
	  jdbcTemplate.update(
	      "UPDATE APP_SCHEMA.PEACOCK_USERS SET USER_ID = ?, USER_ORG = ?, USER_FNAME = ?, USER_SNAME = ?, USER_PW = ?, USER_STATUS = ? WHERE USER_ID = ?",
        user.getUserId(), user.getUserOrg(), user.getUserFname(), user.getUserSname(), user.getUserPw(), user.getUserStatus(), user.getUserId() 
	      );
	}	
	
}
