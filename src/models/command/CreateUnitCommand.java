package models.command;

import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Structures.Structure;
import models.visitor.UnitCreationVisitor;

public class CreateUnitCommand implements Command{
    private Player player;
    private GameMap map;
    private Structure structure;
    private String unitType;

    public CreateUnitCommand(GameMap map, Player player, Structure structure, String unitType){
        this.map = map;
        this.player = player;
        this.structure = structure;
        this.unitType = unitType;
    }

    @Override
    public void execute() {
        player.accept(
            new UnitCreationVisitor(
                    map,
                    structure,
                    unitType
            )
        );
    }

    @Override
    public double getTurns() {
        return 2;
    }
}
