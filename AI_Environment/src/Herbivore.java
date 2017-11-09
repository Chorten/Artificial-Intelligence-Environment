/**
 * Created by Chorten Dolma on 10/15/15.
 *
 * This Herbivore class creates Herbivorous animals.
 * It eats plants and randomly moves every 2 cycles.
 * It dies at the last cycle or if it has no energy left or its age exceeds 10.
 */

import java.awt.*;
import javax.swing.*;

public class Herbivore extends Animal implements Runnable {

    protected ImageIcon img;
    private JLabel label;
    private boolean running = true;

    public Herbivore(int x, int y) {
        super(x, y);
    }

    public Herbivore(){
        super();
    }

    public Point getLocation() {
        return super.getLocation();
    }

    public void createHerbivore(int num,Environment myenv)       //Create num number of Herbivores
    {
        for (int i = 0; i < num; i++)
        {
            int xL = (int) (Math.random() * myenv.getX());
            int yL = (int) (Math.random() * myenv.getY());
            if(myenv.map[xL][yL]==null) {
                myenv.map[xL][yL] = new Herbivore(xL, yL);
                new Thread((Herbivore) myenv.map[xL][yL]).start();
            }
            else num++;
        }
    }

    public void createHerbivore(Environment myenv)       //Create Herbivore at location x,y
    {
        myenv.map[getLocation().x][getLocation().y]= this;
        new Thread((Herbivore)myenv.map[getLocation().x][getLocation().y]).start();
    }

    public void run() {
        Environment myenv = Environment.getInstance();
        while(running){
            try {
                Thread.sleep((long) (2000));    //moves every 2 cycle
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
            if (this.isDead()) {            //If it is dead, remove object from environment.
                myenv.removeObject(this);
                running = false;
            } else {
                this.increaseAge();
                if(this.isBorn()){
                    if(this.parentIsAlive()) addChild(this, myenv);
                }
                else if (this.isFull()){
                    this.decreaseEnergy();
                    continue;          //If full, then don't eat for one cycle.
                }
                else if (findFood(myenv)){
                    showtext_food(getLocation().x, getLocation().y);
                    this.increaseEnergy();    //If food is found, increase energy.
                }
                else {          //Else, decrease energy and randomly move to a place.
                    this.decreaseEnergy();
                    myenv.removeObject(this);
                    MoveTo(myenv);
                }
            }
        }
    }

    public void Move(Environment myenv){
        this.randomMove(myenv);
        if(myenv.checkEmpty(getLocation().x, getLocation().y)) myenv.addObject(this);
        else Move(myenv);
    }

    public void MoveTo(Environment myenv){
        if(checkGrid(myenv)==null) Move(myenv);
        else {
            Point temp = new Point(getLocation().x, getLocation().y);
            moveToward(checkGrid(myenv));
            if (myenv.checkEmpty(getLocation().x, getLocation().y))
                myenv.addObject(this);
            else {
                getLocation().setLocation(temp.x, temp.y);
                myenv.addObject(this);
            }
        }
    }

    public final ImageIcon loadImg() {
        int random = (int) (Math.random() * 3);
        switch (random) {
            case 0:
                img = new ImageIcon(getClass().getResource("Giraffe.jpeg"));
                return img;
            case 1:
                img = new ImageIcon(getClass().getResource("Zebra.jpeg"));
                return img;
            case 2:
                img = new ImageIcon(getClass().getResource("Elephant.jpeg"));
                return img;
        }
        return img;
    }

    public final ImageIcon getImg(){
        if(img==null)
            img=loadImg();
        return img;
    }

    public JLabel getLabel(){
        if(label==null)
            label = new JLabel(getImg());
        label.setToolTipText("Energy: " + getEnergy() + " Age: " + getAge());
        return label;
    }

    public Point checkGrid(Environment myenv){       //Check the quadrants for plant in the grid
        for (int i = (int)getLocation().getX(); i < myenv.getX() ; i++) {
            for (int j = (int) getLocation().getY(); j > 0; j--) {
                if (myenv.map[i][j] instanceof Plant) return new Point(i, j);
            }
        }
        for (int i = (int)getLocation().getX(); i >0 ; i--) {
            for (int j = (int)getLocation().getY(); j >0; j--) {
                if(myenv.map[i][j] instanceof Plant) return new Point(i,j);
            }
        }
        for (int i = (int)getLocation().getX(); i >0 ; i--) {
            for (int j = (int)getLocation().getY(); j < myenv.getY(); j++) {
                if(myenv.map[i][j] instanceof Plant) return new Point(i,j);
            }
        }
        for (int i = (int)getLocation().getX(); i < myenv.getX() ; i++) {
            for (int j = (int)getLocation().getY(); j < myenv.getY(); j++) {
                if(myenv.map[i][j] instanceof Plant) return new Point(i,j);
            }
        }
        return null;
    }

    public void showtext_food(int i, int j){
        JLabel text = new JLabel(" +1 ");
        text.setSize(50,50);
        text.setLocation(i, j);
        getLabel().setText("");
        getLabel().setText(Integer.toString(this.getEnergy()));
    }

}
