package com.thecrunchycorner.peacockint.regprocessor;
//TODO move onto peacocklib
import java.util.concurrent.*;

import org.apache.log4j.Logger;

public class WorkerThreadPool
{
  static Logger logger = Logger.getLogger(RegReqValSender.class.getName());
  
  private ExecutorService execSvc;
  private Future<String> task, taskToCheck;
  private int poolSize;
  private boolean poolRunning;
  private int futureTaskPollTimer;
  private BlockingQueue<Future<String>> futureQ;
  
  private WorkerThreadPool()
  {
    poolRunning=false;
  }

  
  
  public void startThread(Callable<String> job)
  {
    if (poolRunning == false) {
      futureQ = new LinkedTransferQueue<Future<String>>();
      execSvc = Executors.newFixedThreadPool(poolSize);
      
      Runnable taskLoop = new Runnable()
      {
        public void run()
        {
          try {
            for (;;) {
              if (futureQ.isEmpty() == false) {
                taskToCheck = (Future<String>) futureQ.peek();
                if (taskToCheck.isDone()) {
                  futureQ.remove();
                  taskToCheck.get();
                }
              }
              try { Thread.sleep(futureTaskPollTimer); } catch (Exception e) {}
            }
          } catch (Exception e) {  //real exception is wrapped up in future.  future task takes care of displaying it
          }
        }
      };
    
      new Thread(taskLoop).start();
      poolRunning = true;
    }
    
    task = execSvc.submit(job);
    futureQ.add(task);
  }
  
  
  
  public void setPoolSize(int poolSize)
  {
    this.poolSize = poolSize;
  }


  public void setFutureTaskPollTimer(int futureTaskPollTimer)
  {
    this.futureTaskPollTimer = futureTaskPollTimer;
  }
  
}
