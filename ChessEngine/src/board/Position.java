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


public class Position {
   
    
    private int x, y;
    
    public Position() {
        this.x = 0;
        this.y = 0;
    }
    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        
        if (object == null) {
            return false;
        }
        
        if (object instanceof Position) {
            Position position = (Position)object;
            
            return (position.getX() == this.x) && (position.getY() == this.y);
        }
        
        return false;
    }
    
    public int hashCode() {
        int hash = 42;
        
        hash = 31 * hash + x;
        hash = 31 * hash + y;
        
        return hash;
    }
    
    public String toString() {
        String result = "";
        
        switch (this.y) {
            case 0:
                result = result + "a";
                
                break;
            case 1:
                result = result + "b";
                
                break;
            case 2:
                result = result + "c";
                
                break;
            case 3:
                result = result + "d";
                
                break;
            case 4:
                result = result + "e";
                
                break;
            case 5:
                result = result + "f";
                
                break;
            case 6:
                result = result + "g";
                
                break;
            case 7:
                result = result + "h";
                
                break;
            default:
                break;
        }
        
        switch (this.x) {
            case 0:
                result = result + "8";
                
                break;
            case 1:
                result = result + "7";
                
                break;
            case 2:
                result = result + "6";
                
                break;
            case 3:
                result = result + "5";
                
                break;
            case 4:
                result = result + "4";
                
                break;
            case 5:
                result = result + "3";
                
                break;
            case 6:
                result = result + "2";
                
                break;
            case 7:
                result = result + "1";
                
                break;
            default:
                break;
        }
        
        return result;
    }
}

