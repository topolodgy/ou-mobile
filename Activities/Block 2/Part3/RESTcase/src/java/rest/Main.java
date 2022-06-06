/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

/**
 *
 * @author simolodg
 */
public class Main {
       public static void main(String[] args)
   {
      RESTcaseClient c = new RESTcaseClient(); 
      String about = c.about();

    System.out.println("About returned " + about);

    String upper = c.upper("my lowercase string");

    System.out.println("Upper produced " + upper);

    String lower = c.lower("TEST OF LOWERCASE METHOD");

    System.out.println("Lower produced " + lower);
   }
}
