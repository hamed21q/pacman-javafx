package finalPacman.Modeles.observabel;

import javafx.geometry.Point2D;

public interface Observer {
    public void update(Point2D location);
}
