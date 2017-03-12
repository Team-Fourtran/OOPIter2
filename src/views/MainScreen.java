package views;

import javax.swing.*;
import javax.swing.border.Border;

import models.assetOwnership.Observer;
import models.assetOwnership.TileAssociation;
import controllers.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyAdapter;
import models.playerAsset.Assets.PlayerAsset;
import java.util.*;

public class MainScreen implements Observer{
    private JFrame mainScreen;

    private final int EMPTY = 0;
    private final int BSIZE = 15;
    private final int HEXSIZE = 64;
    private final int BORDERS = 10;
    private final int SCRSIZE = HEXSIZE * (BSIZE + 1) + BORDERS*3;

    static final Color COLOURBACK =  Color.WHITE;
    static final Color COLOURCELL =  Color.ORANGE;
    static final Color COLOURGRID =  Color.BLACK;
    static final Color COLOURONE = new Color(255,223,255,200);
    static final Color COLOURTWO = new Color(0,0,0,124);

    private int[][] board = new int[BSIZE][BSIZE];

    private KeyPressInformer keyInformer;

    private TileAssociation[] tiles;
    private JTable unitTable;
    private JTabbedPane tabbedPane;
    private JTable strTable;
    private String[] unitColumnStats = {"UnitID", "Unit Type", "Offensive Damage",
            "Defensive Damage", "Armor",
            "Max Health", "Current Health", "Upkeep", "Location"};
    private String[] structureColumnStats = {"StructuresID", "Structure Type", "Offensive Damage",
            "Defensive Damage", "Armor", "Maximum Health",
            "Current Health", "Upkeep", "Location"};

    private int x = 0;
    private int y = 0;

    public MainScreen(TileAssociation[] tiles){
        this.tiles = tiles;
        for (int i = 0; i < tiles.length; i++) {
            tiles[i].addObserver(this);
        }
        Toolkit.getDefaultToolkit().sync();
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
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

    //For communication between CommandGenerator. Focusing on unit/army to highlight the tile.
    public void searchTileAssociation(PlayerAsset playerAsset){
        for(int i = 0; i < tiles.length; i++){
            if(tiles[i].isAssetOwner(playerAsset)){
                HexProperties hp = hexMech.getHexProperties(tiles[i]);
                this.x = hp.getX();
                this.y = hp.getY();
            }
        }
    }
    public void generateMainScreen(){
        DrawingPanel map = new DrawingPanel();
        map.setBackground(Color.blue);
        JScrollPane mapPane = new JScrollPane(map);
        Dimension d1 = new Dimension(1200, 1200);
        mapPane.setPreferredSize(d1);
        mainScreen = new JFrame("Fourtran Game");

        JPanel startScreen = new JPanel();

        tabbedPane = new JTabbedPane();

        Container content = mainScreen.getContentPane();
        content.add(new JButton("Something"), BorderLayout.SOUTH);
        //Unit OV Table
        Object[][] unitData = new Object[25][9];
        NonEditableTable table = new NonEditableTable(unitData, unitColumnStats);
        unitTable = new JTable(table);
        unitTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Dimension dimension = new Dimension(800, 500);
        unitTable.setPreferredSize(dimension);
        JPanel unitOVPanel = new JPanel(new BorderLayout());
        unitOVPanel.setPreferredSize(dimension);
        unitOVPanel.add(new JScrollPane(unitTable), BorderLayout.CENTER);
        Dimension buttonDimension = new Dimension(30, 30);
        JButton createArmyButton = new JButton("Create Army");
        createArmyButton.setPreferredSize(buttonDimension);
        unitOVPanel.add(createArmyButton, BorderLayout.SOUTH);

        //Structure OV Table
        Object[][] strData = new Object[25][9];
        NonEditableTable table1 = new NonEditableTable(strData, structureColumnStats);
        strTable = new JTable(table1);
        strTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        strTable.setPreferredSize(dimension);
        JPanel strOVPanel = new JPanel();
        strOVPanel.setPreferredSize(dimension);
        strOVPanel.add(new JScrollPane(strTable), BorderLayout.CENTER);
        tabbedPane.addTab("Map", mapPane);
        tabbedPane.addTab("Unit Overview", unitOVPanel);
        tabbedPane.addTab("Structure Overview", strOVPanel);
        //tabbedPane.setBackground(Color.black);
        content.add(tabbedPane, BorderLayout.CENTER);
        tabbedPane.setFocusable(false);
        mapPane.setFocusable(false);
        map.setFocusable(true);
        map.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentShown(ComponentEvent cEvt) {
                Component src = (Component) cEvt.getSource();
                src.requestFocusInWindow();
            }

        });




        mainScreen.setSize( (int)(SCRSIZE/1.23), SCRSIZE);
        mainScreen.setResizable(true);
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //mainScreen.setLocationRelativeTo(null);
    }

    class DrawingPanel extends JPanel{
        //        int xPoint = 100;
//        int yPoint = 100;
        //int x = 0;
        //int y = 0;
        private KeyBoardListener kBL;
        private boolean toggleHT;
        public DrawingPanel()
        {
            setBackground(COLOURBACK);
            setFocusable(true);
            requestFocusInWindow();
            toggleHT = true;
            kBL = new KeyBoardListener();
            addKeyListener(kBL);

        }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1200, 1200);
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
        public void enableHighlight(){
            toggleHT = true;
            addKeyListener(kBL);
        }
        public void disableHighlight(){
            toggleHT = false;
            removeKeyListener(kBL);
        }
        class KeyBoardListener extends KeyAdapter {
            public void keyPressed(KeyEvent e) {
                int id = e.getKeyCode();

                if(id == KeyEvent.VK_RIGHT){
                    //hexMech.highlight(560, 500);
                    /*xPoint += 45;
                    Point p = new Point( hexMech.pxtoHex(xPoint, 300 ));
                    x = p.x;
                    y = p.y;
                    System.out.println(xPoint);
                    System.out.println(p.x + ", " + p.y);*/
                    board[x][y] = 0;
                    x++;
                    board[x][y] = (int)' ';
                }
                if(id == KeyEvent.VK_LEFT){
                    board[x][y] = 0;
                    x--;
                    board[x][y] = (int)' ';
                }
                if(id == KeyEvent.VK_UP){
                    board[x][y] = 0;
                    y--;
                    board[x][y] = (int)' ';
                }
                if(id == KeyEvent.VK_DOWN){
                    board[x][y] = 0;
                    y++;
                    board[x][y] = (int)' ';
                }
                if(id == KeyEvent.VK_ESCAPE){
                    board[x][y] = 0;
                    disableHighlight();
                }
                repaint();
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