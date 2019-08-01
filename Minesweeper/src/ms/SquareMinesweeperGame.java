/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ms;
import java.util.Random;

/**
 *
 * @author jburt
 */
public class SquareMinesweeperGame {
    // controller class for Minesweeper game
    private SquareMinesweeperTile[][] gridContainer;
    
    private void initGame(int gridSize, int nMines){
        
        // create square multidimensional array of 
        // gridSize x gridSize to hold tiles
        SquareMinesweeperTile[][] gridContainerEmpty = new SquareMinesweeperTile[gridSize][gridSize];
        
        // insert [nBombs] mines into the grid
        SquareMinesweeperTile[][] gridContainerWithMines = insertMines(nMines, gridSize, gridContainerEmpty);
        
        // fill the rest of the grid with non-mines
        SquareMinesweeperTile[][] gridContainerPopulated = insertNonMines(gridSize, gridContainerWithMines);
        
        this.gridContainer = gridContainerPopulated;   
    }
    
    private SquareMinesweeperTile[][] insertMines(int nMines, int gridSize, SquareMinesweeperTile[][] gridContainer){
        
        // inserts [nMines] SquareBombTiles into the provided [gridContainer]
        // returns the gridContainer
        
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
    
    public SquareMinesweeperGame(int gridSize, int nMines){
        initGame(gridSize, nMines);
    }
    
    public SquareMinesweeperTile tileAtCoord(int x, int y){
        return gridContainer[y][x];
    }
    
    public void revealTileAtCoord(int x, int y){
        // reveal the tile at coordinates x, y
        // determine the type of tile
        // call the appropriate handler function to execute further actions
        
        
        SquareMinesweeperTile tile = this.gridContainer[y][x];
        int type = tile.reveal();
        if (type == 0){
            bombTileRevealed(x, y);
            // bomb has been clicked
        } else if ( type == 1){
            
        }
    }
    
    private int countAdjacentBombs(int[][] neighbours){
        int count = 0;
        for (int[] neighbour : neighbours){
            
            // skip this iteration if the value is null
            if (neighbour == null) { continue; }
            // retrieve coordinates
            
            int x = neighbour[0];
            int y = neighbour[1];
            
            // increase count if bomb found
            if (this.gridContainer[y][x].isBomb() == true){
                count++;
            }         
        }
        return count;
    }
    
    private void bombTileRevealed(int x, int y){
        System.out.println("Game over - bomb revealed");
    }
    
    private void nonBombTileRevealed(int x, int y){
        // decides if the revealed tile has adjacent bombs
        // or not
        
        // retrieve list of neighbouring tiles
        SquareMinesweeperTile tile = this.gridContainer[y][x];
        int[][] neighbours = tile.neighbours();
            
            
        int adjacentBombs = countAdjacentBombs(neighbours);
        tile.setAdjacentBombs(adjacentBombs);
            
        if (adjacentBombs > 0){
            adjacentTileRevealed();
        } else {
            emptyTileRevealed();
        }
    }
    
    private void adjacentTileRevealed(){
        // display count
    }
    
    private void emptyTileRevealed(){
        // we know there are no adjacent bombs
        // iterate over neighbours
        // call their reveal method, then nonBombTileRevealed for each of them
    }
}
/*



*/