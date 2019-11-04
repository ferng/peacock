package com.thecrunchycorner.peacockint.dbservices;

import com.thecrunchycorner.peacockint.updatecustdbmodel.*;

import java.util.*;

import org.hibernate.*;

import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.*;

import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MsgLogDaoImplTest
{
  MsgLogDaoImpl daoImpl = new MsgLogDaoImpl();
  
  private SessionFactory factory = mock(SessionFactory.class);
  private Session session = mock(Session.class);
  private Query query = mock(Query.class);
  private String msgID;
  
  @Test
  public final void findCustomerByIdFoundTest()
  {
    daoImpl.setSessionFactory(factory);
    when(factory.getCurrentSession()).thenReturn(session);
    when(session.createQuery("from MsgLogModel where msgId = :logMsgId")).thenReturn(query);
    
    msgID=UUID.randomUUID().toString();    
    
    MsgLogModel msgLog = new MsgLogModel();
    msgLog.setMsgLogId(1001);
    msgLog.setMsgId(msgID);
    msgLog.setMsgDateTime(new GregorianCalendar());
    msgLog.setMsgStatus(MsgStatus.RECEIVED);
    msgLog.setMsgSrcOrg("MYBNK");
    msgLog.setMsgDestOrg("CRNCHY");
    msgLog.setMsgError("");
    
    ArrayList<MsgLogModel> queryReplyList = new ArrayList<MsgLogModel>();
    queryReplyList.add(msgLog);
    
    when(query.list()).thenReturn(queryReplyList);
    
    assertEquals(msgLog.getMsgId(), msgID);
  }
  
  
  
  @Test
  public final void findCustomerByIdNotFoundTest()
  {
    daoImpl.setSessionFactory(factory);
    when(factory.getCurrentSession()).thenReturn(session);
    when(session.createQuery("from MsgLogModel where msgId = :logMsgId")).thenReturn(query);
    
    MsgLogModel msgLog = new MsgLogModel();
    
    ArrayList<MsgLogModel> queryReplyList = new ArrayList<MsgLogModel>();
    queryReplyList.add(msgLog);
    
    when(query.list()).thenReturn(queryReplyList);
    
    assertEquals(msgLog.getMsgStatus(), MsgStatus.NEW);
  }

  
}
