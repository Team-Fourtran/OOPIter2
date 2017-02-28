package models.ctrlCommand;

import models.assetOwnership.GameMap;
import models.playerAsset.Colonist;
import models.playerAsset.Player;
import models.playerAsset.Unit;
import models.visitor.CapitalCreationVisitor;

public class CTRLCreateCapitalCommand implements CTRLCommand{
    private Unit unit;  //TODO: Might be able to make of type Colonist

    public CTRLCreateCapitalCommand(Unit unit){
        this.unit = unit;
    }

    @Override
    public void execute(GameMap map, Player player) {
        if(unit instanceof Colonist){ //TODO Get rid of type checking if handled by iterators
            player.accept(
                    new CapitalCreationVisitor(
                            unit,
                            map
                    )
            );
        }
        else{
            System.out.println("Need Colonist to create capital!");
        }
    }
}
