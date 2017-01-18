
package ics.book.tracker.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pathID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "pathID" })
@XmlRootElement(name = "ProcessedResult")
public class ProcessedResult {

    @XmlElement(required = true)
    protected String pathID;

    /**
     * Gets the value of the pathID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPathID() {
        return pathID;
    }

    /**
     * Sets the value of the pathID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPathID(String value) {
        this.pathID = value;
    }

}
