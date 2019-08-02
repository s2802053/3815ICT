/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ms;
import java.awt.event.*;
/**
 *
 * @author jburt
 */
public class ClickHandler implements ActionListener {
    
    int x;
    int y;
    SquareMinesweeperGame currentGame;
    
    public ClickHandler(int x, int y, SquareMinesweeperGame currentGame){
        this.x = x;
        this.y = y;
        this.currentGame = currentGame;
    }
    
    public void actionPerformed(ActionEvent event){
        currentGame.makeMove(x, y);
    }
}
