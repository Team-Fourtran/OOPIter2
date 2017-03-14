package models.ctrlCommand;
import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Structures.University;
import models.playerAsset.Assets.Technology.Technology;
import models.visitor.NewTechVisitor;

/**
 * Created by Clay on 3/13/2017.
 */
public class CTRLResearchTechnologyCommand implements CTRLCommand{

    String type,asset;
    University univ;

    public CTRLResearchTechnologyCommand(String type, String asset, University univ){
        this.type = type;
        this.asset = asset;
        this.univ = univ;
    }

    public void execute(GameMap map, Player player){
        player.accept(
                new NewTechVisitor(
                        type,
                        asset,
                        univ
        ));
    }
}
