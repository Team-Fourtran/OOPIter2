package models.command;

import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Structures.University;
import models.visitor.NewTechVisitor;

public class ResearchCommand implements Command {
    private String type,asset;
    private University univ;
    private Player player;

    public ResearchCommand(Player player, University university, String type, String asset){
        this.univ = university;
        this.type = type;
        this.asset = asset;
        this.player = player;
    }

    @Override
    public void execute() {
        player.accept(
                new NewTechVisitor(
                        type,
                        asset,
                        univ
                ));
    }

    @Override
    public String toString(){
        return type + " for " + asset;
    }

    @Override
    public double getTurns() {
        return 4;
    }
}
