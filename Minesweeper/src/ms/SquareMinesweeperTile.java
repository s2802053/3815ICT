/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ms;
import java.util.*;

/**
 *
 * @author jburt
 */
public class SquareMinesweeperTile {
    protected int x; // x location of tile in grid
    protected int y; // y location of tile in grid
    protected Boolean isBomb;
    protected Boolean isRevealed;
    protected int adjacentBombs;
    
    public Boolean isBomb(){
        return this.isBomb;
    }
    
    public Boolean isRevealed(){
        return this.isRevealed;
    }
    
    public int adjacentBombs(){
        return this.adjacentBombs;
    }
    
    public void setAdjacentBombs(int i){
        this.adjacentBombs = i;
    }
    
    public void increaseAdjacentBombs(){
        this.adjacentBombs++;
    }
    
    public int[][] neighbours(){
        boolean topRow = this.y < 1;
        boolean bottomRow = this.y == 9;
        boolean leftCol = this.x == 0;
        boolean rightCol = this.x == 9;
        
        // multidimensional array containing all 
        // possible coordinates of neighbouring cells
        int[][] possibilities = {{x-1, y-1}, {x, y-1}, {x+1, y-1},
                                     {x-1, y}, null, {x+1, y},
                                     {x-1, y+1}, {x, y+1}, {x+1, y+1}};
        
        // filter out the neighbours that aren't required
        
        if (topRow){
            // no NW, N or NE neighbours
            possibilities[0] = null;
            possibilities[1] = null;
            possibilities[2] = null;
        } else if (bottomRow){
            // no SW, S or SE neighbours
            possibilities[6] = null;
            possibilities[7] = null;
            possibilities[8] = null;
        }
        
        if (leftCol){
            // no NW, W or SW neighbours
            possibilities[0] = null;
            possibilities[3] = null;
            possibilities[6] = null;
        } else if (rightCol){
            // no NE, E or SE neighbours
            possibilities[2] = null;
            possibilities[5] = null;
            possibilities[8] = null;
        }
        return possibilities;
    }
    
    public SquareMinesweeperTile(int x, int y) {
        this.x = x;
        this.y = y;
        this.isRevealed = false;
    }
    
    public int reveal(){
        this.isRevealed = true;
        return 0;
    }
}

/*
App starts
Run nested loops to generate grid of x * y squares
For each square - create class object with x and y values

*/