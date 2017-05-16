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
import board.Bitboard;
import board.Piece;

public class King extends Piece {
    

    public static long getMoves(long[][] board, Piece piece) {
        long bitmap = Bitboard.getBitmapAtPosition(board, piece.getPosition());
        long result = 0;
        
        /* The squares directly above, and below, the King. */
        result = result | (bitmap << 8) | (bitmap >>> 8);
        
        /* The three squares to the right of the King. */
        result = result | ((bitmap <<  1) & ~Bitboard.getY(Bitboard.Y.A));
        result = result | ((bitmap <<  9) & ~Bitboard.getY(Bitboard.Y.A));
        result = result | ((bitmap <<  7) & ~Bitboard.getY(Bitboard.Y.H));
        
        /* The three squares to the left of the King. */
        result = result | ((bitmap >>> 1) & ~Bitboard.getY(Bitboard.Y.H));
        result = result | ((bitmap >>> 9) & ~Bitboard.getY(Bitboard.Y.H));
        result = result | ((bitmap >>> 7) & ~Bitboard.getY(Bitboard.Y.A));
        
        return result;
    }
}

