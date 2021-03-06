/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template y, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

/**
 *
 * @author himanshu
 * SUPPORT https://chessprogramming.wikispaces.com/

 */
import board.Bitboard;
import board.Piece;
import board.Position;

public class Evaluator {
    
    private static class Regions {
        public static long CENTER = 0x0000001818000000L;
    }
    
    private static class Strengths {
        
        private static final int[][][] Pawn = {
            {
                { /* Black */
                     0,  0,  0,  0,  0,  0,  0,  0
                }, {
                    10, 16, 24, 32, 32, 24, 16, 10
                }, {
                    10, 16, 24, 32, 32, 24, 16, 10
                }, {
                     6, 12, 18, 28, 28, 16, 12,  6
                }, {
                     4,  8, 18, 26, 26, 16,  8,  4
                }, {
                     4,  6, 12, 12, 12,  4,  6,  4
                }, {
                     2,  4,  4,  0,  0,  4,  4,  2
                }, {
                     0,  0,  0,  0,  0,  0,  0,  0
                }
            }, {
                { /* White */
                     0,  0,  0,  0,  0,  0,  0,  0
                }, {
                     2,  4,  4,  0,  0,  4,  4,  2
                }, {
                     4,  6, 12, 12, 12,  4,  6,  4
                }, {
                     4,  8, 18, 26, 26, 16,  8,  4
                }, {
                     6, 12, 18, 28, 28, 16, 12,  6
                }, {
                    10, 16, 24, 32, 32, 24, 16, 10
                }, {
                    10, 16, 24, 32, 32, 24, 16, 10
                }, {
                     0,  0,  0,  0,  0,  0,  0,  0
                }
            }
        };
        
        private static final int[][][] Knight = {
            {
                { /* Black */
                    -8, -4,  2,  4,  4,  2, -4, -8
                }, {
                     2,  6, 14, 20, 20, 14,  6,  2
                }, {
                     6, 14, 28, 32, 32, 28, 14,  6
                }, {
                     8, 18, 30, 32, 32, 30, 18,  8
                }, {
                     8, 18, 26, 30, 30, 26, 18,  8
                }, {
                     6, 14, 24, 22, 22, 24, 14,  6
                }, {
                     2,  6, 14, 20, 20, 14,  6,  2
                }, {
                    -8, -4,  2,  4,  4,  2, -4, -8
                }
            }, {
              { /* White */
                    -8, -4,  2,  4,  4,  2, -4, -8
                }, {
                     2,  6, 14, 20, 20, 14,  6,  2
                }, {
                     6, 14, 24, 22, 22, 24, 14,  6
                }, {
                     8, 18, 26, 30, 30, 26, 18,  8
                }, {
                     8, 18, 30, 32, 32, 30, 18,  8
                }, {
                     6, 14, 28, 32, 32, 28, 14,  6
                }, {
                     2,  6, 14, 20, 20, 14,  6,  2
                }, {
                    -8, -4,  2,  4,  4,  2, -4, -8
                }
            }
        };
        
        private static final int[][][] Bishop = {
            {
                { /* Black */
                    16, 16, 16, 16, 16, 16, 16, 16
                }, {
                    16, 30, 32, 32, 32, 32, 30, 16
                }, {
                    16, 28, 32, 32, 32, 32, 28, 16
                }, {
                    16, 26, 32, 32, 32, 32, 26, 16
                }, {
                    16, 26, 32, 32, 32, 32, 26, 16
                }, {
                    26, 28, 32, 32, 32, 32, 28, 26
                }, {
                    26, 30, 32, 32, 32, 30, 29, 26
                }, {
                    16, 16, 16, 16, 16, 16, 16, 16
                }
            }, {
                { /* White */
                    16, 16, 16, 16, 16, 16, 16, 16
                }, {
                    26, 30, 32, 32, 32, 30, 29, 26
                }, {
                    26, 28, 32, 32, 32, 32, 28, 26
                }, {
                    16, 26, 32, 32, 32, 32, 26, 16
                }, {
                    16, 26, 32, 32, 32, 32, 26, 16
                }, {
                    16, 28, 32, 32, 32, 32, 28, 16
                }, {
                    16, 30, 32, 32, 32, 32, 30, 16
                }, {
                    16, 16, 16, 16, 16, 16, 16, 16
                }
            }
        };
        
        private static final int[][][] Rook = {
            {
                { /* Black */
                     0,  0,  0,  0,  0,  0,  0,  0
                }, {
                    10, 10, 10, 10, 10, 10, 10, 10
                }, {
                    -2,  0,  0,  0,  0,  0,  0, -2
                }, {
                    -2,  0,  0,  0,  0,  0,  0, -2
                }, {
                    -2,  0,  0,  0,  0,  0,  0, -2
                }, {
                    -2,  0,  0,  0,  0,  0,  0, -2
                }, {
                    -2,  0,  0,  0,  0,  0,  0, -2
                }, {
                     0,  0,  0,  3,  3,  0,  0,  0
                }
            }, {
                { /* White */
                     0,  0,  0,  3,  3,  0,  0,  0
                }, {
                    -2,  0,  0,  0,  0,  0,  0, -2
                }, {
                    -2,  0,  0,  0,  0,  0,  0, -2
                }, {
                    -2,  0,  0,  0,  0,  0,  0, -2
                }, {
                    -2,  0,  0,  0,  0,  0,  0, -2
                }, {
                    -2,  0,  0,  0,  0,  0,  0, -2
                }, {
                    10, 10, 10, 10, 10, 10, 10, 10
                }, {
                     0,  0,  0,  0,  0,  0,  0,  0
                }
            }
        };
        
        private static final int[][][] Queen = {
            {
                { /* Black */
                    -2, -2,  0,  0,  0,  0,  0,  0
                }, {
                    -2, -2,  0,  0,  0,  0,  0,  0
                }, {
                    -2, -2,  0,  0,  0,  0,  0,  0
                }, {
                     0,  0,  0,  4,  4,  0,  0,  0
                }, {
                     0,  0,  0,  4,  4,  0,  0,  0
                }, {
                     0,  2,  2,  2,  2,  0,  0,  0
                }, {
                     0,  0,  2,  2,  2,  0,  0,  0
                }, {
                    -2, -2, -2,  0,  0, -2, -2, -2
                }
            }, {
                { /* White */
                    -2, -2, -2,  0,  0, -2, -2, -2
                }, {
                     0,  0,  2,  2,  2,  0,  0,  0
                }, {
                     0,  2,  2,  2,  2,  0,  0,  0
                }, {
                     0,  0,  0,  4,  4,  0,  0,  0
                }, {
                     0,  0,  0,  4,  4,  0,  0,  0
                }, {
                    -2, -2,  0,  0,  0,  0,  0,  0
                }, {
                    -2, -2,  0,  0,  0,  0,  0,  0
                }, {
                    -2, -2,  0,  0,  0,  0,  0,  0
                }
            }
        };
        
        private static final int[][][] King = {
            {
                { /* Black */
                   -20, -20, -20, -20, -20, -20, -20, -20
                }, {
                   -20, -20, -20, -20, -20, -20, -20, -20
                }, {
                   -20, -20, -20, -20, -20, -20, -20, -20
                }, {
                   -20, -20, -20, -20, -20, -20, -20, -20
                }, {
                   -20, -20, -20, -20, -20, -20, -20, -20
                }, {
                    -8, -16, -16, -16, -16, -16, -16,  -8
                }, {
                    -6,  -6, -12, -12, -12, -12,  -6,  -6
                }, {
                     4,   4,   8, -12,  -8, -12,  10,   6
                }
            }, {
                { /* White */
                     4,   4,   8, -12,  -8, -12,  10,   6
                }, {
                    -6,  -6, -12, -12, -12, -12,  -6,  -6
                }, {
                    -8, -16, -16, -16, -16, -16, -16,  -8
                }, {
                   -20, -20, -20, -20, -20, -20, -20, -20
                }, {
                   -20, -20, -20, -20, -20, -20, -20, -20
                }, {
                   -20, -20, -20, -20, -20, -20, -20, -20
                }, {
                   -20, -20, -20, -20, -20, -20, -20, -20
                }, {
                   -20, -20, -20, -20, -20, -20, -20, -20
                }
            }
        };
    }
    
    public static boolean isCheck(long[][] board, int color) {
        long king = Bitboard.getBitmap(board, color, 0);
        long result = 0;
        
        if (color == 0) {
            color = 1;
        } else {
            color = 0;
        }
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position position = new Position(i, j);
                Piece current = null;
                
                if (!Bitboard.occupied(board, position)) {
                    continue;
                }
                
                current = Bitboard.getPiece(board, position);
                
                if (current.getColor() == color) {
                    result = king & Bitboard.getMoves(board, current);
                    
                    if (result != 0) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    public static int getMaterialValue(Piece piece) {
        int result = 0;
        
        if (piece != null) {
            switch (piece.getType()) {
                case 5:
                    result = 250;
                    
                    break;
                case 4:
                    result = 500;
                    
                    break;
                case 3:
                    result = 650;
                    
                    break;
                case 2:
                    result = 750;
                    
                    break;
                case 1:
                    result = 900;
                    
                    break;
                case 0:
                    result = 1000;
                    
                    break;
                default:
                    break;
            }
        }
        
        return result;
    }
    
    public static int evaluate(long[][] board, int color) {
        int result = 0;
        
        if (Evaluator.isCheck(board, color)) {
            return 0;
        } 
        
        /* Compute score on material and piece positioning. */
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece current = Bitboard.getPiece(board, new Position(i, j));
                
                if ((current != null) && (current.getColor() == color)) {
                    int value = Evaluator.getMaterialValue(current);
                    
                    switch (current.getType()) {
                        case 5:
                            value = value + (Strengths.Pawn[color][i][j] * 100);
                            
                            break;
                        case 4:
                            value = value + (Strengths.Knight[color][i][j] * 100);
                            
                            break;
                        case 3:
                            value = value + (Strengths.Bishop[color][i][j] * 100);
                            
                            break;
                        case 2:
                            value = value + (Strengths.Rook[color][i][j] * 100);
                            
                            break;
                        case 1:
                            value = value + (Strengths.Queen[color][i][j] * 100);
                            
                            break;
                        default:
                            value = value + (Strengths.King[color][i][j] * 100);
                            
                            break;
                    }
                    
                    /* Control of center bonus. */
                    if ((Bitboard.getMoves(board, current) & Regions.CENTER) != 0) {
                        value = value + 1000;
                    }
                    
                    result = result + value;
                }
            }
        }
        
        return result;
    }
}

