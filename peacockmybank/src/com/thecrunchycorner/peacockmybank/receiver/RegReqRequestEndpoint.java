package com.thecrunchycorner.peacockmybank.receiver;

import com.thecrunchycorner.peacockmybank.regvalwsmodel.*;

import java.net.URL;
import java.util.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.apache.log4j.helpers.Loader;
import org.apache.log4j.xml.DOMConfigurator;

import org.springframework.ws.server.endpoint.annotation.*;
import org.springframework.ws.soap.SoapHeader;


@Endpoint
public class RegReqRequestEndpoint
{
  static Logger logger = Logger.getLogger(RegReqRequestEndpoint.class.getName());
  private static final String NAMESPACE_URI = "http://www.thecrunchycorner.com/peacockInt/schemas";
  
  @ResponsePayload
  @PayloadRoot(namespace=NAMESPACE_URI, localPart="CustRegValidationRequest")
  public CustRegValidationResponse handleRegReqValRequest(@RequestPayload final CustRegValidationRequest valRequest, SoapHeader header) throws Exception
  {
    NDC.push("SIMI - " + valRequest.getNewCustDetails().getCustomerId());
    
    
    
    if (valRequest.getNewCustDetails().getCustomerId().contentEquals("RESETLOGGER")) {
      URL url = Loader.getResource("log4j.xml");
      LogManager.resetConfiguration();
      DOMConfigurator.configure(url);
    }
    
    
    logger.info("Received request " + valRequest.getRequestID());
    
    CustRegValidationResponse valResponse = new CustRegValidationResponse();
    
    valResponse.setResponseID(UUID.randomUUID().toString());
    valResponse.setResponseDateTime(valRequest.getRequestDateTime());
    valResponse.setRequestResponse(RequestResponseType.RECEIVED);
    valResponse.setOriginalRequestID(valRequest.getRequestID());
    valResponse.setOriginalRequestAction(valRequest.getRequestAction());

    logger.info("Sending response" + valRequest.getRequestID());
    return valResponse;
  }
}