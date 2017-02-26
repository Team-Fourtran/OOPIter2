package application;

import models.assetOwnership.TileAssociation;
import views.*;

import java.util.ArrayList;

public class Game {
  private MainScreen mainScreen;
  
  public Game(ArrayList<TileAssociation> list) throws InterruptedException{

      TileAssociation[] _tiles = new TileAssociation[list.size()];
      _tiles = list.toArray(_tiles);

      mainScreen = new MainScreen(_tiles);
      mainScreen.initialize();
      mainScreen.generateMainScreen();
      mainScreen.showMainScreen();
      Thread.sleep(1000);
  }

}