package models.command;

import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Units.Unit;
import models.visitor.CapitalCreationVisitor;

public class CreateCapitalCommand implements Command{
    private Player player;
    private GameMap map;
    private Unit colonistToRemove;

    public CreateCapitalCommand(GameMap map, Player p, Unit colonist){
        this.player = p;
        this.map = map;
        this.colonistToRemove = colonist;
    }

    @Override
    public void execute() {
        player.accept(
                new CapitalCreationVisitor(
                        colonistToRemove,
                        map
                )
        );
    }

    @Override
    public double getTurns() {
        //TODO: Get Turns to build a capital
        return 0;
    }
}
