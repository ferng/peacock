//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.09.26 at 05:20:06 PM BST 
//


package com.thecrunchycorner.peacockint.regvalwsmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResponseID" type="{http://www.thecrunchycorner.com/peacockInt/schemas}MsgIDType"/>
 *         &lt;element name="ResponseDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="RequestResponse" type="{http://www.thecrunchycorner.com/peacockInt/schemas}RequestResponseType"/>
 *         &lt;element name="OriginalRequestID" type="{http://www.thecrunchycorner.com/peacockInt/schemas}MsgIDType"/>
 *         &lt;element name="OriginalRequestAction" type="{http://www.thecrunchycorner.com/peacockInt/schemas}RequestActionType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "responseID",
    "responseDateTime",
    "requestResponse",
    "originalRequestID",
    "originalRequestAction"
})
@XmlRootElement(name = "CustRegValidationResponse", namespace = "http://www.thecrunchycorner.com/peacockInt/schemas")
public class CustRegValidationResponse {

    @XmlElement(name = "ResponseID", namespace = "http://www.thecrunchycorner.com/peacockInt/schemas", required = true)
    protected String responseID;
    @XmlElement(name = "ResponseDateTime", namespace = "http://www.thecrunchycorner.com/peacockInt/schemas", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar responseDateTime;
    @XmlElement(name = "RequestResponse", namespace = "http://www.thecrunchycorner.com/peacockInt/schemas", required = true)
    protected RequestResponseType requestResponse;
    @XmlElement(name = "OriginalRequestID", namespace = "http://www.thecrunchycorner.com/peacockInt/schemas", required = true)
    protected String originalRequestID;
    @XmlElement(name = "OriginalRequestAction", namespace = "http://www.thecrunchycorner.com/peacockInt/schemas", required = true)
    protected RequestActionType originalRequestAction;

    /**
     * Gets the value of the responseID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseID() {
        return responseID;
    }

    /**
     * Sets the value of the responseID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseID(String value) {
        this.responseID = value;
    }

    /**
     * Gets the value of the responseDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getResponseDateTime() {
        return responseDateTime;
    }

    /**
     * Sets the value of the responseDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setResponseDateTime(XMLGregorianCalendar value) {
        this.responseDateTime = value;
    }

    /**
     * Gets the value of the requestResponse property.
     * 
     * @return
     *     possible object is
     *     {@link RequestResponseType }
     *     
     */
    public RequestResponseType getRequestResponse() {
        return requestResponse;
    }

    /**
     * Sets the value of the requestResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestResponseType }
     *     
     */
    public void setRequestResponse(RequestResponseType value) {
        this.requestResponse = value;
    }

    /**
     * Gets the value of the originalRequestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalRequestID() {
        return originalRequestID;
    }

    /**
     * Sets the value of the originalRequestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalRequestID(String value) {
        this.originalRequestID = value;
    }

    /**
     * Gets the value of the originalRequestAction property.
     * 
     * @return
     *     possible object is
     *     {@link RequestActionType }
     *     
     */
    public RequestActionType getOriginalRequestAction() {
        return originalRequestAction;
    }

    /**
     * Sets the value of the originalRequestAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestActionType }
     *     
     */
    public void setOriginalRequestAction(RequestActionType value) {
        this.originalRequestAction = value;
    }

}
