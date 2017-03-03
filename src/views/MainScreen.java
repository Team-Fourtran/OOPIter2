package views;

import javax.swing.*;

import models.assetOwnership.Observer;
import models.assetOwnership.TileAssociation;
import controllers.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MainScreen implements Observer {
    private JFrame mainScreen;
    
    final static int EMPTY = 0;
    final static int BSIZE = 15;
    final static int HEXSIZE = 64;
    final static int BORDERS = 10;
    final static int SCRSIZE = HEXSIZE * (BSIZE + 1) + BORDERS*3;

    final static Color COLOURBACK =  Color.WHITE;
    final static Color COLOURCELL =  Color.ORANGE;
    final static Color COLOURGRID =  Color.BLACK;
    final static Color COLOURONE = new Color(255,223,255,200);
    final static Color COLOURTWO = new Color(0,0,0,124);

    private int[][] board = new int[BSIZE][BSIZE];

    private KeyPressInformer keyInformer;

    public TileAssociation[] tiles;
    public MainScreen(TileAssociation[] tiles){
        this.tiles = tiles;
        for (int i = 0; i < tiles.length; i++) {
        	tiles[i].addObserver(this);
        }
    }

    public KeyPressInformer getKeyInformer(){
        return this.keyInformer;
    }
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

        HashMap<String, Boolean> keyMap = new HashMap<>();
        keyMap.put("ENTER",false);
        keyMap.put("CONTROL",false);
        keyMap.put("UP",false);
        keyMap.put("DOWN",false);
        keyMap.put("LEFT",false);
        keyMap.put("RIGHT",false);
        keyInformer = new KeyPressInformer(keyMap);
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
            setFocusable(true);
            requestFocusInWindow();
            KeyBoardListener kBL = new KeyBoardListener();
            addKeyListener(kBL);
        }
        class KeyBoardListener extends KeyAdapter {

            @Override
            public void keyReleased(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    keyInformer.update("RIGHT", false);
                } else if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    keyInformer.update("LEFT", false);
                } else if(e.getKeyCode() == KeyEvent.VK_UP){
                    keyInformer.update("UP", false);
                } else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    keyInformer.update("DOWN", false);
                } else if(e.getKeyCode() == KeyEvent.VK_CONTROL){
                    keyInformer.update("CONTROL", false);
                } else if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    keyInformer.update("ENTER", false);
                }
            }
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    keyInformer.update("RIGHT", true);
                } else if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    keyInformer.update("LEFT", true);
                } else if(e.getKeyCode() == KeyEvent.VK_UP){
                    keyInformer.update("UP", true);
                } else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    keyInformer.update("DOWN", true);
                } else if(e.getKeyCode() == KeyEvent.VK_CONTROL){
                    keyInformer.update("CONTROL", true);
                } else if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    keyInformer.update("ENTER", true);
                }
            }

        }
        public void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2.translate(-10, -10);
            super.paintComponent(g2);
            //draw grid
            int ind = 0;
            for (int i=0;i<BSIZE;i++) {
                for (int j=0;j<BSIZE;j++) {
                    hexMech.drawHex(i,j,g2, tiles[ind]);
                    ind++;
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
    
    public void updateMainScreen() {
    	mainScreen.repaint();
    }

	@Override
	public void update(TileAssociation t) {
		hexMech.updateTile(t);
		mainScreen.repaint();
	}

}

