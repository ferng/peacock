package com.thecrunchycorner.peacockint.regprocessor;
/*
import com.thecrunchycorner.peacockint.updatecustdbmodel.CustomerModel;

import org.apache.log4j.Logger;
import edu.umd.cs.findbugs.annotations.SuppressWarnings;

public class ProcessorController implements JmsListenerObserver
{
  static Logger logger = Logger.getLogger(ProcessorController.class.getName());
  private JmsListenerCustRegrec jmsListener;
//  private WorkerThreadPool threadPool;
  private RegReqValSender regReqSender;
  private boolean registered = false;
  
  public ProcessorController()
  {
  }
  
    
  public void update(CustomerModel dbCust) 
  {
    synchronized(this) {
      if (registered == false) {
        jmsListener.regObs(this);
        registered = true;
      }
    }
    regReqSender.setCust(dbCust);
//    threadPool.startThread(regReqSender);
  }

  
  @SuppressWarnings(value="IS2_INCONSISTENT_SYNC", justification="this is a singleton bean so can only be run once on context startup (or testing)")
  public void setJmsListener(JmsListenerCustRegrec jmsListener)
  {
    this.jmsListener = jmsListener;
  }

/*
  public void setThreadPool(WorkerThreadPool threadPool)
  {
    this.threadPool = threadPool;
  }


  public void setRegReqSender(RegReqValSender regReqSender)
  {
    this.regReqSender = regReqSender;
  }
  

  
}
*/