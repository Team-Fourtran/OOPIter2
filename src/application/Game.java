package application;

import controllers.KeyboardController;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.Player;
import views.*;

import java.util.ArrayList;

public class Game {
    private MainScreen mainScreen;
    private Player player1;
    private Player player2;

    public Game(ArrayList<TileAssociation> list, Player player1, Player player2) throws InterruptedException{

        TileAssociation[] _tiles = new TileAssociation[list.size()];
        _tiles = list.toArray(_tiles);

        this.player1 = player1;
        this.player2 = player2;

        mainScreen = new MainScreen(_tiles);
        mainScreen.initialize();
        mainScreen.generateMainScreen();
        mainScreen.showMainScreen();

        KeyboardController kbc = new KeyboardController(mainScreen.getKeyInformer(), player1.getAssetIterator());

        Thread.sleep(1000);
    }

}