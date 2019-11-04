//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.09.26 at 05:20:06 PM BST 
//


package com.thecrunchycorner.peacockmybank.regvalwsmodel;

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
 *         &lt;element name="RequestID" type="{http://www.thecrunchycorner.com/peacockInt/schemas}MsgIDType"/>
 *         &lt;element name="RequestDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="RequestAction" type="{http://www.thecrunchycorner.com/peacockInt/schemas}RequestActionType"/>
 *         &lt;element name="NewCustDetails" type="{http://www.thecrunchycorner.com/peacockInt/schemas}CustomerType"/>
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
    "requestID",
    "requestDateTime",
    "requestAction",
    "newCustDetails"
})
@XmlRootElement(name = "CustRegValidationRequest", namespace = "http://www.thecrunchycorner.com/peacockInt/schemas")
public class CustRegValidationRequest {

    @XmlElement(name = "RequestID", namespace = "http://www.thecrunchycorner.com/peacockInt/schemas", required = true)
    protected String requestID;
    @XmlElement(name = "RequestDateTime", namespace = "http://www.thecrunchycorner.com/peacockInt/schemas", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestDateTime;
    @XmlElement(name = "RequestAction", namespace = "http://www.thecrunchycorner.com/peacockInt/schemas", required = true)
    protected RequestActionType requestAction;
    @XmlElement(name = "NewCustDetails", namespace = "http://www.thecrunchycorner.com/peacockInt/schemas", required = true)
    protected CustomerType newCustDetails;

    /**
     * Gets the value of the requestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestID() {
        return requestID;
    }

    /**
     * Sets the value of the requestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestID(String value) {
        this.requestID = value;
    }

    /**
     * Gets the value of the requestDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequestDateTime() {
        return requestDateTime;
    }

    /**
     * Sets the value of the requestDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequestDateTime(XMLGregorianCalendar value) {
        this.requestDateTime = value;
    }

    /**
     * Gets the value of the requestAction property.
     * 
     * @return
     *     possible object is
     *     {@link RequestActionType }
     *     
     */
    public RequestActionType getRequestAction() {
        return requestAction;
    }

    /**
     * Sets the value of the requestAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestActionType }
     *     
     */
    public void setRequestAction(RequestActionType value) {
        this.requestAction = value;
    }

    /**
     * Gets the value of the newCustDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerType }
     *     
     */
    public CustomerType getNewCustDetails() {
        return newCustDetails;
    }

    /**
     * Sets the value of the newCustDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerType }
     *     
     */
    public void setNewCustDetails(CustomerType value) {
        this.newCustDetails = value;
    }

}
