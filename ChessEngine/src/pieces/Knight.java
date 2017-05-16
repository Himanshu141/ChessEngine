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

public class Knight extends Piece {
    private static final long serialVersionUID = -8631562924470037688L;

    public static long getMoves(long[][] board, Piece piece) {
        long bitmap = Bitboard.getBitmapAtPosition(board, piece.getPosition());
        long result = 0;
        
        /* The set of squares below the Knight. */
        result = result | ((bitmap <<  10) & ~(Bitboard.getY(Bitboard.Y.A) | Bitboard.getY(Bitboard.Y.B)));
        result = result | ((bitmap <<  17) & ~(Bitboard.getY(Bitboard.Y.A)));
        result = result | ((bitmap <<   6) & ~(Bitboard.getY(Bitboard.Y.G) | Bitboard.getY(Bitboard.Y.H)));
        result = result | ((bitmap <<  15) & ~(Bitboard.getY(Bitboard.Y.H)));
        
        /* The set of squares above the Knight. */
        result = result | ((bitmap >>> 10) & ~(Bitboard.getY(Bitboard.Y.G) | Bitboard.getY(Bitboard.Y.H)));
        result = result | ((bitmap >>> 17) & ~(Bitboard.getY(Bitboard.Y.H)));
        result = result | ((bitmap >>>  6) & ~(Bitboard.getY(Bitboard.Y.A) | Bitboard.getY(Bitboard.Y.B)));
        result = result | ((bitmap >>> 15) & ~(Bitboard.getY(Bitboard.Y.A)));
        
        return result;
    }
}
