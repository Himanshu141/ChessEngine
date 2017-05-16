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

 * 
 */
import board.Bitboard;
import board.Piece;
import board.Position;

public class Bishop extends Piece {
    

    public static long getMoves(long[][] board, Piece piece) {
        long bitmap = Bitboard.getBitmapAtPosition(board, piece.getPosition());
        long result = 0;
        
        int x = piece.getPosition().getX() + 1;
        int y = piece.getPosition().getY() + 1;
        
        /* Squares on the lower-right diagonal of the Bishop. */
        for (int i = 1; i < (8 - piece.getPosition().getY()); i++) {
            result = result | (bitmap << (9 * i));
            
            if (Bitboard.occupied(board, new Position(x, y))) {
                break;
            }
            
            x++;
            y++;
        }
        
        x = piece.getPosition().getX() + 1;
        y = piece.getPosition().getY() - 1;
        
        /* Squares on the lower-left diagonal of the Bishop. */
        for (int i = 1; i < (piece.getPosition().getY() + 1); i++) {
            result = result | (bitmap << (7 * i));
            
            if (Bitboard.occupied(board, new Position(x, y))) {
                break;
            }
            
            x++;
            y--;
        }
        
        x = piece.getPosition().getX() - 1;
        y = piece.getPosition().getY() + 1;
        
        /* Squares on the upper-right diagonal of the Bishop. */
        for (int i = 1; i < (8 - piece.getPosition().getY()); i++) {
            result = result | (bitmap >>> (7 * i));
            
            if (Bitboard.occupied(board, new Position(x, y))) {
                break;
            }
            
            x--;
            y++;
        }
        
        x = piece.getPosition().getX() - 1;
        y = piece.getPosition().getY() - 1;
        
        /* Squares on the upper-left diagonal of the Bishop. */
        for (int i = 1; i < (piece.getPosition().getY() + 1); i++) {
            result = result | (bitmap >>> (9 * i));
            
            if (Bitboard.occupied(board, new Position(x, y))) {
                break;
            }
            
            x--;
            y--;
        }
        
        return result;
    }
}

