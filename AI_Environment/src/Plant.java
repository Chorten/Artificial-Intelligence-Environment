/**
 * @(#)Plant.java
 *
 *  This is the Plant class which creates plants in random locations at random times.
 *
 * @author Chorten Dolma
 * @version 1.00 10/7/2015
 */
import java.awt.*;
import javax.swing.*;

public class Plant{

    private ImageIcon img;
    private JLabel label;

    Environment myenv = Environment.getInstance();

    public Plant(){new Point();}

    public Plant(int x, int y)
    {
        new Point(x, y);
    }

    public ImageIcon loadImg(){         //Use plant as its image icon.
        img = new ImageIcon(getClass().getResource("Plant.jpeg"));
        return img;
    }

    public static boolean P_Growth(int clock) {        // Randomly check for a time to grow plants
        int rand =(int)(Math.random()*3+3);
        return clock % rand == 0;
    }

    public void createPlant(int num)    //Create num number of plants
    {
        for (int i = 0; i < num; i++)
        {
            int xL = (int) (Math.random() * myenv.getX());
            int yL = (int) (Math.random() * myenv.getY());
            if(myenv.map[xL][yL]==null) {
                myenv.map[xL][yL] = new Plant(xL, yL);
            }
            else num++;
        }
    }

    public JLabel getLabel(){
        label = new JLabel(loadImg());
        return label;
    }

}