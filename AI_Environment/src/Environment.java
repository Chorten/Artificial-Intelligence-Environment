/**
 * @(#)Environment.java
 *
 * Created by Chorten Dolma on 10/23/2015
 * Special thanks to Professor Nooreddin
 * This Environment class keeps track of the animals and plants. It is the
 * platform for the animals to interact.
 */

import javax.swing.*;
import java.awt.*;

public class Environment extends JFrame
{
    private final int x;
    private final int y;
    protected Object[][] map;
    private JFrame frame;

    private static Environment instance;

    private Environment(int x, int y)       //Create Environment using JFrame
    {
        frame = new JFrame("Environment");
        frame.setLayout(new GridLayout(x+1,y));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(700, 700));
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setVisible(true);

        this.x = x;
        this.y = y;
        map = new Object[x][y];

    }

    public int getX() { return x; }

    public int getY() { return y; }

    public static Environment getInstance()
    {
        if( instance == null)
            return null;
        return instance;
    }

    public static Environment createNewInstance(int x, int y)
    {
        if( instance != null)
            return null;
        instance = new Environment(x , y);
        return instance;
    }

    public void start()
    {
        initialize();
    }

    private void initialize()
    {
        Plant plantlist = new Plant();
        plantlist.createPlant(50);
        Herbivore HerbList=new Herbivore();
        HerbList.createHerbivore(30, getInstance());
        Carnivore CarnList = new Carnivore();
        CarnList.createCarnivore(25, getInstance());
    }

    public void removeObject(Object obj) {          //Remove object from environment
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if(obj instanceof Herbivore) {
                    if (((Herbivore) obj).getLocation().getX() == i && ((Herbivore) obj).getLocation().getY() == j) {
                        map[i][j] = null;
                    }
                }
                if(obj instanceof Carnivore) {
                    if (((Carnivore) obj).getLocation().getX() == i && ((Carnivore) obj).getLocation().getY() == j) {
                        map[i][j] = null;
                    }
                }
            }
        }
    }

    public void addObject(Object obj) {         //Add object to the environment
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (obj instanceof Herbivore) {
                    if (((Herbivore) obj).getLocation().getX() == i && ((Herbivore) obj).getLocation().getY() == j)
                        map[i][j] = obj;
                }
                if (obj instanceof Carnivore) {
                    if (((Carnivore) obj).getLocation().getX() == i && ((Carnivore) obj).getLocation().getY() == j)
                        map[i][j] = obj;
                }
            }
        }
    }

    public boolean checkEmpty(int xl, int yl){      // Check if location is empty
        if(xl >=0 && xl<x && yl>=0 && yl < y) {
            if (map[xl][yl] == null) return true;
        }
        return false;
    }

    public void print() {       //Print environment
        frame.getContentPane().removeAll();         //Clear screen
        ImageIcon img = new ImageIcon(getClass().getResource("Grass.jpeg"));
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (map[i][j] instanceof Plant)
                    frame.add(((Plant)map[i][j]).getLabel());
                else if (map[i][j] instanceof Herbivore)
                    frame.add(((Herbivore)map[i][j]).getLabel());
                else if (map[i][j] instanceof Carnivore)
                    frame.add(((Carnivore)map[i][j]).getLabel());
                else if (map[i][j] == null){
                    JLabel label = new JLabel(img);
                    frame.add(label);
                }
            }
        }
        frame.validate();   //Validate frame
        frame.repaint();    //Update frame
    }


    //Check if all animals are dead in the grid
    public boolean checkEmptyGrid(){
        boolean check = false;
        outerloop:
        for(int i =0; i<x; i++){
            for (int j=0; j <y; j++){
                if(map[i][j] ==null || map[i][j] instanceof Plant) continue;
                else{
                    check = true;
                    break outerloop;
                }
            }
        }

        // if all animals are dead then ask tø exit
        if(check == false){
            int confirmed = JOptionPane.showConfirmDialog(null,
                    " All Living Beings Are Dead! Do you want to exit? ", "Exit Program Message Box",
                    JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                frame.dispose();
                return true;
            }
        }
        return false;
    }


}