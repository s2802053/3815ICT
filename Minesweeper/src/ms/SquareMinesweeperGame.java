/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ms;
import java.util.Random;
import java.util.Observable;

/**
 *
 * @author jburt
 */
public class SquareMinesweeperGame extends Observable { 
    
// controller class for Minesweeper game
    public SquareMinesweeperTile[][] gridContainer;
    private SquareMinesweeperTile[] bombLocations;
    private Boolean isWon;
    private Boolean isLost;
    private int gridSize;
    
    private void initGame(int gridSize, int nMines){
        
        // create square multidimensional array of 
        // gridSize x gridSize to hold tiles
        SquareMinesweeperTile[][] gridContainerEmpty = new SquareMinesweeperTile[gridSize][gridSize];
        
        // insert [nMines] mines into the grid
        SquareMinesweeperTile[][] gridContainerWithMines = insertMines(nMines, gridSize, gridContainerEmpty);
        
        // fill the rest of the grid with non-mines
        SquareMinesweeperTile[][] gridContainerPopulated = insertNonMines(gridSize, gridContainerWithMines);
        
        this.gridContainer = gridContainerPopulated;
        
        // increase adjacent bomb counts of tiles next to bombs
        setAdjacentBombs();
    }
    
    private SquareMinesweeperTile[][] insertMines(int nMines, int gridSize, SquareMinesweeperTile[][] gridContainer){
        
        // inserts [nMines] SquareBombTiles into the provided [gridContainer]
        // returns the gridContainer
        
        // initialise bombLocations
        this.bombLocations = new SquareMinesweeperTile[nMines];
        
        // produce 10 random coordinate pairing
        for (int i = 0; i < nMines; i++){
            
            boolean availableCoordinatesNotFound = true;
            
            while (availableCoordinatesNotFound){
                
                // generate random x and y coordinates
                int x = new Random().nextInt(gridSize);
                int y = new Random().nextInt(gridSize);
                
                // ensure there is not already a bomb at these coords
                if (gridContainer[y][x] == null){
                    
                    // set condition to false to break loop
                    availableCoordinatesNotFound = false;
                    
                    // create a new bomb tile for the produced coords
                    SquareBombTile tile = new SquareBombTile(x, y);
                    // insert into grid at coordinates
                    gridContainer[y][x] = tile;
                    this.bombLocations[i] = tile;
                    
                }
            }
        }
        return gridContainer;
    }
    
    private SquareMinesweeperTile[][] insertNonMines(int gridSize, SquareMinesweeperTile[][] gridContainer){
        
        // fill rest of grid with non mines
        for (int y = 0; y < gridSize; y++){
            for (int x = 0; x < gridSize; x++){
                if (gridContainer[y][x] == null){
                    SquareNonBombTile tile = new SquareNonBombTile(x, y);
                    gridContainer[y][x] = tile;
                }
            }
        }
        return gridContainer;
    }
    
    private void setAdjacentBombs(){
        for (SquareMinesweeperTile bombTile : this.bombLocations){
            // for each bomb tile
            // retrieve neighbour coordinates
            int[][] neighbours = bombTile.neighbours();
            for (int[] coord : neighbours){
                // increase bomb count of each neighbour
                if (coord == null){ continue; };
                int x = coord[0];
                int y = coord[1];
               
                this.gridContainer[y][x].increaseAdjacentBombs();
            }
        }
    }
    
    public SquareMinesweeperGame(int gridSize, int nMines){
        initGame(gridSize, nMines);
        this.gridSize = gridSize;
        this.isLost = false;
        this.isWon = false;
    }
    
    public SquareMinesweeperTile tileAtCoord(int x, int y){
        return gridContainer[y][x];
    }
    
    public void makeMove(int x, int y){
        
        // reveal the tile at location x, y
        // any further actions will be conducted
        revealInitialTile(x, y);
        setChanged();
        notifyObservers();
        
        
        
        if (!isLost){
            // if the game was not lost on the previous move
            // check if the win condition has been met
            if (this.gameIsWon()){
                this.isWon = true;
                this.gameHasBeenWon();
            }
        }
    }
    
    // reveal the tile at location x, y
    // and execute any consequential actions
    private void revealInitialTile(int x, int y){
        // reveal the tile at coordinates x, y
        // this tile could be a mine
        // determine the type of tile
        // call the appropriate handler function to execute further actions
        
        SquareMinesweeperTile tile = tileAtCoord(x, y);
        int type = tile.reveal();
        if (type == 0){
            gameHasBeenLost();
            // bomb has been clicked
        } else if ( type == 1){
            subsequentTileRevealed(x, y);
        }
    }
    
    // called when a subsequent tile is revealed
    // this tile cannot be a bomb tile, although
    // it could be adjacent to one. Decides if the
    // tile is adjacent to a bomb, or empty, and
    // calls the appropriate handler.
    private void subsequentTileRevealed(int x, int y){
        // decides if the revealed tile has adjacent bombs
        // or not
        
        // retrieve list of neighbouring tiles
        SquareMinesweeperTile tile = tileAtCoord(x, y);
            
        if (tile.adjacentBombs() > 0){
            adjacentTileRevealed(x, y);
        } else {
            emptyTileRevealed(x, y);
        }
    }
    
    // called when an adjacent tile is revealed
    // tile should be revealed and its bomb count set
    private void adjacentTileRevealed(int x, int y){
    }
    
    // called when an empty tile is revealed
    // should reveal all neighbouring unrevealed tiles
    private void emptyTileRevealed(int x, int y){
        // empty tile has no adjacent bombs
        // iterate over neighbours revealing them if they
        // have not already been revealed.
        SquareMinesweeperTile tile = tileAtCoord(x, y);
        int[][] neighbours = tile.neighbours();
        for (int[] neighbourCoords : neighbours){
            
            // if neighbour is null, continue
            if (neighbourCoords == null){ continue; };
            
            int neighbourX = neighbourCoords[0];
            int neighbourY = neighbourCoords[1];
            
            SquareMinesweeperTile neighbour = tileAtCoord(neighbourX, neighbourY);
            if (neighbour.isRevealed()){
                // if the neighbour tile has already been revealed
                // skip to the next neighbour
                continue;
            }
            neighbour.reveal();
            subsequentTileRevealed(neighbourX, neighbourY);
        }
    }
    
    // perform some actions when the game has been lost
    private void gameHasBeenLost(){
        System.out.println("Game has been lost");
    }
    
    // perform some actions when the game has been won
    private void gameHasBeenWon(){
        System.out.println("Game has been won");
    }
    
    private Boolean gameIsWon(){
        // tests the win condition and returns a boolean
        // indicating if the condition has been met
        //
        // win condition = all non-bomb tiles have been revealed
        for (int y = 0; y < gridSize; y++){
            for (int x = 0; x < gridSize; x++){
                // iterate over all tiles in grid
                
                SquareMinesweeperTile tile = tileAtCoord(x, y);
                if (! (tile.isBomb() || tile.isRevealed()) ){
                    // if we find a tile that is not a bomb, and
                    // has not been revealed, return false
                    return false;
                }
            }
        }
        // if we finish checking the grid without returning false
        // then return true
        return true;
    }
    
    public void drawToConsole(){
        String[][] outp = new String[gridSize][gridSize];
        for (int y = 0; y < gridSize; y++){
            for (int x = 0; x < gridSize; x++){
                SquareMinesweeperTile tile = tileAtCoord(x, y);
                
                if (tile.isBomb()){
                    outp[y][x] = "B";
                } else if (!tile.isRevealed()){
                    outp[y][x] = "#";
                } else {
                    outp[y][x] = Integer.toString(tile.adjacentBombs());
                }
            }
        }
        for (int i = 0; i < gridSize; i++){
            String line = String.join(" ", outp[i]);
            System.out.println(line);
        }
    }
}   
