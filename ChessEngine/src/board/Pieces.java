/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template y, choose Tools | Templates
 * and open the template in the editor.
 */
package board;

/**
 *
 * @author himanshu
 * SUPPORT https://chessprogramming.wikispaces.com/

 */





public class Pieces  {
    
    private Piece[][] pieces;
    
   
    
    public Pieces() {
        this.pieces = new Piece[16][2];
    }
    
   
    
    public Piece getPiece(int color, int type) {
        return this.pieces[type][color];
    }
}

