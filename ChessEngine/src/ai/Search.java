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

public class Search {
    private int depth;
    private long nodes;
    
    public Search() {
        this.depth = 3;
        this.nodes = 0;
    }
    
    public Search(int depth) {
        this.depth = depth;
        this.nodes = 0;
    }
    
    public Move searchForMove(long[][] board, int color) {
        LinkedList<Move> availableMoves = MoveGenerator.generateMovesForColor(board, color);
        Move result = null;
        
        /* While there are still moves to evaluate... */
        for (Move move : availableMoves) {
            Piece current, capture;
            
            current = Bitboard.getPiece(board, move.getX());
            
            board = Bitboard.removePiece(board, current, move.getX());
            
            /* Remember any possible captures... */
            capture = Bitboard.getPiece(board, move.getY());
            
            board = Bitboard.movePiece(board, current, move.getY());
            
            long score = -this.search(board, Search.invertColor(color), this.depth, -100000, 100000);
            
            board = Bitboard.removePiece(board, current, move.getY());
            
            /* Ada tested a capture, make sure to put the piece back. */
            if (capture != null) {
                board = Bitboard.movePiece(board, capture, move.getY());
            }
            
            board = Bitboard.movePiece(board, current, move.getX());
            
            move.setScore(score);
        }
        
        long score = availableMoves.getFirst().getScore();
        
        result = availableMoves.getFirst();
        
        for (Move move : availableMoves) {
            System.out.print("    Potential Move: " + move + "\n");
            
            if (move.getScore() == -10000) {
                continue;
            }
            
            if (move.getScore() > score) {
                score = move.getScore();
                
                result = move;
            }
        }
        
        System.out.print("Move: " + result + ", Nodes: " + this.nodes + "\n");
        
        return result;
    }
    
    private long search(long[][] board, int color, int depth, long alpha, long beta) {
        this.nodes++;
        
        /* We are at a leaf, evaluate position. */
        if (depth <= 0) {
            return Evaluator.evaluate(board, color);
        }
        
        LinkedList<Move> availableMoves = MoveGenerator.generateMovesForColor(board, color);
        
        for (Move move : availableMoves) {
            Piece current, capture;
            
            current = Bitboard.getPiece(board, move.getX());
            
            board = Bitboard.removePiece(board, current, move.getX());
            
            /* Remember any possible captures... */
            capture = Bitboard.getPiece(board, move.getY());
            
            board = Bitboard.movePiece(board, current, move.getY());
            
            long score = -search(board, Search.invertColor(color), depth - 1, -beta, -alpha);
            
            if (score > alpha) {
                alpha = score;
            }
            
            board = Bitboard.removePiece(board, current, move.getY());
            
            /* Ada tested a capture, make sure to put the piece back. */
            if (capture != null) {
                board = Bitboard.movePiece(board, capture, move.getY());
            }
            
            board = Bitboard.movePiece(board, current, move.getX());
            
            if (beta <= alpha) {
                break;
            }
        }
        
        return alpha;
    }
    
    private static int invertColor(int color) {
        if (color != 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
