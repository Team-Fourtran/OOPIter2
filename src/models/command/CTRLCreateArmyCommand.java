/*
IMMEDIATE COMMAND FROM CONTROLLER
 */
package models.command;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Player;
import models.playerAsset.Unit;
import models.visitor.ArmyCreationVisitor;

public class CTRLCreateArmyCommand implements Command{
    private TileAssociation RPLocation;
    private Unit[] units;
    private Player player;
    private GameMap map;

    public CTRLCreateArmyCommand(GameMap map, Player p, TileAssociation startTile, Unit ... units){
        this.map = map;
        this.player = p;
        this.RPLocation = startTile;
        this.units = units;
    }

    @Override
    public void execute() {
        player.accept(
                new ArmyCreationVisitor(map, RPLocation, units)
        );
    }

    @Override
    public double getTurns() {
        return 0;
    }
}
