package models.ctrlCommand;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Structures.Structure;

public class CTRLHealCommand implements CTRLCommand{
    private Structure structure;
    private TileAssociation tile;

    public CTRLHealCommand(Structure structure, TileAssociation tile){
        this.tile = tile;
        this.structure = structure;
    }

    @Override
    public void execute(GameMap map, Player player) {

    }
}
