/**
 * @author Jessie Baskauf and Ellie Mamantov
 * The Controller handles user input and coordinates the updating of the model and the view with the help of a timer.
 */

package finalPacman;

import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Controller implements EventHandler<KeyEvent> {
    final private static double FRAMES_PER_SECOND = 5.0;

    @FXML private Label scoreLabel;
    @FXML private Label levelLabel;
    @FXML private  Label gameOverLabel;
    @FXML private PacManView pacManView;
    private PacManModel pacManModel;
    private static final String[] levelFiles = {"C:/pacman-master/pacman-master/src/levels/level1.txt", "C:/pacman-master/pacman-master/src/levels/level2.txt", "C:/pacman-master/pacman-master/src/levels/level3.txt"};
    private Timer timer;
    private boolean paused;
    public Controller() {
        this.paused = false;
    }


    public void initialize(){
        String file = getLevelFile(0);
        this.pacManModel = new PacManModel();
        this.update(PacManModel.Direction.NONE);
        this.startTimer();
    }

    private void startTimer() {
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                            update(pacManModel.getPlayer().getCurrentDirection());
                    }
                });
            }
        };
        long frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    private void update(PacManModel.Direction direction){
        this.pacManModel.step(direction);
        this.pacManView.update(pacManModel);
        this.levelLabel.setText(String.format("Level: %d", this.pacManModel.getLevel()));
        scoreLabel.setText("Score: " + pacManModel.getScore());
        if(pacManModel.playerWon())
        {
            gameOverLabel.setText("Congratulation, you won!!!");
            pacManModel.setSmallDotCount(0);
            this.pacManModel.startNextLevel();
            pacManModel.setPlayerWon(false);
            gameOverLabel.setText("");
        }
        else if(pacManModel.isGameOver()){
            gameOverLabel.setText("Game Over!!!!");
            this.timer.cancel();
        }

    }


    /**
     * Takes in user keyboard input to control the movement of PacMan and start new games
     * @param keyEvent user's key click
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        boolean keyRecognized = true;
        KeyCode code = keyEvent.getCode();
        PacManModel.Direction direction = PacManModel.Direction.NONE;
        if (code == KeyCode.LEFT) {
            direction = PacManModel.Direction.LEFT;
        } else if (code == KeyCode.RIGHT) {
            direction = PacManModel.Direction.RIGHT;
        } else if (code == KeyCode.UP) {
            direction = PacManModel.Direction.UP;
        } else if (code == KeyCode.DOWN) {
            direction = PacManModel.Direction.DOWN;
        } else if (code == KeyCode.G) {
            pause();
            this.pacManModel.setGameOver(false);
            this.pacManModel.startNewGame();
            this.gameOverLabel.setText(String.format(""));
            paused = false;
            this.startTimer();
        } else {
            keyRecognized = false;
        }
        if (keyRecognized) {
            keyEvent.consume();
            pacManModel.getPlayer().setCurrentDirection(direction);
        }
    }

    /**
     * Pause the timer
     */
    public void pause() {
            this.timer.cancel();
            this.paused = true;
    }

    public double getBoardWidth() {
        return PacManView.CELL_WIDTH * this.pacManView.getColumnCount();
    }

    public double getBoardHeight() {
        return PacManView.CELL_WIDTH * this.pacManView.getRowCount();
    }

    public static String getLevelFile(int x)
    {
        return levelFiles[x];
    }

    public boolean getPaused() {
        return paused;
    }
}
