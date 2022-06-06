
package echosoapclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for echo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="echo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shout" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "echo", propOrder = {
    "shout"
})
public class Echo_Type {

    protected String shout;

    /**
     * Gets the value of the shout property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShout() {
        return shout;
    }

    /**
     * Sets the value of the shout property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShout(String value) {
        this.shout = value;
    }

}
