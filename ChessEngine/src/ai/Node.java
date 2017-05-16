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
import board.Bitboard;
import java.util.* ;
import ai.Evaluator;

import ai.MCTS_Search;
import ai.MoveGenerator;
import board.Move;
import board.Piece;
/**
 *
 * @author himanshu
 * OWN CODE
 */
public class Node {
        public long[] score;
	public double games;
	public Move move;
	public LinkedList<Node> children ;
	//public Set<Integer> rVisited;
	public Node parent;
	public int player;
	long[][] board ;
        double weight ;
	/**
	 * This creates the root node
	 * 
	 * @param b
	 */
        
        
	public Node(long[][] b,int color,Move move) {
		children = new LinkedList<Node>() ;
                board = b ;
		player = color;
		this.move = move ;
                games= 0 ;
                score = new long[2] ;
                weight = 100000.00 ;
	}
        
        public Node(long[][] b, int color){
            children = new LinkedList<Node>() ;
            board = b ;
            player = color ;
            games = 0 ;
            score = new long[2] ;
            weight = 100000.00 ;
        }
        
        public void addChild(Node n){
            children.add(n );
        }
        
        

        /*public Node search(long[][] board,int color){
            long[][] currboard = duplicate(board) ;
            LinkedList<Move> availableMoves = MoveGenerator.generateMovesForColor(board, color);
            
        }*/
        public void createChildren(long[][] board, int color) {
            LinkedList<Move> availableMoves = MoveGenerator.generateMovesForColor(board, color);
            //Move result = null;
            
        
            for (Move move : availableMoves) {
                Piece current, capture;
            
                current = Bitboard.getPiece(board, move.getX());
            
                board = Bitboard.removePiece(board, current, move.getX());
            
                /* Remember any possible captures... */
                capture = Bitboard.getPiece(board, move.getY());
            
                board = Bitboard.movePiece(board, current, move.getY());
                Node child = new Node(duplicate(board),Node.invertColor(color),move) ;
                child.parent =this ;
                addChild(child);
               // MCTS_Search.boardNodepair.put(duplicate(board),child) ;
                //long score = -this.search(board, Search.invertColor(color), this.depth, -100000, 100000);
            
                board = Bitboard.removePiece(board, current, move.getY());
            
            /*  test capture, make sure to put the piece back. */
                if (capture != null) {
                    board = Bitboard.movePiece(board, capture, move.getY());
                }
            
                board = Bitboard.movePiece(board, current, move.getX());
            
                
            }
        }
        
        public boolean arecreatedChildren(){
            if(children.size()!=0){
                return true ;
            }else{
                return false ;
            }
        }
        
        public void backpropagateScore(long[] score){
            this.score[0] = this.score[0] + score[0] ;
            this.score[1] = this.score[1] + score[1] ;
            this.games++ ;
            if(this.parent != null){
                this.parent.backpropagateScore(score);
                this.weight = upperConfidenceBound(1.0) ;
            }
        }
        
        public Node select(){
            if(!arecreatedChildren()){
                createChildren(this.board, this.player) ;
            }
            double totalweight = 0.0;
            for(Node child : children){
                totalweight += child.weight ;
            }
            Random r = new Random() ;
            double randomValue = totalweight*(r.nextDouble()) ;
            int i =0; 
            double temp = 0.0 ;
            for(Node child : children){
                temp += child.weight ;
                if(temp>randomValue){
                    return children.get(i) ;
                }else{
                    i++ ;
                }
            }
            return children.getLast() ;
            
        }
        
        public void expand(){
            if(!arecreatedChildren()){
                createChildren(this.board, this.player) ;
            }
        }
        
        public void simulate(int depth){
            if(!arecreatedChildren()){
                System.out.println("Simulate at depth: "+depth);
                createChildren(this.board, this.player) ;
            }
            Random r = new Random() ;
            int trueDepth = r.nextInt()%(depth+1);
            Node n = select() ;
            n.truesimulate(trueDepth-1) ;
        }
        
        public void truesimulate(int depth){
            if(depth<=0){
                long[] tempscore = new long[2] ;
                tempscore[0] = Evaluator.evaluate(this.board, 0) ;
                tempscore[1] = Evaluator.evaluate(this.board, 1) ;
                backpropagateScore(tempscore);
                return ;
            }
            Node n = select() ;
            n.truesimulate(depth-1) ;
        }
	

	
	public double upperConfidenceBound(double c) {
		return (double)(score[0]+score[1])/(2*games) +
				c*Math.sqrt(Math.log(parent.games + 1) / games);
	}
        
        public Node bestNode(int color){
            Node bestNode = null ;
            long bestValue = -100000000 ;
            for(Node child : children){
                if(child.score[color] > bestValue){
                    bestValue = child.score[color] ;
                    bestNode = child ;
                }
            }
            return bestNode ;
        }

	/**
	 * Update the tree with the new score.
	 * @param scr
	 */
	private static int invertColor(int color) {
            if (color != 0) {
                return 0;
            } else {
                return 1;
            }
        }

	
        
        private long[][] duplicate(long[][] board){
            long[][] newboard = new long[6][2];
            for(int i =0; i < 6 ; i++){
                for(int j =0 ; j < 2;  j++){
                    newboard[i][j] = board[i][j] ;
                }
            }
            return newboard ;
        }
	/**
	 * Select a child node at random and return it.
	 * @param board
	 * @return
	 */
	
    
}

