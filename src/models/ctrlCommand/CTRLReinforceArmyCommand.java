package models.ctrlCommand;

import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Units.Unit;
import models.visitor.ReinforceArmyVisitor;

public class CTRLReinforceArmyCommand implements CTRLCommand{
    private Unit unit;
    private RallyPoint rallyPoint;

    public CTRLReinforceArmyCommand(Unit unit, RallyPoint rallyPoint){
        this.unit = unit;
        this.rallyPoint = rallyPoint;
    }

    @Override
    public void execute(GameMap map, Player player) {
        rallyPoint.accept(
                new ReinforceArmyVisitor(map, unit)
        );
    }
}
