package application;

/* Standard library imports */
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.ArmyManager;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.StructureManager;
import models.playerAsset.Assets.UnitManager;
import models.playerAsset.Assets.Units.Unit;
import models.utility.TileGen;

import java.util.ArrayList;

/* 
 * Named as per Dave's specifications 
 * Contains the main method for creating and starting the game
 */
public class RunGame {
    public static void main(String[] args) throws InterruptedException{
        int map_length = 10;
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        TileGen tileGen = new TileGen(map_length, map_length);
        ArrayList<TileAssociation> _tiles = tileGen.execute();

        UnitManager p1um = player1.getUnits();
        UnitManager p2um = player2.getUnits();

        Unit p1u1 = p1um.addNewUnit("colonist");
        _tiles.get(0).add(p1u1);

        Unit p2u1 = p2um.addNewUnit("colonist");
        _tiles.get(map_length*map_length -1).add(p2u1);

        Game game = new Game(player1,player2,_tiles);
    }
}
