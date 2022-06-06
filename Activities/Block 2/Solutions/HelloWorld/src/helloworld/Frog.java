package helloworld;

/**
 * The class Frog illustrates a simple Java class
 * @author tm352
 */
public class Frog
{
   private String name;

   public Frog(String name)
   {
      this.name = name;
   }

   @Override
   public String toString()
   {
      return "Frog{" + "name=" + name + '}';
   }

}
