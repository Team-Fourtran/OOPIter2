package views;

import java.awt.*;
import models.assetOwnership.TileAssociation;
import java.util.HashMap;
import models.visitor.*;

public class hexMech {

    public static boolean XYVertex=true;

    private static int BORDERS = 30;	//default number of pixels for the border.

    static int s=0;	// length of one side
    static int t=0;	// short side of 30o triangle outside of each hex
    static int r=0;	// radius of inscribed circle (centre to middle of each side). r= h/2
    static int h=0;	// height. Distance between centres of two adjacent hexes. Distance between two opposite sides in a hex.

    private static HashMap<TileAssociation, HexProperties> gps = new HashMap<>();
    private static Graphics2D gg;

    public static HexProperties getHexProperties(TileAssociation tileAssociation){
        return gps.get(tileAssociation);
    }
    public static void setXYasVertex(boolean b) {
        XYVertex=b;
    }
    public static void setBorders(int b){
        BORDERS=b;
    }

    public static void setHeight(int height) {
        h = height;			// h = basic dimension: height (distance between two adj centresr aka size)
        r = h/2;			// r = radius of inscribed circle
        s = (int) (h / 1.73205);	// s = (h/2)/cos(30)= (h/2) / (sqrt(3)/2) = h / sqrt(3)
        t = (int) (r / 1.73205);	// t = (h/2) tan30 = (h/2) 1/sqrt(3) = h / (2 sqrt(3)) = r / sqrt(3)
    }

    public static Polygon hex (int x0, int y0) {

        int y = y0 + BORDERS;
        int x = x0 + BORDERS;
        if (s == 0  || h == 0) {
            System.out.println("ERROR: size of hex has not been set");
            return new Polygon();
        }

        int[] cx,cy;

        if (XYVertex)
            cx = new int[] {x,x+s,x+s+t,x+s,x,x-t};  //this is for the top left vertex being at x,y. Which means that some of the hex is cutoff.
        else
            cx = new int[] {x+t,x+s+t,x+s+t+t,x+s+t,x+t,x};	//this is for the whole hexagon to be below and to the right of this point

        cy = new int[] {y,y,y+r,y+r+r,y+r+r,y+r};
        return new Polygon(cx,cy,6);
    }

    public static void drawHex(int i, int j, Graphics2D g2, TileAssociation tileAssoc) {
        int x = i * (s+t);
        int y = j * h + (i%2) * h/2;
        Polygon poly = hex(x,y);
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(10));

        HexProperties h = new HexProperties(x, y, i, j, g2);
        gps.put(tileAssoc, h);
        gg = g2;

        g2.setColor(Color.black);
        g2.drawPolygon(poly);

        TileDrawingVisitor v = new TileDrawingVisitor(x, y, g2);
        tileAssoc.accept(v);
        v.drawTile();
        g2.setStroke(oldStroke);

    }

    public static void updateTile(TileAssociation t2) {
        HexProperties p = gps.get(t2);
        TileDrawingVisitor v = new TileDrawingVisitor(p.getX(), p.getY(), p.getGraphic());
        t2.accept(v);
        v.drawTile();
    }

    public static Point pxtoHex(int mx, int my) {
        Point p = new Point(-1,-1);

        //correction for BORDERS and XYVertex
        mx -= BORDERS;
        my -= BORDERS;
        if (XYVertex) mx += t;

        int x = (int) (mx / (s+t)); //this gives a quick value for x. It works only on odd cols and doesn't handle the triangle sections. It assumes that the hexagon is a rectangle with width s+t (=1.5*s).
        int y = (int) ((my - (x%2)*r)/h); //this gives the row easily. It needs to be offset by h/2 (=r)if it is in an even column

        //dx,dy are the number of pixels from the hex boundary. (ie. relative to the hex clicked in)
        int dx = mx - x*(s+t);
        int dy = my - y*h;

        if (my - (x%2)*r < 0) return p; // prevent clicking in the open halfhexes at the top of the screen

        //System.out.println("dx=" + dx + " dy=" + dy + "  > " + dx*r/t + " <");

        //even columns
        if (x%2==0) {
            if (dy > r) {	//bottom half of hexes
                if (dx * r /t < dy - r) {
                    x--;
                }
            }
            if (dy < r) {	//top half of hexes
                if ((t - dx)*r/t > dy ) {
                    x--;
                    y--;
                }
            }
        } else {  // odd columns
            if (dy > h) {	//bottom half of hexes
                if (dx * r/t < dy - h) {
                    x--;
                    y++;
                }
            }
            if (dy < h) {	//top half of hexes
                //System.out.println("" + (t- dx)*r/t +  " " + (dy - r));
                if ((t - dx)*r/t > dy - r) {
                    x--;
                }
            }
        }
        p.x=x;
        p.y=y;
        return p;
    }
}