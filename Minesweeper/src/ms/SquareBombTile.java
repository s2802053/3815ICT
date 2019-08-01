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
public class SquareBombTile extends SquareMinesweeperTile {
    SquareBombTile(int x, int y){
        super(x, y);
        this.isBomb = true;
    }
}
