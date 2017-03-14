package views;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import models.assetOwnership.TileAssociation;
import models.assetOwnership.TileObserver;
import controllers.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyAdapter;

import models.playerAsset.Assets.PlayerAsset;

import java.util.*;

public class MainScreen implements TileObserver {
    private JFrame mainScreen;

    private final int EMPTY = 0;
    private final int BSIZE = 10;
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
    private UnitOverview unitTable;
    private JTabbedPane tabbedPane;
    private StructureOverview strTable;

    private int x = 0;
    private int y = 0;
    private int tileAssociationIndex;

    TileTargetting tileReceiver;

    private DrawingPanel map;
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

    public TileTargetting getTileTargetter(){
        return tileReceiver;
    }
    public void showMainScreen(){
        mainScreen.setVisible(true);
//        StructureCreationDialog structureCreationDialog = new StructureCreationDialog();
//        structureCreationDialog.createDialog();
//        String structure = structureCreationDialog.returnStructureType();
//        System.out.println(structure);
//        structureCreationDialog.closeDialog();
//        structureCreationDialog.createDialog();
//        structure = structureCreationDialog.returnStructureType();
//        System.out.println(structure);
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
        tileReceiver = new TileTargetting(this);
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
        map = new DrawingPanel();
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

        Dimension d = new Dimension(900, 700);
        unitTable = new UnitOverview(d);
        strTable = new StructureOverview(d);

        tabbedPane.addTab("Map", mapPane);
        tabbedPane.addTab("Unit Overview", unitTable);
        tabbedPane.addTab("Structure Overview", strTable);

        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
            }
        };

        tabbedPane.addChangeListener(changeListener);
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

    }

    class DrawingPanel extends JPanel{
        private TileHighlightListener tHL;
        private CommandListener commandListener;
        private boolean toggleHT;
        public DrawingPanel(){
            setBackground(COLOURBACK);
            setFocusable(true);
            requestFocusInWindow();
            toggleHT = false;
            commandListener = new CommandListener();
            tHL = new TileHighlightListener();
            addKeyListener(commandListener);
        }
        @Override
//        public Dimension getPreferredSize() {
//            return new Dimension(1200, 1200);
//        }
        public void paintComponent(Graphics g){
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
            addKeyListener(tHL);
        }
        public void disableHighlight(){
            toggleHT = false;
            removeKeyListener(tHL);
        }

        public void enableCommand(){
            addKeyListener(commandListener);
        }
        public void disableCommand(){
            removeKeyListener(commandListener);
        }

        class CommandListener extends KeyAdapter{
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
                if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    keyInformer.update("CONTROL", true);
                } else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    keyInformer.update("RIGHT", true);
                } else if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    keyInformer.update("LEFT", true);
                } else if(e.getKeyCode() == KeyEvent.VK_UP){
                    keyInformer.update("UP", true);
                } else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    keyInformer.update("DOWN", true);
                } else if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    keyInformer.update("ENTER", true);
                }
            }
        }
        class TileHighlightListener extends KeyAdapter {

            public void keyPressed(KeyEvent e) {
                int id = e.getKeyCode();

                if(id == KeyEvent.VK_Q || id == KeyEvent.VK_NUMPAD7){
                    board[x][y] = 0;
                    if(x % 2 == 0 || x == 0){
                        x = (x-1 < 0)? BSIZE-1 : x-1;
                        y = (y-1 < 0)? BSIZE-1 : y-1;
                    } else {
                        x = (x-1 < 0)? BSIZE-1 : x-1;
                    }
                    board[x][y] = (int)' ';
                }
                if(id == KeyEvent.VK_W || id == KeyEvent.VK_NUMPAD8){
                    board[x][y] = 0;
                    y = (y-1 < 0)? BSIZE-1 : y-1;
                    board[x][y] = (int)' ';
                }
                if(id == KeyEvent.VK_E || id == KeyEvent.VK_NUMPAD9){
                    board[x][y] = 0;
                    if(x % 2 == 0 || x == 0){
                        x = (x+1) % BSIZE;
                        y = (y-1 < 0)? BSIZE-1 : y-1;
                    } else{
                        x = (x+1) % BSIZE;
                    }

                    board[x][y] = (int)' ';
                }
                if(id == KeyEvent.VK_A || id == KeyEvent.VK_NUMPAD1){
                    board[x][y] = 0;
                    if(x % 2 == 1 || x == 0) {
                        y = (y+1) % BSIZE;
                        x = (x - 1 < 0) ? BSIZE - 1 : x - 1;
                    } else{
                        x = (x - 1 < 0) ? BSIZE - 1 : x - 1;
                    }
                    board[x][y] = (int)' ';
                }
                if(id == KeyEvent.VK_S || id == KeyEvent.VK_NUMPAD2){
                    board[x][y] = 0;
                    y = (y+1) % BSIZE;
                    board[x][y] = (int)' ';
                }
                if(id == KeyEvent.VK_D || id == KeyEvent.VK_NUMPAD3){
                    board[x][y] = 0;
                    if(x % 2 == 1 || x == 0) {
                        y = (y+1) % BSIZE;
                        x = (x+1) % BSIZE;
                    } else{
                        x = (x+1) % BSIZE;
                    }
                    board[x][y] = (int)' ';
                }
                if(id == KeyEvent.VK_ESCAPE){
                    board[x][y] = 0;
                    disableHighlight();
                    enableCommand();
                    tileReceiver.receiveTile(null);
                }

                if(id == KeyEvent.VK_ENTER){
                    board[x][y] = 0;
                    tileAssociationIndex = (x*BSIZE) + y;
                    disableHighlight();
                    enableCommand();
                    tileReceiver.receiveTile(tiles[tileAssociationIndex]);
                }
                repaint();
            }
        }
    }

    public void doTileTargetting(TileTargetting ttr, PlayerAsset startingHighlight){
        tileReceiver = ttr;

        /* Disable command cycling until we press Enter */
        map.disableCommand();

        /* Enable highlighting */
        map.enableHighlight();

        //Key listeners will call receiveTile on tileReceiver

    }

    public void updateMainScreen() {
        mainScreen.repaint();
    }

    @Override
    public void updateAdd(TileAssociation t, PlayerAsset p) {
    	updateTile(t);
    }
    
    public void updateRemove(TileAssociation t, PlayerAsset p) {
    	updateTile(t);
    }
    
    public void updateTile(TileAssociation t) {
        hexMech.updateTile(t);
        mainScreen.repaint();
    }
}