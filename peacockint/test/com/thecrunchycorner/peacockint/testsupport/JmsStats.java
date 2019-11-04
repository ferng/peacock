package com.thecrunchycorner.peacockint.testsupport;

import java.util.Enumeration;
import java.util.HashMap;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsStats
{
  ActiveMQConnectionFactory custRegQCF;
  private HashMap<String, Object> infoDetails = new HashMap<String, Object>();
    
  
  
  public JmsStats()
  {
    this.custRegQCF = new ActiveMQConnectionFactory();
  }

  
  
  public HashMap<String, Object> getQInfo(String qName) throws JMSException {
    Connection conn = null;
     
    try {
      conn = custRegQCF.createConnection();
      Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

      Queue replyTo = session.createTemporaryQueue();
      MessageConsumer consumer = session.createConsumer(replyTo);

      Queue regReqQ = session.createQueue("CUST.REGREQ");
      MessageProducer producer = session.createProducer(null);
      
      //String queueName = "ActiveMQ.Statistics.Broker." + regReqQ.getQueueName();
      String queueName = "ActiveMQ.Statistics.Destination." + regReqQ.getQueueName();
      Queue query = session.createQueue(queueName);
     
      Message msg = session.createMessage();
      msg.clearBody();
      
      msg.setJMSReplyTo(replyTo);
      producer.send(query, msg);
      conn.start();
      MapMessage reply = (MapMessage) consumer.receive();

      String name;
      
      
      for (@SuppressWarnings("unchecked") Enumeration<String> e = (Enumeration<String>) reply.getMapNames(); e.hasMoreElements();) {
        name = e.nextElement().toString();
        infoDetails.put(name, reply.getObject(name));
      }
    } catch (JMSException e) {
      throw e;
    } finally {
      try {
        conn.close();
      } catch (Exception e) {
          
      }
    }
    return infoDetails;
  }
  
}
