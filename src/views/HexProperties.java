package views;

import java.awt.*;

public class HexProperties {
    private int x;
    private int y;
    private int actualX;
    private int actualY;
    private Graphics2D g2;

    public HexProperties(int x, int y, int actualX, int actualY, Graphics2D g2){
        this.x = x;
        this.y = y;
        this.actualX = actualX;
        this.actualY = actualY;
        this.g2 = g2;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getActualX(){
        return this.actualX;
    }
    public int getActualY(){
        return this.actualY;
    }
    public Graphics2D getGraphic(){
        return this.g2;
    }
}
