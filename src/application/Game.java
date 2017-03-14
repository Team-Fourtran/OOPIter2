package application;

import controllers.KeyboardController;
import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.ctrlCommand.CTRLCommand;
import models.ctrlCommand.CommandNotConfiguredException;
import models.playerAsset.Assets.Player;
import views.*;

import java.util.ArrayList;

public class Game {

  private MainScreen mainScreen;
  private Player currentPlayer;
  private GameMap map;

  public Game(Player player, ArrayList<TileAssociation> list) throws InterruptedException{
      this.currentPlayer = player;
      this.map = new GameMap(list, 5, 5);

      TileAssociation[] _tiles = new TileAssociation[list.size()];
      _tiles = list.toArray(_tiles);

      mainScreen = new MainScreen(_tiles);
      mainScreen.initialize();
      mainScreen.generateMainScreen();
      mainScreen.showMainScreen();

      KeyboardController kbc = new KeyboardController(
              this,
              mainScreen.getKeyInformer(),
              currentPlayer.getAssetIterator(),
              mainScreen.getTileTargetter()
      );


      Thread.sleep(1000);
  }

  public GameMap getMap() {
	  return map;
  }
  
  public void setCurrentPlayer(Player p) {
	  this.currentPlayer = p;
  }
  
  public void notifyOfCommand(CTRLCommand cmd){
      try {
          cmd.execute(map, currentPlayer);
      } catch (CommandNotConfiguredException e) {
          e.printStackTrace();
      }
  }

}