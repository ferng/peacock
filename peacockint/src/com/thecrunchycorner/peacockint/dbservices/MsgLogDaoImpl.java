package com.thecrunchycorner.peacockint.dbservices;

import com.thecrunchycorner.peacockint.updatecustdbmodel.MsgLogModel;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MsgLogDaoImpl implements MsgLogDao
{
  private SessionFactory sessionFactory;
  
  public MsgLogDaoImpl()
  {
  }

  
  
  @Override
  @Transactional
  public MsgLogModel findLogById(String custId)
  {
    Query query = sessionFactory.getCurrentSession().createQuery("from MsgLogModel where msgId = :logMsgId");
    query.setParameter("logMsgId", custId);

    if (query.list().isEmpty()) {
      return new MsgLogModel();
    } else {
      return (MsgLogModel) query.list().get(0);
    }
  }
  
  
  
  @Override
  @Transactional
  public void insertMsgLog(MsgLogModel msgLog)
  {
    sessionFactory.getCurrentSession().saveOrUpdate(msgLog);
  }
  
  
  
  @Autowired
  public void setSessionFactory(SessionFactory sessionFactory)
  {
    this.sessionFactory = sessionFactory;
  }

}
