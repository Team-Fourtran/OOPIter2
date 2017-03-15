package models.ctrlCommand;
import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.command.ResearchCommand;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Structures.University;

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
        isConfigured = true;
        parts.requestExecution();
    }

    public void execute(GameMap map, Player player){
        univ.addCommand(
                new ResearchCommand(
                        player,
                        univ,
                        type,
                        asset
                )
        );
    }

    @Override
    public boolean isConfigured() {
        return isConfigured;
    }
}
