/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template y, choose Tools | Templates
 * and open the template in the editor.
 */
package pieces;

/**
 *
 * @author himanshu
 *SUPPORT https://chessprogramming.wikispaces.com/

 */
import board.Bitboard;
import board.Piece;
import board.Position;

public class Pawn extends Piece {
    

    public static long getMoves(long[][] board, Piece piece) {
        long bitmap = Bitboard.getBitmapAtPosition(board, piece.getPosition());
        long result;
        
        /* For Pawns we need to know their color. */
        if (piece.getColor() == 0) {
            int x = piece.getPosition().getX();
            int y = piece.getPosition().getY();
            
            /* If at a position of origin, allow the Pawn to advance two squares. */
            if (Bitboard.getPositionFromBitmap(bitmap).getX() == 6) {
                if (Bitboard.occupied(board, new Position(x - 1, y))) {
                    result = (bitmap >>> 8);
                } else {
                    if (Bitboard.occupied(board, new Position(x - 2, y))) {
                        result = (bitmap >>> 8);
                    } else {
                        result = (bitmap >>> 8) | (bitmap >>> 16);
                    }
                }
            } else {
                result = (bitmap >>> 8);
            }
            
            long opponents = 0;
            
            /* Gather enemies in attackable positions... */
            opponents = opponents | Bitboard.getBitmapAtPosition(board, 1, new Position(x - 1, y + 1));
            opponents = opponents | Bitboard.getBitmapAtPosition(board, 1, new Position(x - 1, y - 1));
            
            /* Determine whether the Pawn can advance ahead, or attack... */
            if ((opponents > 0) && (Bitboard.getBitmapAtPosition(board, 1,
                    new Position(x, y - 1)) > 0)) {
                result = 0;
            }
            
            /* Make sure we can't advance when we're being blocked... */
            if (Bitboard.getBitmapAtPosition(board, 1, new Position(x - 1, y)) > 0) {
                result = 0;
            }
            
            result = result | opponents;
        } else {
            int x = piece.getPosition().getX();
            int y = piece.getPosition().getY();
            
            /* If at a position of origin, allow the Pawn to advance two squares. */
            if (Bitboard.getPositionFromBitmap(bitmap).getX() == 1) {
                if (Bitboard.occupied(board, new Position(x + 1, y))) {
                    result = (bitmap << 8);
                } else {
                    if (Bitboard.occupied(board, new Position(x + 2, y))) {
                        result = (bitmap << 8);
                    } else {
                        result = (bitmap << 8) | (bitmap << 16);
                    }
                }
            } else {
                result = (bitmap << 8);
            }
            
            long opponents = 0;
            
            /* Gather enemies in attackable positions... */
            opponents = opponents | Bitboard.getBitmapAtPosition(board, 0, new Position(x + 1, y + 1));
            opponents = opponents | Bitboard.getBitmapAtPosition(board, 0, new Position(x + 1, y - 1));
            
            /* Determine whether the Pawn can advance ahead, or attack... */
            if ((opponents > 0) && (Bitboard.getBitmapAtPosition(board, 0, new Position(x, y + 1)) > 0)) {
                result = 0;
            }
            
            /* Make sure we can't advance when we're being blocked... */
            if (Bitboard.getBitmapAtPosition(board, 0, new Position(x + 1, y)) > 0) {
                result = 0;
            }
            
            result = result | opponents;
        }
        
        /* Some cleaning up... Make sure we can't wrap around the board. */
        if (piece.getPosition().getY() == Bitboard.Y.A) {
            result = result & ~Bitboard.getY(Bitboard.Y.H);
        } else if (piece.getPosition().getY() == Bitboard.Y.H) {
            result = result & ~Bitboard.getY(Bitboard.Y.A);
        }
        
        return result;
    }
}

