package models.assetOwnership;

import models.playerAsset.Assets.CombatAsset;

import java.util.Vector;

/**
 * Created by allisonaguirre on 3/15/17.
 */
public class RadiusOfVisibilityAssociation extends Radius {
    private Vector<TileAssociation> nonVisibleTiles = new Vector<TileAssociation>(0);

    public RadiusOfVisibilityAssociation(CombatAsset asset, TileAssociation baseTile, GameMap map) {
        super(baseTile, asset.getVisibility());
    }

    public Vector<TileAssociation> getRadiusOfVisibility() {
        return super.getTilesWithinRadius();
    }

    public Vector<TileAssociation> getNonVisibleTiles() {
        return nonVisibleTiles;
    }

}
