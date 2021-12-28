package finalPacman.Modeles;

import finalPacman.PacManModel;
import javafx.geometry.Point2D;

public abstract class Creature{
    protected Point2D location;
    protected Point2D velocity;
    protected PacManModel.Direction lastDirection;
    protected PacManModel.Direction currentDirection;
    public Point2D potentialLocation;
    public PacManModel.Direction getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(PacManModel.Direction lastDirection) {
        this.lastDirection = lastDirection;
    }

    public PacManModel.Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(PacManModel.Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public Point2D dirToVelocity(PacManModel.Direction direction)
    {
        switch (direction)
        {
            case UP -> {
                return new Point2D(-1,0);
            }
            case DOWN -> {
                return new Point2D(1, 0);
            }
            case LEFT -> {
                return new Point2D(0, -1);
            }
            case RIGHT -> {
                return new Point2D(0, 1);
            }
            default -> {
                return new Point2D(0, 0);
            }
        }
    }
    public boolean isAvailable(PacManModel.CellValue[][] grid, Point2D location) {
        return switch (grid[(int) location.getX()][(int) location.getY()]) {
            case WALL, GHOST -> false;
            default -> true;
        };
    }
}
