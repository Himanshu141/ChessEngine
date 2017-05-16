/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template y, choose Tools | Templates
 * and open the template in the editor.
 */
package board;

/**
 *
 * @author himanshu
 * SUPPORT https://chessprogramming.wikispaces.com/

 */

import pieces.*;



public class Bitboard {
    
    
    private static long[] masks = null;
    
    /* Letter representations of board's ys. */
    public static class Y {
        public static final int A = 0;
        public static final int B = 1;
        public static final int C = 2;
        public static final int D = 3;
        public static final int E = 4;
        public static final int F = 5;
        public static final int G = 6;
        public static final int H = 7;
    }
    
    /* All of the important bitmaps used by the Bitboard. */
    private static class Bitmaps {
        
        /* Bitmaps for white pieces */
        private static final long[] WHITE_PIECES = {
                0x1000000000000000L, /* KING   */
                0x0800000000000000L, /* QUEEN  */
                0x8100000000000000L, /* ROOK   */
                0x2400000000000000L, /* BISHOP */
                0x4200000000000000L, /* KNIGHT */
                0x00FF000000000000L, /* PAWN   */
        };
        
        /* Bitmaps for black pieces */
        private static final long[] BLACK_PIECES = {
                0x0000000000000010L, /* KING   */
                0x0000000000000008L, /* QUEEN  */
                0x0000000000000081L, /* ROOK   */
                0x0000000000000024L, /* BISHOP */
                0x0000000000000042L, /* KNIGHT */
                0x000000000000FF00L, /* PAWN   */
        };
        
        /* Bitmaps for board xs */
        private static final long[] RANKS = {
                0xFF00000000000000L, /* 1 */
                0x00FF000000000000L, /* 2 */
                0x0000FF0000000000L, /* 3 */
                0x000000FF00000000L, /* 4 */
                0x00000000FF000000L, /* 5 */
                0x0000000000FF0000L, /* 6 */
                0x000000000000FF00L, /* 7 */
                0x00000000000000FFL, /* 8 */
        };
        
        /* Bitmaps for board ys */
        private static final long[] FILES = {
                0x0101010101010101L, /* A */
                0x0202020202020202L, /* B */
                0x0404040404040404L, /* C */
                0x0808080808080808L, /* D */
                0x1010101010101010L, /* E */
                0x2020202020202020L, /* F */
                0x4040404040404040L, /* G */
                0x8080808080808080L, /* H */
        };
    }
    
    public Bitboard() {
        masks = new long[64];
        
        for (int i = 0; i < masks.length; i++) {
            masks[i] = 1L << i;
        }
    }
    
    public static long[][] createBoard() {
        long[][] board = new long[6][2];
        
        for (int i = 0; i < board.length; i++) {
        	board[i][0] = Bitmaps.WHITE_PIECES[i];
        	board[i][1] = Bitmaps.BLACK_PIECES[i];
        }
        
        if (Bitboard.masks == null) {
            Bitboard.masks = new long[64];
            
            for (int i = 0; i < Bitboard.masks.length; i++) {
                Bitboard.masks[i] = 1L << i;
            }
        }
        
        return board;
    }
    
    public static long getBitmap(long[][] board) {
        long result = 0;
        
        for (int i = 0; i < board.length; i++) {
            result = result | board[i][0] | board[i][1];
        }
        
        return result;
    }
    
    public static long getBitmap(long[][] board, Piece piece) {
        return getBitmap(board, piece.getColor(), piece.getType());
    }
    
    public static long getBitmap(long[][] board, int color, int type) {
        return board[type][color];
    }
    
    public static long getBitmap(long[][] board, int color) {
        long result = 0;
        
        for (int i = 0; i < board.length; i++) {
            result = result | board[i][color];
        }
        
        return result;
    }
    
    public static long getBitmapAtPosition(long[][] board, Position position) {
        return Bitboard.getBitmap(board) & Bitboard.getMaskAtPosition(position);
    }
    
    public static long getBitmapAtPosition(long[][] board, int color, Position position) {
        long result = Bitboard.getBitmap(board, color) & Bitboard.getMaskAtPosition(position);
        
        return result;
    }
    
    public static Position getPositionFromBitmap(long bitmap) {
        Position result = new Position(0, 0);
        
        for (int i = 1; i < 65; i++) {
            if ((bitmap & 1) == 1) {
                result.setY((i - 1) % 8);
                
                break;
            }
            
            if ((i % 8) == 0) {
                result.setX(result.getX() + 1);
            }
            
            bitmap = bitmap >>> 1;
        }
        
        return result;
    }
    
    public static int getColorFromBitmap(long[][] board, long bitmap) {
        int result = -1;
        
        for (int i = 0; i < board.length; i++) {
            if ((board[i][0] & bitmap) != 0) {
                result = 0;
                
                break;
            } else if ((board[i][1] & bitmap) != 0) {
                result = 1;
                
                break;
            }
        }
        
        return result;
    }
    
    public static int getTypeFromBitmap(long[][] board, long bitmap) {
        int result = -1;
        
        for (int i = 0; i < board.length; i++) {
            if ((board[i][0] & bitmap) != 0) {
                result = i;
                
                break;
            } else if ((board[i][1] & bitmap) != 0) {
                result = i;
                
                break;
            }
        }
        
        return result;
    }
    
    public static Piece getPiece(long[][] board, Position position) {
        Piece result = null;
        
        if (Bitboard.occupied(board, position)) {
            long bitmap = Bitboard.getBitmapAtPosition(board, position);
            
            result = new Piece(Bitboard.getColorFromBitmap(board, bitmap), Bitboard.getTypeFromBitmap(board, bitmap));
            
            result.setPosition(position);
        }
        
        return result;
    }
    
    public static long getMoves(long[][] board, Piece piece) {
        long result = 0;
        
        if (piece == null) {
            return result;
        }
        
        /* What piece are we looking for? */
        switch (piece.getType()) {
            case 5:
                result = Pawn.getMoves(board, piece);
                
                break;
            case 4:
                result = Knight.getMoves(board, piece);
                
                break;
            case 3:
                result = Bishop.getMoves(board, piece);
                
                break;
            case 2:
                result = Rook.getMoves(board, piece);
                
                break;
            case 1:
                result = Queen.getMoves(board, piece);
                
                break;
            case 0:
                result = King.getMoves(board, piece);
                
                break;
            default:
                break;
        }
        
        /* Blocks squares held by a piece of the same color. */
        result = result & ~Bitboard.getBitmap(board, piece.getColor());
        
        return result;
    }
    
    public static long getY(int y) {
        return Bitmaps.FILES[y];
    }
    
    public static long getX(int x) {
        return Bitmaps.RANKS[x];
    }
    
    public static long getMaskAtPosition(Position position) {
        long result = 0;
        
        if ((getIndexAtPosition(position) < 64) && (getIndexAtPosition(position) > -1)) {
            result = Bitboard.masks[getIndexAtPosition(position)];
        }
        
        return result;
    }
    
    public static long[][] movePiece(long[][] board, Piece piece, Position position) {
        board[piece.getType()][piece.getColor()] = Bitboard.getBitmap(board, piece) | Bitboard.getMaskAtPosition(position);
        
        return board;
    }
    
    public static long[][] removePiece(long[][] board, Piece piece, Position position) {
        board[piece.getType()][piece.getColor()] = Bitboard.getBitmap(board, piece) & ~Bitboard.getMaskAtPosition(position);
        
        return board;
    }
    
    public static boolean occupied(long[][] board, Position position) {
        return (Bitboard.getBitmap(board) & Bitboard.getMaskAtPosition(position)) != 0;
    }
    
    public static boolean isPositionAttacked(long bitmap, Position position) {
        return (bitmap >>> (((position.getX() * 8) + position.getY())) & 1) == 1;
    }
    
    public static int getIndexAtPosition(Position position) {
        return (position.getX() << 3) + position.getY();
    }
    
    public static String getString(long bitmap) {
        String result = "";
        
        for (int i = 1; i < 65; i++) {
            if ((bitmap & 1) == 1) {
                result = result + "1";
            } else {
                result = result + "0";
            }
            
            if ((i % 8) == 0) {
                result = result + "\n";
            }
            
            bitmap = bitmap >>> 1;
        }
        
        return result;
    }
}

