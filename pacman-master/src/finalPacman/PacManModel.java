package finalPacman;

import finalPacman.Modeles.Enemy;
import finalPacman.Modeles.Player;
import javafx.geometry.Point2D;

import javafx.fxml.FXML;

import java.io.*;

import java.util.*;

public class PacManModel {


    public void setPlayerWon(boolean playerWon) {
        this.playerWon = playerWon;
    }

    private boolean playerWon = false;
    private boolean gameOver = false;

    public enum CellValue {
        EMPTY, SMALLDOT, BIGDOT, WALL, GHOST, PACMANHOME
    }

    ;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    }
    @FXML
    private int rowCount;
    @FXML
    private int score;
    private int smallDotCount;
    private int columnCount;
    private CellValue[][] grid;
    private Player player = new Player();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private int level = 0;
    private int enemyCount;

    public PacManModel() {
        this.startNewGame();
    }

    public void initializeLevel(String fileName) {
        fileName = "/" + fileName;
        File file = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()) {
                lineScanner.next();
                columnCount++;
            }
            rowCount++;
        }
        columnCount = columnCount / rowCount;
        Scanner scanner2 = null;
        try {
            scanner2 = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        grid = new CellValue[rowCount][columnCount];
        int row = 0;
        int pacmanRow = 0;
        int pacmanColumn = 0;
        while (scanner2.hasNextLine()) {
            int column = 0;
            String line = scanner2.nextLine();
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()) {
                String value = lineScanner.next();
                CellValue thisValue = CellValue.EMPTY;
                //(value.equals("E"))
                switch (value) {
                    case "W" -> thisValue = CellValue.WALL;
                    case "S" -> {
                        thisValue = CellValue.SMALLDOT;
                        smallDotCount++;
                    }
                    case "P" -> {
                        thisValue = CellValue.PACMANHOME;
                        pacmanRow = row;
                        pacmanColumn = column;
                    }
                    case "G" -> {
                        enemies.add(new Enemy(new Point2D(row, column)));
                        enemyCount++;
                        thisValue = CellValue.GHOST;
                    }
                    default -> thisValue = CellValue.EMPTY;
                }
                grid[row][column] = thisValue;
                column++;
            }
            row++;
        }
        player.setLocation(new Point2D(pacmanRow, pacmanColumn));
        player.setLastDirection(Direction.NONE);
        player.setCurrentDirection(Direction.NONE);
        enemyCount = enemies.size();
        for (Enemy enemy: enemies){
            player.registerObserver(enemy);
        }
    }

    /**
     * Initialize values of instance variables and initialize level map
     */
    public void startNewGame() {
        this.player.setLocation(new Point2D(15, 9));
        smallDotCount = 0;
        enemyCount = 0;
        enemies.clear();
        this.score = 0;
        rowCount = 0;
        columnCount = 0;
        this.player.potentialLocation = new Point2D(15, 9);
        this.player.setCurrentDirection(Direction.RIGHT);
        this.level = 1;
        this.initializeLevel(Controller.getLevelFile(0));
    }

    public void startNextLevel() {
        if (this.playerWon()) {
            System.out.println(level);

            this.level++;
            System.out.println(level);

            rowCount = 0;
            columnCount = 0;
            try {
                this.initializeLevel(Controller.getLevelFile(level - 1));
            } catch (ArrayIndexOutOfBoundsException e) {
                level--;

            }
        }
    }

    public boolean playerWon() {
        return this.playerWon;
    }


    public Player getPlayer() {
        return player;
    }


    public void setSmallDotCount(int smallDotCount) {
        this.smallDotCount = smallDotCount;
    }

    private void movePlayer(Direction direction)
    {
        this.grid[(int)player.getLocation().getX()][(int)player.getLocation().getY()] = CellValue.EMPTY;
        //set last location of player to Empty
        //change players location if its possible
        this.player.move(grid, direction);
        //take the previous player location value(if it was small dot or big dot)
        CellValue playerCellvalue = this.grid[(int)player.getLocation().getX()][(int)player.getLocation().getY()];
        switch (playerCellvalue) {
            case SMALLDOT -> {
                this.score+=10;
                smallDotCount--;
            }
        }
        //update grid and set new players location to PACKMANHOME
        this.grid[(int)player.getLocation().getX()][(int)player.getLocation().getY()] = CellValue.PACMANHOME;
        if(smallDotCount <= 0)
        {
            playerWon = true;
        }
    }

    private void moveGhosts(){
        for (Enemy enemy : this.enemies){
            enemy.move(grid);
            CellValue cell = grid[(int) enemy.potentialLocation.getX()][(int) enemy.potentialLocation.getY()];
            grid[(int) enemy.getLocation().getX()][(int) enemy.getLocation().getY()] = cell;
            grid[(int) enemy.potentialLocation.getX()][(int) enemy.potentialLocation.getY()] = CellValue.GHOST;
        }

    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void step(Direction direction) {
        System.out.println(this.smallDotCount);
        movePlayer(direction);
        moveGhosts();
        if (player_and_enemy_hit()){ gameOver = true; }
    }


    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    private boolean player_and_enemy_hit(){
        for (Enemy enemy : enemies){
            if (enemy.getLocation().equals(this.player.getLocation())){
                grid[(int) this.player.getLocation().getX()][(int) this.player.getLocation().getY()] = CellValue.EMPTY;
                return true;
            }
        }
        return false;
    }


    public CellValue[][] getGrid() {
        return grid;
    }

    public CellValue getCellValue(int row, int column) {
        assert row >= 0 && row < this.grid.length && column >= 0 && column < this.grid[0].length;
        return this.grid[row][column];
    }
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }
    public int getScore(){ return this.score; };


}

