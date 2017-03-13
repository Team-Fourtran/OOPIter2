package models.assetOwnership;

import java.util.HashMap;

import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.PlayerAsset;

public class PlayerAssetOwnership {
	private static PlayerAssetOwnership myself;
	private static HashMap<PlayerAsset, Player> ownership = new HashMap<PlayerAsset, Player>();
	
	private PlayerAssetOwnership() {
		
	}
	
	public static PlayerAssetOwnership getInstance() {
		checkInstance();
		return myself;
	}
	
	public static void addPlayerAsset(Player p, PlayerAsset ... assets) {
		checkInstance();
		for (PlayerAsset a : assets) {
			if (!ownership.containsKey(a)) {
				ownership.put(a, p);
			}
 		}
	}
	
	public static void removePlayerAsset(PlayerAsset ... assets) {
		checkInstance();
		for (PlayerAsset a : assets) {
			ownership.remove(a);
		}
	}
	
	public static Player getPlayerOwnership(PlayerAsset asset) {
		checkInstance();
		return ownership.get(asset);
	}

	private static void checkInstance() {
		if (myself == null) {
			myself = new PlayerAssetOwnership();
		}
	}
}
