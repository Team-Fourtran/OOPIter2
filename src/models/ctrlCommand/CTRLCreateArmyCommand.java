/*
IMMEDIATE COMMAND FROM CONTROLLER
 */
package models.ctrlCommand;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Units.Unit;
import models.visitor.ArmyCreationVisitor;

public class CTRLCreateArmyCommand implements CTRLCommand{
    private TileAssociation RPLocation;
    private Unit[] units;

    public CTRLCreateArmyCommand(TileAssociation startTile, Unit ... units){
        this.RPLocation = startTile;
        this.units = units;
    }

    @Override
    public void execute(GameMap map, Player player) {
        player.accept(
                new ArmyCreationVisitor(map, RPLocation, units)
        );
    }
}
