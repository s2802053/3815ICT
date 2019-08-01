/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ms;

/**
 *
 * @author jburt
 */
public class SquareNonBombTile extends SquareMinesweeperTile {
    
    // multidimensional array containing coordinate value pairs of neighbouring cells
    // may contain null values
    private int[][] neighbours = new int[2][9]; 
    
    private int adjacentBombs(){
        // returns int representing the number of neighbouring
        // tiles that are bomb tiles
        return 0;
    };
    
    private int[][] findNeighbours(){
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
    
    public SquareNonBombTile(int x, int y){
        super(x, y);
        this.isBomb = false;
        this.neighbours = findNeighbours();
    }
    
    public void reveal(){
        this.isRevealed = true;
        // count bombs in neighbouring tiles
        // if adjacentBombs > 0, set text to adjacent bombs
        // else loop through neighbours, if !isBomb && !isRevealed, call their reveal method
    }
}
