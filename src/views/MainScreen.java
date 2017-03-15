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
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.PlayerAsset;
import models.playerAsset.Iterators.AssetIterator;
import models.playerAsset.Iterators.TypeIterator2;

import java.util.*;

public class MainScreen implements TileObserver {
    private JFrame mainScreen;
    private Player currentPlayer;
    private PlayerInfoArea playerInfoArea;
    private ControllerInfoArea controllerInfoArea;

    private final int EMPTY = 0;
    private final int BSIZE = 10;
    private final int HEXSIZE = 64;
    private final int BORDERS = 10;
    private final int SCRSIZE = HEXSIZE * (BSIZE + 1) + BORDERS*3;

    static final Color COLOURBACK =  Color.WHITE;
    static final Color COLOURCELL =  Color.BLACK;

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
    AssetTargetting assetReceiver;

    private DrawingPanel map;

    public MainScreen(Player p, TileAssociation[] tiles){
        this.currentPlayer = p;
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

    public void updatePlayer(Player p){
        this.currentPlayer = p;
        playerInfoArea.update(currentPlayer);
    }

    public KeyPressInformer getKeyInformer(){
        return this.keyInformer;
    }
    public TurnSwitchNotifier getTurnSwitchNotifier(){
        return playerInfoArea.getTurnSwitchNotifier();
    }

    public TileTargetting getTileTargetter(){
        return tileReceiver;
    }
    public AssetTargetting getAssetTargetter(){
        return assetReceiver;
    }
    public void showMainScreen(){
        mainScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainScreen.setVisible(true);

        //FullScreen
        mainScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        tileReceiver = new TileTargetting(this);
        assetReceiver = new AssetTargetting(this);
    }

    //For communication between CommandGenerator. Focusing on unit/army to highlight the tile.
    public void highlightAsset(PlayerAsset playerAsset){
        map.isHighlighted = false;
        for(int i = 0; i < tiles.length; i++){
            if(tiles[i].isAssetOwner(playerAsset)){
                HexProperties hp = hexMech.getHexProperties(tiles[i]);
                this.x = hp.getActualX();
                this.y = hp.getActualY();
                map.isHighlighted = true;
                map.repaint();
                return;
            }
        }
    }
    public void stopHighlightingAsset(){
        board[x][y] = 0;
    }
    public void generateMainScreen(){
        map = new DrawingPanel();
        map.setBackground(Color.blue);
        JScrollPane mapPane = new JScrollPane(map);
        //Dimension d1 = new Dimension(1200, 1200);
        //mapPane.setPreferredSize(d1);
        mainScreen = new JFrame("Fourtran Game");

        JPanel startScreen = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        tabbedPane = new JTabbedPane();

        Container content = mainScreen.getContentPane();
        content.setLayout(new GridBagLayout());

        //Controller information area
        this.controllerInfoArea = new ControllerInfoArea(content);

        //Player information area with resources
        this.playerInfoArea = new PlayerInfoArea(content, new TurnSwitchNotifier());
        playerInfoArea.update(currentPlayer);

        //Unit OV Table
        Dimension d = new Dimension(1400, 700);

        unitTable = new UnitOverview(d);
        strTable = new StructureOverview(d);

        tabbedPane.addTab("Map", mapPane);
        tabbedPane.addTab("Unit Overview", unitTable);
        tabbedPane.addTab("Structure Overview", strTable);
        //tabbedPane.setPreferredSize(d1);
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                if (sourceTabbedPane.getSelectedComponent() instanceof DataTable){
                    ((DataTable)sourceTabbedPane.getSelectedComponent()).update(currentPlayer);
                }
                System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
            }
        };
        tabbedPane.addChangeListener(changeListener);

        c.gridwidth = 4;
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        content.add(tabbedPane, c);

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
        mainScreen.pack();
        mainScreen.setResizable(true);
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    class DrawingPanel extends JPanel{
        private TileHighlightListener tHL;
        private CommandListener commandListener;
        private boolean toggleHT;
        protected boolean isHighlighted;
        protected Graphics2D gg2;
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
        public Dimension getPreferredSize() {
            return new Dimension(600, 700);
        }
        public void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D)g;
            this.gg2 = g2;
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
            if(isHighlighted){
                int h = HEXSIZE;
                int r = h/2;
                int s = (int) (h / 1.73205);
                int t = (int) (r / 1.73205);
                int i = x * (s+t);
                int j = y * h + (x%2) * h/2;

                Polygon poly = hexMech.hex(i,j);
                Stroke oldStroke = g2.getStroke();
                g2.setStroke(new BasicStroke(10));
                g2.setColor(Color.yellow);
                g2.drawPolygon(poly);
                g2.setStroke(oldStroke);
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
                    if(x % 2 == 0 || x == 0){
                        x = (x-1 < 0)? BSIZE-1 : x-1;
                        y = (y-1 < 0)? BSIZE-1 : y-1;
                    } else {
                        x = (x-1 < 0)? BSIZE-1 : x-1;
                    }
                    isHighlighted = true;
                }
                if(id == KeyEvent.VK_W || id == KeyEvent.VK_NUMPAD8){
                    y = (y-1 < 0)? BSIZE-1 : y-1;
                    isHighlighted = true;
                }
                if(id == KeyEvent.VK_E || id == KeyEvent.VK_NUMPAD9){
                    if(x % 2 == 0 || x == 0){
                        x = (x+1) % BSIZE;
                        y = (y-1 < 0)? BSIZE-1 : y-1;
                    } else{
                        x = (x+1) % BSIZE;
                    }
                    isHighlighted = true;
                }
                if(id == KeyEvent.VK_A || id == KeyEvent.VK_NUMPAD1){
                    if(x % 2 == 1 || x == 0) {
                        y = (y+1) % BSIZE;
                        x = (x - 1 < 0) ? BSIZE - 1 : x - 1;
                    } else{
                        x = (x - 1 < 0) ? BSIZE - 1 : x - 1;
                    }
                    isHighlighted = true;
                }
                if(id == KeyEvent.VK_S || id == KeyEvent.VK_NUMPAD2){
                    y = (y+1) % BSIZE;
                    isHighlighted = true;
                }
                if(id == KeyEvent.VK_D || id == KeyEvent.VK_NUMPAD3){
                    if(x % 2 == 1 || x == 0) {
                        y = (y+1) % BSIZE;
                        x = (x+1) % BSIZE;
                    } else{
                        x = (x+1) % BSIZE;
                    }
                    isHighlighted = true;
                }
                if(id == KeyEvent.VK_ESCAPE){
                    disableHighlight();
                    enableCommand();
                    isHighlighted = false;
                    tileReceiver.receiveTile(null);
                }

                if(id == KeyEvent.VK_NUMPAD5 || id == KeyEvent.VK_ENTER){
                    tileAssociationIndex = (x*BSIZE) + y;
                    disableHighlight();
                    enableCommand();
                    isHighlighted = false;
                    tileReceiver.receiveTile(tiles[tileAssociationIndex]);
                }
                repaint();
            }
        }
    }

    class AssetCycleListener extends KeyAdapter {
        private TypeIterator2 iter;
        private PlayerAsset selected;

        public AssetCycleListener(TypeIterator2<PlayerAsset> iter){
            this.iter = iter;
            selected = iter.current();
        }

        public void keyPressed(KeyEvent e) {
            int id = e.getKeyCode();

            if(id == KeyEvent.VK_LEFT){
                System.out.println("LEFT");
                iter.prev();
                selected = (PlayerAsset) iter.current();
                System.out.println(selected.toString());
                highlightAsset(selected);
            }
            if(id == KeyEvent.VK_RIGHT){
                System.out.println("RIGHT");
                iter.next();
                selected = (PlayerAsset) iter.current();
                System.out.println(selected.toString());
                highlightAsset(selected);
            }

            if(id == KeyEvent.VK_ESCAPE){
                stopHighlightingAsset();
                map.removeKeyListener(this);
                map.enableCommand();
                assetReceiver.receiveAsset(null);
            }

            if(id == KeyEvent.VK_ENTER){
                stopHighlightingAsset();
                map.removeKeyListener(this);
                map.enableCommand();
                assetReceiver.receiveAsset(selected);
            }
            map.repaint();
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

    public void doAssetTargetting(AssetTargetting at){
        assetReceiver = at;
        TypeIterator2<PlayerAsset> iter = at.getIterator();
        iter.first();
        if (iter.current() == null){
            return;
        }
        map.disableCommand();
        map.addKeyListener(new AssetCycleListener(iter));
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

    public void updateControls(AssetIterator iter){
        highlightAsset((PlayerAsset) iter.current());
        controllerInfoArea.update(iter);
    }
}