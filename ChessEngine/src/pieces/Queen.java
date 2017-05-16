/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template y, choose Tools | Templates
 * and open the template in the editor.
 */
package pieces;

/**
 *
 * @author himanshu
 * SUPPORT https://chessprogramming.wikispaces.com/

 */
import board.Piece;

public class Queen extends Piece {
    

    public static long getMoves(long[][] board, Piece piece) {
        return Rook.getMoves(board, piece) | Bishop.getMoves(board, piece);
    }
}
