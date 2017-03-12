package controllers;

import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.PlayerAsset;
import views.MainScreen;

public class TileTargetting {
    MainScreen screen;
    MessageGenerator receiver;

    public TileTargetting(MainScreen s){
        screen = s;
    }

    protected void targetTile(MessageGenerator receiver, PlayerAsset startingHighlight){
        this.receiver = receiver;
        screen.doTileTargetting(this,startingHighlight);
    }


    public void receiveTile(TileAssociation ta){
        if(null != ta)
            receiver.receiveTargetTile(ta);
    }
}
