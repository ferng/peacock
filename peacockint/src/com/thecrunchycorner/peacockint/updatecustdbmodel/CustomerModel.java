package com.thecrunchycorner.peacockint.updatecustdbmodel;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="APP_SCHEMA.CUSTOMER")
public class CustomerModel
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name="CUST_ID", nullable=false)
  private long customerId;

  @Column(name="CUST_ORG_CUST_ID", nullable=false)
  private String customerOrgCustId;
  
  @Column(name="CUST_ORG_ID", nullable=false)
  private String customerOrg;

  @Column(name="CUST_STATUS", nullable=false)
  private CustomerModelStatusType customerStatus;

  @Column(name="CUST_FNAME", nullable=false)
  private String customerFname;
  
  @Column(name="CUST_SNAME", nullable=false)
  private String customerSname;
  
  @Column(name="CUST_DOB", nullable=false)
  private GregorianCalendar customerDOB;
  
  @Column(name="CUST_START_DATE", nullable=false)
  private GregorianCalendar customerStartDate;
  
  @Column(name="CUST_END_DATE", nullable=false)
  private GregorianCalendar customerEndDate;
  
  public CustomerModel()
  {
    customerId = 0;
    customerOrgCustId = "";
    customerOrg = "";
    customerStatus = CustomerModelStatusType.UNAUTH;
    customerFname = "";
    customerSname = "";
    customerDOB = new GregorianCalendar();
    customerStartDate = new GregorianCalendar();
    customerEndDate = new GregorianCalendar();
  }
  
  public CustomerModel(String customerOrgCustId, String customerOrg, CustomerModelStatusType customerStatus, String customerFname, String customerSname,
              GregorianCalendar customerDOB, GregorianCalendar customerStartDate, GregorianCalendar customerEndDate)
  {
    customerId = 0;
    this.customerOrgCustId = customerOrgCustId;
    this.customerOrg = customerOrg;
    this.customerStatus = customerStatus;
    this.customerFname = customerFname;
    this.customerSname = customerSname;
    this.customerDOB = customerDOB;
    this.customerStartDate = customerStartDate;
    this.customerEndDate = customerEndDate;
  }
  
  public long getCustomerId()
  {
    return customerId;
  }

  public void setCustomerId(long customerId)
  {
    this.customerId = customerId;
  }

  public String getCustomerOrg()
  {
    return customerOrg;
  }

  public void setCustomerOrg(String customerOrg)
  {
    this.customerOrg = customerOrg;
  }

  public String getCustomerOrgCustId()
  {
    return customerOrgCustId;
  }

  public void setCustomerOrgCustId(String customerOrgCustId)
  {
    this.customerOrgCustId = customerOrgCustId;
  }

  public CustomerModelStatusType getCustomerStatus()
  {
    return customerStatus;
  }

  public void setCustomerStatus(CustomerModelStatusType customerStatus)
  {
    this.customerStatus = customerStatus;
  }

  public String getCustomerFname()
  {
    return customerFname;
  }

  public void setCustomerFname(String customerFname)
  {
    this.customerFname = customerFname;
  }

  public String getCustomerSname()
  {
    return customerSname;
  }

  public void setCustomerSname(String customerSname)
  {
    this.customerSname = customerSname;
  }

  public GregorianCalendar getCustomerDOB()
  {
    return customerDOB;
  }

  public void setCustomerDOB(GregorianCalendar customerDOB)
  {
    this.customerDOB = customerDOB;
  }

  public GregorianCalendar getCustomerStartDate()
  {
    return customerStartDate;
  }

  public void setCustomerStartDate(GregorianCalendar customerStartDate)
  {
    this.customerStartDate = customerStartDate;
  }

  public GregorianCalendar getCustomerEndDate()
  {
    return customerEndDate;
  }

  public void setCustomerEndDate(GregorianCalendar customerEndDate)
  {
    this.customerEndDate = customerEndDate;
  }


}
