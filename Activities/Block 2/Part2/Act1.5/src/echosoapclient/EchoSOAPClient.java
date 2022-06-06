/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echosoapclient;

/**
 *
 * @author TM352
 */
public class EchoSOAPClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String shout = "hello web service";
        String result = echo(shout);
        System.out.println("Result = " + result);
    }

    private static String echo(java.lang.String shout) {
        echosoapclient.Echo_Service service = new echosoapclient.Echo_Service();
        echosoapclient.Echo port = service.getEchoPort();
        return port.echo(shout);
    }

}
