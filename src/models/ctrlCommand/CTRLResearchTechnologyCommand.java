package models.ctrlCommand;
import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Structures.University;
import models.visitor.NewTechVisitor;

public class CTRLResearchTechnologyCommand implements CTRLCommand{
    private String type,asset;
    private University univ;
    private boolean isConfigured;

    public CTRLResearchTechnologyCommand(){
        isConfigured = false;
    }

    public void configure(String type, String asset, University univ) {
        isConfigured = true;
        this.type = type;   //type of tech
        this.asset = asset; //asset to apply to
        this.univ = univ;
    }

    @Override
    public void callback() throws CommandNotConfiguredException {

    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        this.univ = (University)parts.getRequestingAsset();
        this.type = parts.getTechTypeString();
        this.asset = parts.getTechAssetString();
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
