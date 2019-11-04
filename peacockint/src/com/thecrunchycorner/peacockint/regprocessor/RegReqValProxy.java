package com.thecrunchycorner.peacockint.regprocessor;

import com.thecrunchycorner.peacockint.regvalwsmodel.*;
import com.thecrunchycorner.peacockint.updatecustdbmodel.*;

import java.io.*;
import java.security.*;

import javax.net.ssl.SSLContext;
import javax.xml.datatype.*;

import org.apache.http.conn.ssl.*;
import org.apache.http.impl.client.*;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;


@Component
public class RegReqValProxy extends WebServiceGatewaySupport
{
  static Logger logger = Logger.getLogger(RegReqValProxy.class.getName());
  
  //TODO make sure everything is moved via deep copies not shallow ones, using copy constructors or clonable (use the former i think)
  public RegReqValProxy()
  {
  }

  public String sendRegValidationReq(CustomerModel dbCust, String msgId, String URI) throws DatatypeConfigurationException, KeyStoreException
  {
    logger.info("about to send customer validation request");
    
    CustRegValidationResponse response = null;
    
    try {
      KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
      KeyStore keyStore  = KeyStore.getInstance(KeyStore.getDefaultType());
      
      FileInputStream store = new FileInputStream(new File(System.getenv("CATALINA_HOME"), "/conf/stores/intToSimi/client_cacerts.jks"));
      try {
        trustStore.load(store, "letmein".toCharArray());
      } catch (Exception e) {
        e.printStackTrace();
      }finally {
        store.close();
      }

      store = new FileInputStream(new File(System.getenv("CATALINA_HOME"), "/conf/stores/intToSimi/client_keystore.jks"));
      try {
        keyStore.load(store, "letmein".toCharArray());
      } catch (Exception e) {
        e.printStackTrace();
      }finally {
        store.close();
      }

      
      SSLContext sslcontext = SSLContexts.custom()
          .loadTrustMaterial(trustStore)
          .loadKeyMaterial(keyStore, "letmein".toCharArray())
          .build();

      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);


      CloseableHttpClient httpClient;
      synchronized(this) {
        httpClient = HttpClients.custom()
            .addInterceptorFirst(new RemoveSurplusSpringHeaderInterceptor())
            .setSSLSocketFactory(sslsf)
            .build();
      }
      

      HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
      messageSender.setHttpClient(httpClient);

      WebServiceTemplate sslWebServiceTemplate = getWebServiceTemplate();
      sslWebServiceTemplate.setMessageSender(messageSender);


      
      CustRegValidationRequest request = new CustRegValidationRequest();
      request.setRequestID(msgId);
      request.setRequestDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar());
      request.setRequestAction(RequestActionType.VALIDATION);
      
      ProcessorCustConverter converter = new ProcessorCustConverter();
      CustomerType wsCust = converter.dbToWs(dbCust);
      request.setNewCustDetails(wsCust);
      

      
      response = (CustRegValidationResponse) sslWebServiceTemplate.marshalSendAndReceive(URI, request);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response.getRequestResponse().toString();
  }

}