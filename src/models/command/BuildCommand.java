package models.command;


import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.RallyPoint;
import models.visitor.StructureCreationVisitor;

public class BuildCommand implements Command{
    private Player player;
    private GameMap map;
    private RallyPoint rallyPoint;
    private String structureType;
    private int numWorkersAssigned;

    public BuildCommand(GameMap map, Player player, RallyPoint rallyPoint, String structureType, int numWorkersAssigned){
        this.map = map;
        this.player = player;
        this.rallyPoint = rallyPoint;
        this.structureType = structureType;
        this.numWorkersAssigned = numWorkersAssigned;
    }

    @Override
    public void execute() {
        StructureCreationVisitor vis = new StructureCreationVisitor(map, rallyPoint, structureType);
        player.accept(
                vis
        );
        if(numWorkersAssigned > 0){
            rallyPoint.addCommand(
                    new ModifyWorkersCommand(false,
                            map,
                            rallyPoint.getArmy(),
                            vis.getCreation(),
                            numWorkersAssigned
                    )
            );
        }
    }

    @Override
    public double getTurns() {
        return 3;
    }
}
