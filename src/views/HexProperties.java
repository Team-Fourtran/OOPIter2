package views;

import java.awt.*;

public class HexProperties {
    private int x;
    private int y;
    private Graphics2D g2;

    public HexProperties(int x, int y, Graphics2D g2){
        this.x = x;
        this.y = y;
        this.g2 = g2;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public Graphics2D getGraphic(){
        return this.g2;
    }
}
