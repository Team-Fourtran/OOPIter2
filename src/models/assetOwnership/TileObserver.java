package models.assetOwnership;

import models.playerAsset.Assets.PlayerAsset;

/**
 * Created by TK on 2/25/17.
 */
public interface TileObserver {
	void updateRemove(TileAssociation tileAssociation, PlayerAsset p);
	void updateAdd(TileAssociation tileAssociation, PlayerAsset p);
}
