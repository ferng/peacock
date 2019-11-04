package com.thecrunchycorner.peacockint.regprocessor;
//TODO rename JmsListener to Controller (as it does everything now)  get rid of threadpool and controller
//TODO redo all unit tests that counted, they never reliably worked anyway
import com.thecrunchycorner.peacockint.updatecustdbmodel.CustomerModel;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;


public class JmsListenerCustRegrec implements JmsListener
{
  private ArrayList<JmsListenerObserver> obsList;
  private CustomerModel dbCust = new CustomerModel();

  private RegReqValSender regReqSender;

  public JmsListenerCustRegrec()
  {
    obsList = new ArrayList<JmsListenerObserver>();
  }


  @Override
  public void regObs(JmsListenerObserver o)
  {
    obsList.add(o);
  }


  @Override
  public void dropObs(JmsListenerObserver o)
  {
    int i = obsList.indexOf(o);
    if (i >= 0) {
      obsList.remove(i);
    }
  }

  
  @Override
  public void notifyObservers()
  {
    for (JmsListenerObserver observer :  obsList) {
      observer.update(dbCust);
    }
  }

 
  //public void readJmsMsg(Map<?, ?> map)
  public void readJmsMsg(CustomerModel cust)
  {

    regReqSender.setCust(cust);
    try {
    regReqSender.call();
    } catch (Exception e) {}
    /* dbCust = cust;
    
    notifyObservers();
    */
  }

  @Autowired
  public void setRegReqSender(RegReqValSender regReqSender)
  {
    this.regReqSender = regReqSender;
  }

}
