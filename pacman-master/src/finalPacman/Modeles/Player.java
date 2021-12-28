package finalPacman.Modeles;

import finalPacman.Modeles.observabel.Observer;
import finalPacman.Modeles.observabel.Subject;
import finalPacman.PacManModel;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Player extends Creature implements Subject {
    private ArrayList<Observer> enemies = new ArrayList<>();
    public Player(){
        this.potentialLocation = new Point2D(15,10);
    }

    public void move(PacManModel.CellValue[][] grid, PacManModel.Direction direction)  {
        this.location = this.potentialLocation;
        Point2D potentialLocation;
        Point2D potentialVelocity = dirToVelocity(direction);
        potentialLocation = getLocation().add(potentialVelocity);
        if (isAvailable(grid,potentialLocation)){
            this.potentialLocation = potentialLocation;
            this.notifyObservers();
        }
        else
        {
            setCurrentDirection(PacManModel.Direction.NONE);
        }
    }

    public PacManModel.Direction getLastDirection() {
        return lastDirection;
    }

    @Override
    public void registerObserver(Observer o) {
        this.enemies.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        this.enemies.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer enemy : enemies){
            enemy.update(this.location);
        }
    }
}
