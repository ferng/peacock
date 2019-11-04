package com.thecrunchycorner.peacockint.receiver;

import com.thecrunchycorner.peacockint.updatecustdbmodel.CustomerModel;

import org.apache.log4j.Logger;

import org.springframework.jms.core.support.JmsGatewaySupport;



public class CustomerRegJmsImpl extends JmsGatewaySupport implements CustomerRegJms
{
  static Logger logger = Logger.getLogger(CustomerRegJmsImpl.class.getName());
  
  @Override
  public void postMsgToJms(final CustomerModel customer)
  {
    getJmsTemplate().convertAndSend(customer);
    logger.debug("Message posted to jms");
  }

}
