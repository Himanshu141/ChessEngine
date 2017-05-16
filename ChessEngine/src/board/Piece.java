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



 


public class Piece {
    
    /*The integer "type" here represents type of piece. 
        King = 0 , Queen = 1 , Rook = 2 , Bishop = 3 , Knight = 4 , Pawn = 5
    */
   
    private Position position;
    private int color;
    private int type;
    
 
    public Piece() {
        this.position = null;
        this.color    = -1;
        this.type     = -1;
    }
    
    public Piece(Position position) {
        this.position = position;
        this.color    = -1;
        this.type     = -1;
    }
    
    public Piece(int color, int type) {
        this.position = null;
        this.color    = color;
        this.type     = type;
    }
    
    public Position getPosition() {
        return this.position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public void setColor(int color) {
        this.color = color;
    }
    
    public int getType() {
        return this.type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    
    
    public String toString() {
        String result = "Piece (Color: ";
        
        if (this.color == 0) {
            result = result + "White";
        } else {
            result = result + "Black";
        }
        
        result = result + ", Type: ";
        
        switch(this.type) {
            case 0:
                result = result + "Normal";
                
                break;
            case 1:
                result = result + "King";
                
                break;
            default:
                result = result + "Unknown";
                
                break;
        }
        
        result = result + ", Position: " + this.position + ")";
        
        return result;
    }
    
    private String getIdentifier() {
        return (String.valueOf((char)(66 + ((this.color * 20) + (this.color * 1)))) + String.valueOf(this.type));
    }
    
    
}

