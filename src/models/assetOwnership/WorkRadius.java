package models.assetOwnership;

import java.util.ArrayList;
import java.util.Vector;

import models.playerAsset.Assets.Structures.Capital;
import models.playerAsset.Assets.Structures.Farm;
import models.playerAsset.Assets.Structures.Fort;
import models.playerAsset.Assets.Structures.Mine;
import models.playerAsset.Assets.Structures.ObservationTower;
import models.playerAsset.Assets.Structures.PowerPlant;
import models.playerAsset.Assets.Structures.University;

public class WorkRadius extends Radius {

	public WorkRadius(TileAssociation baseTile, int radiusSize) {
		super(baseTile, radiusSize);
	}
	
	// Return a list of all TAs with this resource
	public ArrayList<TileAssociation> getTilesWithResource(String resourceType) {
		ArrayList<TileAssociation> tilesWithResources = new ArrayList<TileAssociation>();
		Vector<TileAssociation> tiles = getInfluencedTiles();
		for (int i = 0; i < tiles.size(); i++) {
		    if(tiles.get(i).hasResourcePackage()){
                switch (resourceType){
                    case "food":
                        if (tiles.get(i).getResourcePackage().getFoodCount() > 0) {
                            tilesWithResources.add(tiles.get(i));
                        }
                        break;
                    case "ore":
                        if (tiles.get(i).getResourcePackage().getOreCount() > 0) {
                            tilesWithResources.add(tiles.get(i));
                        }
                        break;
                    case "energy":
                        if (tiles.get(i).getResourcePackage().getEnergyCount() > 0) {
                            tilesWithResources.add(tiles.get(i));
                        }
                        break;
                    default:
                        break;
                }
            }
		}
		return tilesWithResources;
	}

}
