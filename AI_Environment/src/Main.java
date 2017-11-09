/**
 * @(#)Main.java
 *
 * This is the main class where the environment is initialized and initiated.
 * The environment is created as 5 by 5. And the clock is set as 5 so
 * the environment will be printed 5 times.
 *
 * @author Chorten Dolma
 * @version 1.00 12/27/2015
 */

public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        Environment environment;
        environment = Environment.createNewInstance(10,10);
        final int clock = 40;
        environment.start();
        for (int i = 0; i < (2*clock); i++) {
            environment.print();
            if(environment.checkEmptyGrid()) break;
            Thread.sleep((long) 1000);
        }
    }

}