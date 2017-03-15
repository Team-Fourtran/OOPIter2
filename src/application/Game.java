package application;

import com.sun.tools.javac.util.ArrayUtils;
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
  private Player[] players;
  private GameMap map;

  private KeyboardController kbc;

  public Game(Player player, Player enemyPlayer, ArrayList<TileAssociation> list) throws InterruptedException{
      /* Initialize Players array, and set the initial current player */
      this.players = new Player[2];
      this.players[0] = player;
      this.players[1] = enemyPlayer;
      this.currentPlayer = players[0];

      this.map = new GameMap(list, 5, 5);

      TileAssociation[] _tiles = new TileAssociation[list.size()];
      _tiles = list.toArray(_tiles);

      mainScreen = new MainScreen(_tiles);
      mainScreen.updatePlayer(currentPlayer);
      mainScreen.initialize();
      mainScreen.generateMainScreen();
      mainScreen.showMainScreen();

      this.kbc = new KeyboardController(
              this,
              mainScreen.getKeyInformer(),
              currentPlayer.makeIterator(),
              mainScreen.getTileTargetter()
      );
      Thread.sleep(1000);
  }

  public GameMap getMap() {
	  return map;
  }

  public void turnSwitch(){
      togglePlayers();
      this.kbc.updateIterator(currentPlayer.makeIterator());        //Update the KeyboardController
      this.mainScreen.updatePlayer(this.currentPlayer);
  }

  private void togglePlayers(){
      int newIndex = java.util.Arrays.asList(players).indexOf(currentPlayer)+1 % 2; //mod magic
      this.currentPlayer = players[newIndex];
  }






  //Leaving this so as to not break some tests in Utility/Main
  public void setCurrentPlayer(Player p) {
	  this.currentPlayer = p;
  }

  public void notifyOfCommand(CTRLCommand cmd){
      try {
//          //TODO remove this and implement player switching
//          currentPlayer.endTurn();
//          currentPlayer.beginTurn();
          cmd.execute(map, currentPlayer);
      } catch (CommandNotConfiguredException e) {
          e.printStackTrace();
      }
  }

}