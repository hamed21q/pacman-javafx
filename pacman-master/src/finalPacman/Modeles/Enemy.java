package finalPacman.Modeles;

import finalPacman.Modeles.observabel.Observer;
import finalPacman.PacManModel;
import javafx.geometry.Point2D;

public class Enemy extends Creature implements Observer
{
    public PacManModel.Direction dir = PacManModel.Direction.UP;

    private Point2D targetLocation = new Point2D(15,9);

    public Enemy(Point2D location){
        this.potentialLocation = location;
        this.setLocation(this.potentialLocation);
    }

    public void move(PacManModel.CellValue[][] grid) {
        this.location = potentialLocation;
        Point2D potentialLocation;
        Point2D potentialVelocity = dirToVelocity(this.dir);
        potentialLocation = getLocation().add(potentialVelocity);
        if (isAvailable(grid,potentialLocation)){
            this.potentialLocation = potentialLocation;
        }
        else
        {
            this.dir = enemyAI();
        }
    }
    private PacManModel.Direction randomDirection(){
        int random_int = (int)Math.floor(Math.random()*(4)+1);
        switch (random_int)
        {
            case 1 -> {
                return PacManModel.Direction.DOWN;
            }
            case 2 -> {
                return PacManModel.Direction.RIGHT;
            }
            case 3 -> {
                return PacManModel.Direction.LEFT;
            }
            case 4 -> {
                return PacManModel.Direction.UP;
            }
        }
        return null;
    }

    private PacManModel.Direction enemyAI() {
        if (this.location.getY() == this.targetLocation.getY()){
            if (this.location.getX() > this.targetLocation.getX()){
                 return PacManModel.Direction.UP;
            }
            else{
                 return PacManModel.Direction.DOWN;
            }
        }
        else if(this.location.getX() == this.targetLocation.getX()){
            if (this.location.getY() > this.targetLocation.getY()){
                 return PacManModel.Direction.LEFT;
            }
            else{
               return PacManModel.Direction.RIGHT;
            }
        }else{
            return randomDirection();
        }
    }

    @Override
    public void update(Point2D location) {
        this.targetLocation = location;
    }
}
