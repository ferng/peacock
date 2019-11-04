package com.thecrunchycorner.peacockint.dbservices;

import com.thecrunchycorner.peacockint.updatecustdbmodel.MsgLogModel;

public interface MsgLogDao
{
  public MsgLogModel findLogById(String msgId);
  public void insertMsgLog(MsgLogModel msgLog);
}
