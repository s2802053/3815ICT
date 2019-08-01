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
        
    public SquareMinesweeperTile(int x, int y) {
        this.x = x;
        this.y = y;
        this.isRevealed = false;
    }
    public void reveal() {}
    
}

/*
App starts
Run nested loops to generate grid of x * y squares
For each square - create class object with x and y values

*/