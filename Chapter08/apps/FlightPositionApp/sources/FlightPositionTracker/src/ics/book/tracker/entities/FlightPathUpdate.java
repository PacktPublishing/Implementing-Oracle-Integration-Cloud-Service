
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
 *         &lt;element name="time" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="longitude" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="latitude" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "pathID", "time", "longitude", "latitude" })
@XmlRootElement(name = "FlightPathUpdate")
public class FlightPathUpdate {

    @XmlElement(required = true)
    protected String pathID;
    @XmlElement(required = true)
    protected String time;
    protected float longitude;
    protected float latitude;

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

    /**
     * Gets the value of the time property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTime(String value) {
        this.time = value;
    }

    /**
     * Gets the value of the longitude property.
     *
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the longitude property.
     *
     */
    public void setLongitude(float value) {
        this.longitude = value;
    }

    /**
     * Gets the value of the latitude property.
     *
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     *
     */
    public void setLatitude(float value) {
        this.latitude = value;
    }

}
