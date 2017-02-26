package application;

import models.assetOwnership.TileAssociation;
import models.playerAsset.*;
import models.utility.*;
import models.assetOwnership.GameMap;
import views.*;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;
import models.utility.*;

public class Game {
//  private Player[] players;
//  private Map map;
//  private Player currentPlayer;
  private final int ROW = 30;
  private final int COL = 30;
//  private TileGen T;
  private MainScreen mainScreen;
  
  public Game(ArrayList<TileAssociation> list) throws InterruptedException{

      TileAssociation[] _tiles = new TileAssociation[list.size()];
      _tiles = list.toArray(_tiles);

      mainScreen = new MainScreen(_tiles);
      mainScreen.initialize();
      mainScreen.generateMainScreen();
      mainScreen.showMainScreen();
      
      Thread.sleep(5000);
      
      System.out.println("awake");
  }

}