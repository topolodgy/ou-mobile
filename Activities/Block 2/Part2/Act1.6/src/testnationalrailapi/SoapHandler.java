package testnationalrailapi;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * Handler for SOAP messages, so that we can set the outgoing header and envelope correctly. Methods
 * in this class are automatically invoked if an instance of this class becomes part of the handler
 * chain for a binding provider.
 *
 * @author TM352 module team August 2019
 */
public class SoapHandler implements SOAPHandler<SOAPMessageContext>
{
    //You need to replace the token with your personal access token
    static final String TOKEN = "ed1a3843-f936-4cae-b6a7-c03b403954a7";

    @Override
    /**
     * SoapHandler interface extra requirement (in addition to Handler methods)
     */
    public Set<QName> getHeaders()
    {
        System.out.println("Client getHeaders() was called ");
        return null;
    }

    @Override
    /**
     * Allows us to perform any extra processing of inbound and outbound messages we require
     */
    public boolean handleMessage(SOAPMessageContext context)
    {
        SOAPMessage soapMsg = context.getMessage();

        System.out.println("Client handleMessage() was called\n");

        Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (isOutbound)
        {
            processOutbound(soapMsg);
        }
        else //it's a response
        {
            processInbound(soapMsg);
        }

        //continue processing
        return true;
    }

    @Override
    /**
     * Called in the event of a fault
     */
    public boolean handleFault(SOAPMessageContext context)
    {
        System.out.println("\nClient handleFault() was called. Stopping. \n");

        //false => stop processing
        return false;
    }

    @Override
    /**
     * Called at the conclusion of a message exchange pattern prior to a message being dispatched
     */
    public void close(MessageContext context)
    {
        System.out.println("Client close() called\n");
    }

    /**
     * Print out the elements in the soap header. Just a utility.
     *
     * @param soapHeader a header whose elements we can display
     */
    private void showHeaderElements(SOAPHeader soapHeader)
    {
        Iterator childElements = soapHeader.getChildElements();

        if (!childElements.hasNext())
        {
            System.out.println("No header elements yet");
        }
        else
        {
            System.out.println("Header elements now...");
            while (childElements.hasNext())
            {
                System.out.println(childElements.next());
            }
        }
    }

    /**
     * Actions to be taken for every outgoing message to modify the headers so that we can include
     * our personal access token. This method modifies the envelope of the outgoing SOAP message and
     * the header elements to include our credentials in the format expected by the server.
     *
     * @param soapMsg the message we are altering
     */
    private void processOutbound(SOAPMessage soapMsg)
    {
        System.out.println("Outbound SOAP message");
        try
        {
            //First set the envelope to include extra xml namespaces
            SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();

            //we add two namespaces to the envelope
            soapEnv.addNamespaceDeclaration("typ", "http://thalesgroup.com/RTTI/2013-11-28/Token/types");
            soapEnv.addNamespaceDeclaration("ldb", "http://thalesgroup.com/RTTI/2017-10-01/ldb/");

            //Then set the header to include our credentials, correctly wrapped up
            SOAPHeader soapHeader = soapEnv.getHeader();

            //if there's no header, add one
            if (soapHeader == null)
            {
                System.out.println("Adding a soap header");
                soapHeader = soapEnv.addHeader();
            }
            else
            {
                //just for information - there's nothing there in our example.
                showHeaderElements(soapHeader);
            }

            //We need two QNames - qualified names. They are names that are associated with namespaces.
            QName tokenName = new QName("http://thalesgroup.com/RTTI/2013-11-28/Token/types", "AccessToken", "typ");
            QName tokenValue = new QName("http://thalesgroup.com/RTTI/2013-11-28/Token/types", "TokenValue", "typ");

            //Add the soap header element for the TokenName
            //and get the resulting element so that we can create a child for it.
            SOAPHeaderElement soapHeaderElement = soapHeader.addHeaderElement(tokenName);

            //Now add a child element to the TokenName, called TokenValue
            SOAPElement tokenValueElement = soapHeaderElement.addChildElement(tokenValue);

            //Finally, set the contents of the TokenValue using our personal token
            tokenValueElement.addTextNode(TOKEN);

            //Now 'commit' the changes to the message
            soapMsg.saveChanges();

            //Show the outgoing soap message in all its glory, so we can see what
            //we're sending to the national rail server
            System.out.println("\nOur message is...");
            soapMsg.writeTo(System.out);
            System.out.println("\n");
        }
        catch (SOAPException | IOException e)
        {
            System.err.println(e);
        }
    }

    /**
     * This method will be called if we receive a response from the server. We could do other things
     * but we just use it to show the raw message we received from the server.
     *
     * @param soapMsg The message received from the server
     */
    private void processInbound(SOAPMessage soapMsg)
    {
        System.out.println("Incoming SOAP message!\n");

        try
        {
            //Show the raw SOAP message we received back
            soapMsg.writeTo(System.out);
        }
        catch (SOAPException | IOException ex)
        {
            System.out.println("Failed to show soap response");
        }

        System.out.println("\n");
    }

    /**
     * This method adds an instance of this class as a handler to a BindingProvider
     * This will not compile until the client-side web service code is generated.
     *
     * @param port the port we're using to access the web service. It will provide the
     * BindingProvider that we will attach a handler to.
     */
    public static void setHandler(com.thalesgroup.rtti._2017_10_01.ldb.LDBServiceSoap port)
    {
        BindingProvider bp = (BindingProvider) port;
        // Get the handler chain
        List<Handler> handlerChain = bp.getBinding().getHandlerChain();

        handlerChain.add(new SoapHandler()); //add an instance of this class as a handler

        // Re-set the handler chain (needed because getHandlerChain() returns
        // a copy of the handlers list)
        bp.getBinding().setHandlerChain(handlerChain);
    }
}
