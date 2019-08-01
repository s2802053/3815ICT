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
    
    public SquareNonBombTile(int x, int y){
        super(x, y);
        this.isBomb = false;
    }
    
    public int reveal(){
        this.isRevealed = true;
        return 1;
    }
}
