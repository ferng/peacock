package com.thecrunchycorner.peacockint.updatecustdbmodel;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="APP_SCHEMA.MSG_LOG")
public class MsgLogModel
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name="MSG_LOG_ID", nullable=false)
  private long msgLogId;

  @Column(name="MSG_ID", nullable=false)
  private String msgId;
  
  @Column(name="MSG_DATETIME", nullable=false)
  private GregorianCalendar msgDateTime;

  @Enumerated(EnumType.STRING)
  @Column(name="MSG_STATUS", nullable=false)
  private MsgStatus msgStatus;

  @Column(name="MSG_SOURCE_ORG", nullable=false)
  private String msgSrcOrg;

  @Column(name="MSG_DEST_ORG", nullable=false)
  private String msgDestOrg;
    
  @Column(name="MSG_ERROR")
  private String msgError;

  public MsgLogModel()
  {
    msgLogId = 0;
    msgId = "";
    msgDateTime = new GregorianCalendar();
    msgStatus = MsgStatus.NEW;
    msgSrcOrg = "";
    msgDestOrg = "";
    msgError = "";
  }

  public long getMsgLogId()
  {
    return msgLogId;
  }

  public void setMsgLogId(long msgLogId)
  {
    this.msgLogId = msgLogId;
  }

  public String getMsgId()
  {
    return msgId;
  }

  public void setMsgId(String msgId)
  {
    this.msgId = msgId;
  }

  public GregorianCalendar getMsgDateTime()
  {
    return msgDateTime;
  }

  public void setMsgDateTime(GregorianCalendar msgDateTime)
  {
    this.msgDateTime = msgDateTime;
  }

  public MsgStatus getMsgStatus()
  {
    return msgStatus;
  }

  public void setMsgStatus(MsgStatus msgStatus)
  {
    this.msgStatus = msgStatus;
  }

  public String getMsgSrcOrg()
  {
    return msgSrcOrg;
  }

  public void setMsgSrcOrg(String msgSrcOrg)
  {
    this.msgSrcOrg = msgSrcOrg;
  }

  public String getMsgDestOrg()
  {
    return msgDestOrg;
  }

  public void setMsgDestOrg(String msgDestOrg)
  {
    this.msgDestOrg = msgDestOrg;
  }

  public String getMsgError()
  {
    return msgError;
  }

  public void setMsgError(String msgError)
  {
    this.msgError = msgError;
  }

    
}
