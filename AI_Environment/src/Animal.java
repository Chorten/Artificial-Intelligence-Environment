/**
 * Created by Chorten Dolma on 10/20/15.
 *
 * This Animal class creates animals. It has initialized location.
 * It also has a start up energy of 5 and age of 0.
 * This class provides the randomMove method for both the Herbivore and Carnivore classes.
 *
 */

import java.awt.*;


public class Animal {
    private Point location;
    private int energy = 0;
    private int Age;

    final static int NORTH = 0;
    final static int SOUTH = 1;
    final static int EAST = 2;
    final static int WEST = 3;

    public  Animal(){
        location = new Point();
        energy = 3;
        Age = 0;
    }

    public Animal(int x, int y) {
        location = new Point(x, y);
        energy = 3;
        Age = 0;
    }

    public Point getLocation() {
        return location;
    }

    public void increaseEnergy() {energy = energy + 1; }

    public void decreaseEnergy() {
        energy = energy - 1;
    }

    public void increaseAge() {
        Age = Age + 1;
    }

    public int getEnergy() { return energy; }

    public int getAge(){ return Age;}

    public boolean isDead() {
        if(Environment.getInstance().map[location.x][location.y] ==null ||Age>=10 || energy < 1){
            return true;
        }
        return false;
    }

    public boolean isBorn() {
        if (energy >= 8 && Age >= 8) {
            energy -= 5;
            return true;
        }
        return false;
    }

    public boolean isFull() {
        if (energy >= 10) return true;
        return false;
    }

    public void addChild(Object obj, Environment myenv)        //Add children at Point location
    {
        int xl = (int)location.getX();
        int yl = (int)location.getY();
        outermost:
        for (int i = (xl-1); i <=(xl+1); i++) {
            for (int j = (yl - 1); j <= (yl + 1); j++) {
                if (myenv.checkEmpty(i, j)) {
                    if (myenv.map[xl][yl] instanceof Herbivore) {
                        Herbivore child = new Herbivore(i, j);
                        child.createHerbivore(myenv);
                        child.img = ((Herbivore) obj).getImg();
                        break outermost;
                    } else if (myenv.map[xl][yl] instanceof Carnivore) {
                        Carnivore child = new Carnivore();
                        child.createCarnivore(myenv);
                        child.img = ((Carnivore) obj).getImg();
                        break outermost;
                    }
                }
            }
        }
    }

    public void randomMove(Environment myEnvironment)
    {
        int maxX = myEnvironment.getX();
        int maxY = myEnvironment.getY();

        int x = (int) location.getX();
        int y = (int) location.getY();

        int randomMove = (int) (Math.random() * 4);

        switch (randomMove)
        {
            case NORTH:
                location.setLocation(x, y - 1);
                break;
            case SOUTH:
                location.setLocation(x, y + 1);
                break;
            case WEST:
                location.setLocation(x - 1, y);
                break;
            case EAST:
                location.setLocation(x + 1, y);
                break;
        }
        if(location.getX() > maxX || location.getY() > maxY ||
                location.getX() < 0 || location.getY() < 0 ) {
            location.setLocation(x, y);
        }
    }

    public void moveToward(Point Location) {
        Point thisLocation = getLocation();
        if (getLocation() != null && Location !=null) {
            int xDist = (int) (Location.getX() - thisLocation.getX());
            int yDist = (int) (Location.getX() - thisLocation.getY());
            if (Math.abs(xDist) > Math.abs(yDist)) {
                if (xDist < 0)
                    location.setLocation(new Point((int) (thisLocation.getX() - 1), (int) thisLocation.getY()));
                else
                    location.setLocation(new Point((int) (thisLocation.getX() + 1), (int) thisLocation.getY()));
            } else {
                if (yDist < 0)
                    location.setLocation(new Point((int) thisLocation.getX(), (int) (thisLocation.getY() - 1)));
                else
                    location.setLocation(new Point((int) thisLocation.getX(), (int) (thisLocation.getY() + 1)));
            }
        }
    }

    public boolean findFood(Environment myenvironment) {             //Find plant or herbivore to eat
        int xl = (int) getLocation().getX();
        int yl = (int) getLocation().getY();
        for (int i = (xl - 1); i <= (xl + 1); i++) {
            for (int j = (yl - 1); j <= (yl + 1); j++) {
                if (i >= 0 && i < myenvironment.getX() && j >= 0 && j < myenvironment.getY()) {
                    if (this instanceof Herbivore) {
                        if (myenvironment.map[i][j] instanceof Plant) {
                            myenvironment.removeObject(this);
                            myenvironment.removeObject(myenvironment.map[i][j]);
                            location.setLocation(i, j);
                            myenvironment.addObject(this);
                            return true;
                        }
                    } else if (this instanceof Carnivore) {
                        if (myenvironment.map[i][j] instanceof Herbivore) {
                            myenvironment.removeObject(this);
                            myenvironment.removeObject(myenvironment.map[i][j]);
                            location.setLocation(i, j);
                            myenvironment.addObject(this);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean parentIsAlive() {
        if(Environment.getInstance().map[getLocation().x][getLocation().y]== null)
            return false;
        return true;
    }
}
