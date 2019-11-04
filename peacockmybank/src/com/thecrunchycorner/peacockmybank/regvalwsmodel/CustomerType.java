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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustomerId" type="{http://www.thecrunchycorner.com/peacockInt/schemas}CustomerIdType"/>
 *         &lt;element name="CustomerOrg" type="{http://www.thecrunchycorner.com/peacockInt/schemas}CustomerOrgType"/>
 *         &lt;element name="CustomerStatus" type="{http://www.thecrunchycorner.com/peacockInt/schemas}CustomerStatusType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerType", namespace = "http://www.thecrunchycorner.com/peacockInt/schemas", propOrder = {
    "customerId",
    "customerOrg",
    "customerStatus"
})
public class CustomerType {

    @XmlElement(name = "CustomerId", namespace = "http://www.thecrunchycorner.com/peacockInt/schemas", required = true)
    protected String customerId;
    @XmlElement(name = "CustomerOrg", namespace = "http://www.thecrunchycorner.com/peacockInt/schemas", required = true)
    protected String customerOrg;
    @XmlElement(name = "CustomerStatus", namespace = "http://www.thecrunchycorner.com/peacockInt/schemas", required = true)
    protected CustomerStatusType customerStatus;

    /**
     * Gets the value of the customerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Sets the value of the customerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerId(String value) {
        this.customerId = value;
    }

    /**
     * Gets the value of the customerOrg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerOrg() {
        return customerOrg;
    }

    /**
     * Sets the value of the customerOrg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerOrg(String value) {
        this.customerOrg = value;
    }

    /**
     * Gets the value of the customerStatus property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerStatusType }
     *     
     */
    public CustomerStatusType getCustomerStatus() {
        return customerStatus;
    }

    /**
     * Sets the value of the customerStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerStatusType }
     *     
     */
    public void setCustomerStatus(CustomerStatusType value) {
        this.customerStatus = value;
    }

}
