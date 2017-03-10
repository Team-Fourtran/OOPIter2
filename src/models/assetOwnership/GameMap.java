package models.assetOwnership;

import models.playerAsset.Assets.PlayerAsset;
import models.utility.ReverseAStar;
import java.util.ArrayList;
import java.util.Vector;

/*
 * Class for containing TileStates and optimal path between tiles
 */
public class GameMap {
    private ArrayList<TileAssociation> tiles;
    private int length;
    private int width;

    public GameMap(ArrayList<TileAssociation> tiles, int length, int width){
    	this.length = length;
    	this.width = width;
        this.tiles = new ArrayList<>(tiles);
    }

    // Gerneate degrees representing the optimal path to get from start to end
    public ArrayList<TileAssociation> generatePath(PlayerAsset asset, TileAssociation end)  {
        ReverseAStar path = new ReverseAStar(end, asset);
        return path.getPath();
    }
    public ArrayList<TileAssociation> generatePath(PlayerAsset asset, PlayerAsset endAsset)  {
        TileAssociation end = searchForTileAssociation(endAsset);
        return generatePath(asset, end);
    }

    public int calculateDistance(TileAssociation start, PlayerAsset asset){
        ReverseAStar path = new ReverseAStar(start, asset);
        return path.getDistance(); //NOTE THIS IS NOT THE MINIMUM DISTANCE
    }

    public int calculateDistance(PlayerAsset asset1, PlayerAsset asset2) {
        TileAssociation start = searchForTileAssociation(asset1);
        return calculateDistance(start, asset2);
    }
    
	public Vector<TileAssociation> getTileAssociationsWithinRadius(PlayerAsset asset, int radius) {
		Vector<TileAssociation> vec = new Vector<TileAssociation>();
		TileAssociation start = searchForTileAssociation(asset);
		getTileAssociationsWithinRadius(start, radius, vec);
		return vec;
	}
	
	public void getTileAssociationsWithinRadius(TileAssociation s, int radius, Vector<TileAssociation> vec) {
		if (!vec.contains(s)) {
			vec.add(s);
		}
		if (radius != 0) {
			for (TileAssociation t : s.getNeighbors()) {
				getTileAssociationsWithinRadius(t, radius-1, vec);
			}
		}
	}

    private TileAssociation searchForTileAssociation(PlayerAsset asset){
        for (TileAssociation t : tiles){
            if (t.isAssetOwner(asset)){
                return t;
            }
        }
        return null;
    }

    public void removeAssetFromMap(PlayerAsset asset){
        searchForTileAssociation(asset).remove(asset);
    }

    public void replaceAsset(PlayerAsset oldAsset, PlayerAsset newAsset){
        TileAssociation tileAssociation = searchForTileAssociation(oldAsset);
        tileAssociation.remove(oldAsset);
        tileAssociation.add(newAsset);
    }

    public void generateImmediateMovement(PlayerAsset asset, TileAssociation destination){
        //Precondition -> asset exists on a tile
        TileAssociation start = searchForTileAssociation(asset);
        if (start == null){
            System.out.println("Asset has no location?!?!");
            return;
        }
        start.remove(asset);
        destination.add(asset);
    }

    public void debugPrint(){
        System.out.print(" ");
        for(int i = 0; i < length; i++){
            System.out.print("_____ ");
        }
        System.out.print("\n");
        for (int i = 0; i < width; i++){
            for (int k = 0; k < length; k++){
                System.out.print("|     ");
            }
            System.out.print("|\n");
            for (int j = 0; j < length; j++){
                System.out.print("|");
                if(tiles.get(length*i+j).isAssetOwner()){
                    System.out.print(" X:" + tiles.get(length*i+j).debugNumAssets() + " ");
                }
                else
                    System.out.print("     ");
            }
            System.out.print("|\n");
            for (int k = 0; k < length; k++){
                System.out.print("|_____");
            }
            System.out.print("|\n");
        }
        for (int i = 0 ; i < 16*length; i++){
            System.out.print("\b");
        }
    }
}