/**
 * @author Jessie Baskauf and Ellie Mamantov
 * Encorporates the various Views of the application that reference different parts of the Model, including the main
 * game board, the score label, the level label, and the Game Over label.
 */

package finalPacman;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import finalPacman.PacManModel.CellValue;

public class PacManView extends Group {
    public final static double CELL_WIDTH = 20.0;

    @FXML private int rowCount;
    @FXML private int columnCount;
    private ImageView[][] cellViews;
    private Image playerImage;
    private Image pacmanRightImage;
    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image ghost1Image;
    private Image ghost2Image;
    private Image blueGhostImage;
    private Image wallImage;
    private Image bigDotImage;
    private Image smallDotImage;

    /**
     * Initializes the values of the image instance variables from files
     */
    public PacManView() {
        this.pacmanRightImage = new Image(getClass().getResourceAsStream("/res/pacmanRight.gif"));
        this.pacmanUpImage = new Image(getClass().getResourceAsStream("/res/pacmanUp.gif"));
        this.pacmanDownImage = new Image(getClass().getResourceAsStream("/res/pacmanDown.gif"));
        this.pacmanLeftImage = new Image(getClass().getResourceAsStream("/res/pacmanLeft.gif"));
        this.ghost1Image = new Image(getClass().getResourceAsStream("/res/redghost.gif"));
        this.ghost2Image = new Image(getClass().getResourceAsStream("/res/ghost2.gif"));
        this.blueGhostImage = new Image(getClass().getResourceAsStream("/res/blueghost.gif"));
        this.wallImage = new Image(getClass().getResourceAsStream("/res/wall.png"));
        this.bigDotImage = new Image(getClass().getResourceAsStream("/res/whitedot.png"));
        this.smallDotImage = new Image(getClass().getResourceAsStream("/res/smalldot.png"));
    }

    /**
     * Constructs an empty grid of ImageViews
     */
    private void initializeGrid() {
        if (this.rowCount > 0 && this.columnCount > 0) {
            this.cellViews = new ImageView[this.rowCount][this.columnCount];
            for (int row = 0; row < this.rowCount; row++) {
                for (int column = 0; column < this.columnCount; column++) {
                    ImageView imageView = new ImageView();
                    imageView.setX((double)column * CELL_WIDTH);
                    imageView.setY((double)row * CELL_WIDTH);
                    imageView.setFitWidth(CELL_WIDTH);
                    imageView.setFitHeight(CELL_WIDTH);
                    this.cellViews[row][column] = imageView;
                    //adding image view to root element of scene
                    this.getChildren().add(imageView);
                }
            }
        }
    }

    /** Updates the view to reflect the state of the model
     *
     * @param model
     */
    public void update(PacManModel model) {
        PacManModel.Direction direction = model.getPlayer().getCurrentDirection();
        switch (direction){
            case UP -> playerImage = pacmanUpImage;
            case LEFT -> playerImage = pacmanLeftImage;
            case DOWN -> playerImage = pacmanDownImage;
            case RIGHT, NONE -> playerImage = pacmanRightImage;
        }
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                CellValue value = model.getCellValue(i, j);
                switch (value){
                    case SMALLDOT -> this.cellViews[i][j].setImage(smallDotImage);
                    case BIGDOT -> this.cellViews[i][j].setImage(bigDotImage);
                    case WALL -> this.cellViews[i][j].setImage(wallImage);
                    case PACMANHOME -> this.cellViews[i][j].setImage(playerImage);
                    case GHOST -> this.cellViews[i][j].setImage(ghost1Image);
                    default -> this.cellViews[i][j].setImage(null);
                }
            }
        }
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        this.initializeGrid();
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        this.initializeGrid();
    }
}
