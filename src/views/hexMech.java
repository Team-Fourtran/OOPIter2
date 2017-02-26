package views;

import java.awt.*;
import models.assetOwnership.Observer;
import models.assetOwnership.TileAssociation;
import java.util.HashMap;
import java.util.Observable;
import models.visitor.TileDrawingVisitor;
import models.visitor.TileVisitor;

public class hexMech implements Observer{

    public static boolean XYVertex=true;	//true: x,y are the co-ords of the first vertex.
    //false: x,y are the co-ords of the top left rect. co-ord.

    private static int BORDERS;	//default number of pixels for the border.

    private static int s=0;	// length of one side
    private static int t=0;	// short side of 30o triangle outside of each hex
    private static int r=0;	// radius of inscribed circle (centre to middle of each side). r= h/2
    private static int h=0;	// height. Distance between centres of two adjacent hexes. Distance between two opposite sides in a hex.
    private Observable observable = null;
    private static HashMap<TileAssociation, HexProperties> gps = new HashMap<>();
    public void update(TileAssociation tA){

    }
    public static void setXYasVertex(boolean b) {
        XYVertex=b;
    }
    public static void setBorders(int b){
        BORDERS=b;
    }
    /** This functions takes the Side length in pixels and uses that as the basic dimension of the hex.
     It calculates all other needed constants from this dimension.
     */
    public static void setHeight(int height) {
        h = height;			// h = basic dimension: height (distance between two adj centresr aka size)
        r = h/2;			// r = radius of inscribed circle
        s = (int) (h / 1.73205);	// s = (h/2)/cos(30)= (h/2) / (sqrt(3)/2) = h / sqrt(3)
        t = (int) (r / 1.73205);	// t = (h/2) tan30 = (h/2) 1/sqrt(3) = h / (2 sqrt(3)) = r / sqrt(3)
    }

    /*********************************************************
     Name: hex()
     Parameters: (x0,y0) This point is normally the top left corner
     of the rectangle enclosing the hexagon.
     However, if XYVertex is true then (x0,y0) is the vertex of the
     top left corner of the hexagon.
     Returns: a polygon containing the six points.
     Called from: drawHex(), fillhex()
     Purpose: This function takes two points that describe a hexagon
     and calculates all six of the points in the hexagon.
     *********************************************************/
    public static Polygon hex (int x0, int y0) {

        int y = y0 + BORDERS;
        int x = x0 + BORDERS; // + (XYVertex ? t : 0); //Fix added for XYVertex = true.
        // NO! Done below in cx= section
        if (s == 0  || h == 0) {
            System.out.println("ERROR: size of hex has not been set");
            return new Polygon();
        }

        int[] cx,cy;

//I think that this XYvertex stuff is taken care of in the int x line above. Why is it here twice?
        if (XYVertex)
            cx = new int[] {x,x+s,x+s+t,x+s,x,x-t};  //this is for the top left vertex being at x,y. Which means that some of the hex is cutoff.
        else
            cx = new int[] {x+t,x+s+t,x+s+t+t,x+s+t,x+t,x};	//this is for the whole hexagon to be below and to the right of this point

        cy = new int[] {y,y,y+r,y+r+r,y+r+r,y+r};
        return new Polygon(cx,cy,6);
    }

    /********************************************************************
     Name: drawHex()
     Parameters: (i,j) : the x,y coordinates of the inital point of the hexagon
     g2: the Graphics2D object to draw on.
     Returns: void
     Calls: hex()
     Purpose: This function draws a hexagon based on the initial point (x,y).
     The hexagon is drawn in the colour specified in MainScreen.COLOURELL.
     *********************************************************************/
    public static void drawHex(int i, int j, Graphics2D g2, TileAssociation tileAssoc) {
        int x = i * (s+t);
        int y = j * h + (i%2) * h/2;
        Polygon poly = hex(x,y);

        HexProperties h = new HexProperties(x, y, g2);
        
        gps.put(tileAssoc, h);

        //g2.drawImage(MainScreen.WATER, 0, 0, null);
        g2.setColor(MainScreen.COLOURCELL);
        g2.drawPolygon(poly);
    	g2.fillPolygon(poly);

        TileDrawingVisitor v = new TileDrawingVisitor(x, y, g2);
        tileAssoc.accept(v);
        v.drawTile();
    }

    /***************************************************************************
     * Name: fillHex()
     * Parameters: (i,j) : the x,y coordinates of the initial point of the hexagon
     n   : an integer number to indicate a letter to draw in the hex
     g2  : the graphics context to draw on
     * Return: void
     * Called from:
     * Calls: hex()
     *Purpose: This draws a filled in polygon based on the coordinates of the hexagon.
     The colour depends on whether n is negative or positive.
     The colour is set by MainScreen.COLOURONE and MainScreen.COLOURTWO.
     The value of n is converted to letter and drawn in the hexagon.
     *****************************************************************************/
    public static void fillHex(int i, int j, int n, Graphics2D g2) {
        char c= 'o';
        int x = i * (s+t);
        int y = j * h + (i%2) * h/2;
        if (n < 0) {
            g2.setColor(MainScreen.COLOURONE);
            g2.fillPolygon(hex(x,y));
            c = (char)(-n);
            g2.drawString(""+c, x+r+BORDERS, y+r+BORDERS+4);
        }
        if (n > 0) {
            g2.setColor(MainScreen.COLOURTWO);
            g2.fillPolygon(hex(x,y));
            c = (char)n;
            g2.drawString(""+c, x+r+BORDERS, y+r+BORDERS+4);
        }
    }
    
	public static void updateTile(TileAssociation t2) {
		HexProperties p = gps.get(t2);
		TileDrawingVisitor v = new TileDrawingVisitor(p.getX(), p.getY(), p.getGraphic());
		t2.accept(v);
		v.drawTile();
	}

}
