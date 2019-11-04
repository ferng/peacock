package com.thecrunchycorner.peacockint.regprocessor;

public interface JmsListener
{
  public void regObs(JmsListenerObserver o);
  public void dropObs(JmsListenerObserver o);
  public void notifyObservers();

}
