/**
 * Created by Chorten Dolma on 10/15/15.
 *
 * This Carnivore class creates carnivorous animals.
 * It eats Herbivores and randomly moves every cycle.
 * It dies if it has no energy left or becomes older than 10.
 * Its functions are similar to Herbvore class except for eating herbivore as its food.
 */
import java.awt.*;
import javax.swing.*;

public class Carnivore extends Animal implements Runnable{

    protected ImageIcon img;
    private JLabel label;
    private boolean running = true;

    public Carnivore(){ super();}

    public Carnivore(int x, int y)
    {
        super(x, y);
    }

    public Point getLocation(){ return super.getLocation();}

    public void createCarnivore(int num, Environment myenv){       //Create num number of Carnivores
        for (int i = 0; i < num; i++)
        {
            int xL = (int) (Math.random() * myenv.getX());
            int yL = (int) (Math.random() * myenv.getY());
            if(myenv.map[xL][yL]==null) {
                myenv.map[xL][yL] = new Carnivore(xL, yL);
                new Thread((Carnivore) myenv.map[xL][yL]).start();
            }
            else num++;
        }
    }

    public void createCarnivore(Environment myenv)       //Create Carnivore at location x,y
    {
        myenv.map[getLocation().x][getLocation().y]=this;
        new Thread((Carnivore)myenv.map[getLocation().x][getLocation().y]).start();
    }

    public void run()
    {
        Environment myenv= Environment.getInstance();

        while(running){
            try {
                Thread.sleep((long) (1000));  // Moves every cycle
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
            if(this.isDead()){
                System.out.println("Carnivore dead");
                myenv.removeObject(this);
                running = false;
            }
            else {
                this.increaseAge();
                if(this.isBorn()) addChild(this, myenv);
                else if(this.isFull()) {
                    this.decreaseEnergy();
                    continue;
                }
                else if (findFood(myenv)) this.increaseEnergy();
                else {
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
                this.getLocation().setLocation(temp.x, temp.y);
                myenv.addObject(this);
            }
        }
    }

    public ImageIcon loadImg() {
        int random = (int) (Math.random() * 3);
        switch (random) {
            case 0:
                img = new ImageIcon(getClass().getResource("Lion.jpeg"));
                return img;
            case 1:
                img = new ImageIcon(getClass().getResource("tiger.jpeg"));
                return img;
            case 2:
                img = new ImageIcon(getClass().getResource("Wolf.jpeg"));
                return img;
        }
        return img;
    }

    public ImageIcon getImg(){
        if(img==null)
            img= loadImg();
        return img;
    }

    public JLabel getLabel(){
        if(label == null)
            label = new JLabel(getImg());
        label.setToolTipText("Energy: " + getEnergy() + " Age: " + getAge());
        return label;
    }

    public Point checkGrid(Environment myenv){       //Check the quadrants to find Herbivore in the grid
        for (int i = (int)getLocation().getX(); i < myenv.getX() ; i++) {
            for (int j = (int) getLocation().getY(); j >= 0; j--) {
                if (myenv.map[i][j] instanceof Herbivore) return new Point(i, j);
            }
        }
        for (int i = (int)getLocation().getX(); i >=0 ; i--) {
            for (int j = (int)getLocation().getY(); j >=0; j--) {
                if(myenv.map[i][j] instanceof Herbivore) return new Point(i,j);
            }
        }
        for (int i = (int)getLocation().getX(); i >=0 ; i--) {
            for (int j = (int)getLocation().getY(); j < myenv.getY(); j++) {
                if(myenv.map[i][j] instanceof Herbivore) return new Point(i,j);
            }
        }
        for (int i = (int)getLocation().getX(); i < myenv.getX() ; i++) {
            for (int j = (int)getLocation().getY(); j < myenv.getY(); j++) {
                if(myenv.map[i][j] instanceof Herbivore) return new Point(i,j);
            }
        }
        return null;
    }


}
