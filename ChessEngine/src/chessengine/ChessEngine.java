/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template y, choose Tools | Templates
 * and open the template in the editor.
 */
package chessengine;

import ai.MCTS_Search;
import ai.Search;
import board.Bitboard;
import board.Move;
import board.Piece;
import board.Position;

/**
 *
 * @author himanshu
 * OWN CODE
 */
public class ChessEngine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Bitboard bitboard = new Bitboard() ;
        long[][] board = Bitboard.createBoard() ;
        MCTS_Search search = new MCTS_Search(250,150) ;
        Search minimax = new Search(4) ;
        Move move = minimax.searchForMove(board,0) ;
        
       
        
        
        
        
        Move start = search.MCTS_searchForMove(board, 0) ;
        long score = start.getScore() ;
        Position X = start.getX() ;
        Position Y = start.getY() ;
        System.out.println(score) ;
        System.out.println(X);
        System.out.println(Y);
        
        Piece current= Bitboard.getPiece(board, start.getX());
        board = Bitboard.removePiece(board, current, start.getX());
        board = Bitboard.movePiece(board, current, start.getY());
        Move move2 = search.MCTS_searchForMove(board, 1) ;
        long score2 = move2.getScore() ;
        Position X2 = move2.getX() ;
        Position Y2 = move2.getY() ;
        System.out.println(score2) ;
        System.out.println(X2);
        System.out.println(Y2);
        
        
        
        Piece current2 = Bitboard.getPiece(board, move2.getX());
        board = Bitboard.removePiece(board, current2, move2.getX());
        board = Bitboard.movePiece(board, current2, move2.getY());
        Move move3 = search.MCTS_searchForMove(board, 0) ;
        long score3 = move3.getScore() ;
        Position X3 = move3.getX() ;
        Position Y3 = move3.getY() ;
        System.out.println(score3) ;
        System.out.println(X3);
        System.out.println(Y3);
    }
    
}
