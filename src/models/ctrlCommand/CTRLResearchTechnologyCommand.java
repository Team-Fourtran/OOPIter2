package models.ctrlCommand;
import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Structures.University;
import models.visitor.NewTechVisitor;

public class CTRLResearchTechnologyCommand implements CTRLCommand{

    String type,asset;
    University univ;
    boolean isConfigured;

    public CTRLResearchTechnologyCommand(){
        isConfigured = false;
    }

    public void configure(String type, String asset, University univ) {
        isConfigured = true;
        this.type = type;
        this.asset = asset;
        this.univ = univ;
    }

    @Override
    public void callback() throws CommandNotConfiguredException {

    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {

    }

    public void execute(GameMap map, Player player){
        player.accept(
                new NewTechVisitor(
                        type,
                        asset,
                        univ
        ));
    }

    @Override
    public boolean isConfigured() {
        return false;
    }
}
