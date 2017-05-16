/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template y, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

/**
 *
 * @author himanshu
 * OWN CODE
 */
import java.util.LinkedList;

import board.Bitboard;
import board.Move;
import board.Piece;
import board.Position;

public class MoveGenerator {
    
    public static LinkedList<Move> generateMovesForPiece(long[][] board, Piece piece) {
        LinkedList<Move> result = new LinkedList<Move>();
        long bitmap;
        
        bitmap = Bitboard.getMoves(board, piece);
        
        while (bitmap != 0) {
            Position destination = Bitboard.getPositionFromBitmap(bitmap);
            Move move = new Move();
            
            move.setX(piece.getPosition());
            move.setY(destination);
            
            result.add(move);
            
            bitmap = bitmap & ~Bitboard.getMaskAtPosition(destination);
        }
        
        return result;
    }
    
    public static LinkedList<Move> generateMovesForColor(long[][] board, int color) {
        LinkedList<Move> result = new LinkedList<Move>();
        
        for (int i = 0; i < 6; i++) {
            long bitmap = Bitboard.getBitmap(board, color, i);
            
            while (bitmap != 0) {
                Piece origin = new Piece(color, i);
                
                origin.setPosition(Bitboard.getPositionFromBitmap(bitmap));
                
                result.addAll(MoveGenerator.generateMovesForPiece(board, origin));
                
                bitmap = bitmap & ~Bitboard.getMaskAtPosition(origin.getPosition());
            }
        }
        
        return result;
    }
}

