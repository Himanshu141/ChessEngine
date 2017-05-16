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
import board.Position;

public class Rook extends Piece {
    

    public static long getMoves(long[][] board, Piece piece) {
        long bitmap = Bitboard.getBitmapAtPosition(board, piece.getPosition());
        long result = 0;
        
        int x = piece.getPosition().getX() + 1;
        int y = piece.getPosition().getY();
        
        /* The set of squares, below the Rook, along its x. */
        for (int i = 1; i < (8 - piece.getPosition().getX()); i++) {
            result = result | (bitmap << (8 * i));
            
            if (Bitboard.occupied(board, new Position(x, y))) {
                break;
            }
            
            x++;
        }
        
        x = piece.getPosition().getX() - 1;
        y = piece.getPosition().getY();
        
        /* The set of squares, above the Rook, along its x. */
        for (int i = 1; i < (piece.getPosition().getX() + 1); i++) {
            result = result | (bitmap >>> (8 * i));
            
            if (Bitboard.occupied(board, new Position(x, y))) {
                break;
            }
            
            x--;
        }
        
        x = piece.getPosition().getX();
        y = piece.getPosition().getY() + 1;
        
        /* The set of squares, right of the Rook, along its y. */
        for (int i = 1; i < (8 - piece.getPosition().getY()); i++) {
            result = result | (bitmap << (1 * i));
            
            if (Bitboard.occupied(board, new Position(x, y))) {
                break;
            }
            
            y++;
        }
        
        x = piece.getPosition().getX();
        y = piece.getPosition().getY() - 1;
        
        /* The set of squares, left of the Rook, along its y. */
        for (int i = 1; i < (piece.getPosition().getY() + 1); i++) {
            result = result | (bitmap >>> (1 * i));
            
            if (Bitboard.occupied(board, new Position(x, y))) {
                break;
            }
            
            y--;
        }
        
        return result;
    }
}

