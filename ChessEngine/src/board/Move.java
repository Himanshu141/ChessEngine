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


public class Move  {
    
    private Position x, y;
    private long score;
    
    public Move() {
        this.x = new Position();
        this.y = new Position();
        
        this.score = 0;
    }
    
    public Move(Position x, Position y) {
        this.x = x;
        this.y = y;
    }
    
    public Move(byte bytes[]) {
        this.x = new Position((int)bytes[0], (int)bytes[1]);
        this.y = new Position((int)bytes[2], (int)bytes[3]);
    }
    
    public Position getX() {
        return this.x;
    }
    
    public void setX(Position x) {
        this.x = x;
    }
    
    public Position getY() {
        return this.y;
    }
    
    public void setY(Position y) {
        this.y = y;
    }
    
    public long getScore() {
        return this.score;
    }
    
    public void setScore(long score) {
        this.score = score;
    }
    
    public String toString() {
        return this.x + " to " + this.y + " (" + this.score + ")";
    }
}
