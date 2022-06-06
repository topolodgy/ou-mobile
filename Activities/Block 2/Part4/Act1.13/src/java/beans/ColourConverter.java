/**
 * Custom JSF converter class to allow storing colour information in
 * our WidgetFormBean as a java.awt.Color object.
 */
package beans;

import java.awt.Color;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author tm352
 */
@FacesConverter(value = "colourConverter")
public class ColourConverter implements Converter
{
    /**
     * This method implements the conversion from the UIComponent string value to an Object of our
     * choosing
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        System.out.println("ColourConverter getasOBJECT received value " + value);
        return Color.decode(value);
    }

    /**
     * This method implements the conversion from an Object type of our choosing to the UIComponent
     * colour requirement for a String
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        System.out.println("ColourConverter getasSTRING received object " + value);
        return (String) value;
    }

}
