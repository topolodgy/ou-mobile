package beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.enterprise.inject.Model;

/**
 * A backing bean to maintain widget form data
 *
 * @author TM352
 */
@Model
public class WidgetFormBean implements Serializable
{
    private final String genericResponse = "Thank you for your order!";
    private String name;
    private String email;
    private String mobile;
    private String address;
    private boolean contactEmail;
    private boolean contactPost;
    private boolean contactPhone;
    private boolean contactOther;
    private int numWidgets;
    private int colourImportance;
    private String deliveryTime;
    private String deliveryDate;
    private Date dateOfBirth;
    private String htmlColour; //hexadecimal string representation of colour
    private String dateString;
    private final int UNIT_COST = 150;
    private int totalCost;

    /**
     * Initial values for some of the model data
     */
    public WidgetFormBean()
    {
        contactEmail = true;
        contactPost = true;
        contactPhone = true;
        contactOther = true;
        numWidgets = 1;
        colourImportance = 50;
        dateString = "";
        htmlColour = "#FF6600";
    }

    /**
     * Return the date string
     *
     * @return dateString value
     */
    public String getDateString()
    {
        return dateString;
    }

    /**
     * set the date string
     *
     * @param dateString
     */
    public void setDateString(String dateString)
    {
        this.dateString = dateString;
    }

    /**
     * Get the value of deliveryDate
     *
     * @return the value of deliveryDate
     */
    public String getDeliveryDate()
    {
        return deliveryDate;
    }

    /**
     * Set the value of deliveryDate
     *
     * @param deliveryDate new value of deliveryDate
     */
    public void setDeliveryDate(String deliveryDate)
    {
        this.deliveryDate = deliveryDate;
    }

    /**
     * get the widget colour as a string
     * @return hexadecimal string representation of widgetColour
     */
    public String getHtmlColour()
    {
        return htmlColour;
    }
    
    /**
     * Set the html Colour as a string
     * @param colour 
     */
    public void setHtmlColour(String colour)
    {        
        this.htmlColour = colour;
    }

    /**
     * Get the value of deliveryTime
     *
     * @return the value of deliveryTime
     */
    public String getDeliveryTime()
    {
        return deliveryTime;
    }

    /**
     * Set the value of deliveryTime
     *
     * @param deliveryTime new value of deliveryTime
     */
    public void setDeliveryTime(String deliveryTime)
    {
        this.deliveryTime = deliveryTime;
    }

    /**
     * Get the value of colourImportance
     *
     * @return the value of colourImportance
     */
    public int getColourImportance()
    {
        return colourImportance;
    }

    /**
     * Set the value of colourImportance
     *
     * @param colourImportance new value of colourImportance
     */
    public void setColourImportance(int colourImportance)
    {
        this.colourImportance = colourImportance;
    }

    /**
     * Get the value of numWidgets
     *
     * @return the value of numWidgets
     */
    public int getNumWidgets()
    {
        return numWidgets;
    }

    /**
     * Set the value of numWidgets
     *
     * @param numWidgets new value of numWidgets
     */
    public void setNumWidgets(int numWidgets)
    {
        this.numWidgets = numWidgets;
    }

    /**
     * Get the value of contactOther
     *
     * @return the value of contactOther
     */
    public boolean isContactOther()
    {
        return contactOther;
    }

    /**
     * Set the value of contactOther
     *
     * @param contactOther new value of contactOther
     */
    public void setContactOther(boolean contactOther)
    {
        this.contactOther = contactOther;
    }

    /**
     * Get the value of contactPhone
     *
     * @return the value of contactPhone
     */
    public boolean isContactPhone()
    {
        return contactPhone;
    }

    /**
     * Set the value of contactPhone
     *
     * @param contactPhone new value of contactPhone
     */
    public void setContactPhone(boolean contactPhone)
    {
        this.contactPhone = contactPhone;
    }

    /**
     * Get the value of contactPost
     *
     * @return the value of contactPost
     */
    public boolean isContactPost()
    {
        return contactPost;
    }

    /**
     * Set the value of contactPost
     *
     * @param contactPost new value of contactPost
     */
    public void setContactPost(boolean contactPost)
    {
        this.contactPost = contactPost;
    }

    /**
     * Get the value of emailOkay
     *
     * @return the value of emailOkay
     */
    public boolean isContactEmail()
    {
        return contactEmail;
    }

    /**
     * Set the value of emailOkay
     *
     * @param contactEmail new value of emailOkay
     */
    public void setContactEmail(boolean contactEmail)
    {
        this.contactEmail = contactEmail;
    }

    /**
     * Get the value of address
     *
     * @return the value of address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Set the value of address
     *
     * @param address new value of address
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * Get the value of dateOfBirth
     *
     * @return the value of dateOfBirth
     */
    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    /**
     * Set the value of dateOfBirth
     *
     * @param dateOfBirth new value of dateOfBirth
     */
    public void setDateOfBirth(Date dateOfBirth)
    {
        if (dateOfBirth != null)
        {
            System.out.println("date received " + dateOfBirth);

            DateFormat df = new SimpleDateFormat("E, MMM dd yyyy");

            this.dateString = df.format(dateOfBirth);

            this.dateOfBirth = dateOfBirth;
        }
    }

    /**
     * Get the value of mobile
     *
     * @return the value of mobile
     */
    public String getMobile()
    {
        return mobile;
    }

    /**
     * Set the value of mobile
     *
     * @param mobile new value of mobile
     */
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    /**
     * retrieve the generic response to the form data
     *
     * @return the value of genericResponse
     */
    public String getGenericResponse()
    {
        return genericResponse;
    }

    /**
     * Get the value of email
     *
     * @return the value of email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Set the value of email
     *
     * @param email new value of email
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * You would use this method perform overall form processing, e.g. to add up a total cost for
     * the order.
     *
     * @return The page to which we should be redirected after this method is executed, which is the
     * order confirmation page.
     */
    public String processForm()
    {
        setTotalCost(numWidgets * UNIT_COST);

        return "widgetOrderConfirm";
    }

    /**
     * Get the total cost of the order
     *
     * @return
     */
    public int getTotalCost()
    {
        return totalCost;
    }

    /**
     * set the total cost of the order.
     *
     * @param totalCost
     */
    private void setTotalCost(int totalCost)
    {
        this.totalCost = totalCost;
    }    
}