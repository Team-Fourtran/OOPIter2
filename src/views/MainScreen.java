package views;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;

public class MainScreen{
    private JFrame mainScreen;
    
    final static int EMPTY = 0;
    final static int BSIZE = 30;
    final static int HEXSIZE = 40;
    final static int BORDERS = 15;
    final static int SCRSIZE = HEXSIZE * (BSIZE + 1) + BORDERS*3;

    final static Color COLOURBACK =  Color.WHITE;
    //final static BufferedImage WATER = ImageIO.read(application/views/waterHex.gif);
    final static Color COLOURCELL =  Color.ORANGE;
    final static Color COLOURGRID =  Color.BLACK;
    final static Color COLOURONE = new Color(255,223,255,200);
    final static Color COLOURTWO = new Color(0,0,0,124);

    private int[][] board = new int[BSIZE][BSIZE];

    public void showMainScreen(){
        mainScreen.setVisible(true);
    }
    public void initialize(){
        hexMech.setXYasVertex(false);
        hexMech.setHeight(HEXSIZE);
        hexMech.setBorders(BORDERS);

        for(int i = 0; i < BSIZE; i++){
            for(int j = 0; j < BSIZE; j++){
                board[i][j] = EMPTY;
            }
        }
    }
    public void generateMainScreen(){
        DrawingPanel panel = new DrawingPanel();

        mainScreen = new JFrame("Fourtran Game");
        Container content = mainScreen.getContentPane();
        content.add(panel);
        mainScreen.setSize( (int)(SCRSIZE/1.23), SCRSIZE);
        mainScreen.setResizable(true);
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //mainScreen.setLocationRelativeTo(null);
    }

    class DrawingPanel extends JPanel{
        public DrawingPanel()
        {
            setBackground(COLOURBACK);
        }
        public void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2.translate(-100, -100);
            super.paintComponent(g2);
            //draw grid
            for (int i=0;i<BSIZE;i++) {
                for (int j=0;j<BSIZE;j++) {
                    hexMech.drawHex(i,j,g2);
                }
            }
            
            //fill in hexes
            for (int i=0;i<BSIZE;i++) {
                for (int j=0;j<BSIZE;j++) {
                    hexMech.fillHex(i,j,board[i][j],g2);
                }
            }
        }
    }
}

