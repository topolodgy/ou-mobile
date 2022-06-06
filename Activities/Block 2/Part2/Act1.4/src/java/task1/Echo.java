/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task1;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author simolodg
 */
@WebService(serviceName = "Echo")
public class Echo {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "echo")
    public String echo(@WebParam(name = "shout") String shout) {
        //TODO write your implementation code here:
        String response = shout + "\n";
        for (int i = 1; i < shout.length(); i++) {
            response += shout.substring(i) + "\n";
        }
        return response;
    }

}
