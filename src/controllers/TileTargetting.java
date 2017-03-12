package controllers;

import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.PlayerAsset;
import views.MainScreen;

public class TileTargetting {
    MainScreen screen;

    TileTargetting(MainScreen s){
        screen = s;
    }

    protected TileAssociation targetTile(PlayerAsset startHighlight){
        return screen.doTileTargetting();
    }

}
