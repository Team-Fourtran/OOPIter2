package models.assetOwnership;

import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.PlayerAsset;
import models.utility.AStarPathfinder;
import models.utility.ReverseAStar;
import models.visitor.AssetAdditionVisitor;
import models.visitor.AssetVisitor;
import models.utility.ShortestPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/*
 * Class for containing TileStates and optimal path between tiles
 */
public class GameMap {
    private ArrayList<TileAssociation> tiles;
    private HashMap<CombatAsset, RadiusOfInfluenceAssociation> tileInfluence;
    private int length;
    private int width;

    public GameMap(ArrayList<TileAssociation> tiles, int length, int width){
    	this.length = length;
    	this.width = width;
        this.tiles = new ArrayList<>(tiles);
        this.tileInfluence = new HashMap<CombatAsset, RadiusOfInfluenceAssociation>();
    }

    // Gerneate degrees representing the optimal path to get from start to end
    public ArrayList<TileAssociation> generatePath(TileAssociation start, TileAssociation end)  {
        AStarPathfinder path = new AStarPathfinder(start, end);
        return path.getPath();
    }

    // Gerneate degrees representing the optimal path to get from start to end
    public ArrayList<TileAssociation> generatePath(PlayerAsset asset, TileAssociation end)  {
        TileAssociation start = searchForTileAssociation(asset);
        return generatePath(start, end);
    }

    public ArrayList<TileAssociation> generatePath(PlayerAsset asset, PlayerAsset endAsset)  {
        TileAssociation end = searchForTileAssociation(endAsset);
        return generatePath(asset, end);
    }

    public int calculateDistance(TileAssociation start, PlayerAsset asset){
        TileAssociation end = searchForTileAssociation(asset);
        ShortestPath path = new ShortestPath(start, end);
        return path.getDistance();
    }

    public int calculateDistance(PlayerAsset asset1, PlayerAsset asset2) {
        TileAssociation start = searchForTileAssociation(asset1);
        return calculateDistance(start, asset2);
    }
    
    private TileAssociation searchForTileAssociation(PlayerAsset asset){
        for (TileAssociation t : tiles){
            if (t.isAssetOwner(asset)){
                return t;
            }
        }
        return null;
    }

    public boolean removeAssetFromMap(PlayerAsset asset){
        TileAssociation loc = searchForTileAssociation(asset);
        boolean removal = false;
        if (loc != null){
            removal = loc.remove(asset);
        }
        // also remove player asset from the influencer lists
        tileInfluence.remove(asset);
        return removal;
    }
    
    public boolean removeAssetFromMap(PlayerAsset asset, TileAssociation location) {
    	boolean removal = location.remove(asset);
    	tileInfluence.remove(asset);
    	return removal;
    }

    // TODO: test this!!
    public void addAssetToMap(PlayerAsset asset, PlayerAsset location){
        TileAssociation loc = searchForTileAssociation(location);
        if (loc != null){
        	addAssetToMap(asset, loc);
        }
    }
    
    public void addAssetToMap(PlayerAsset asset, TileAssociation location){
    	AssetVisitor v = new AssetAdditionVisitor(this, location);
    	asset.accept(v);
    }
    
    // TODO: modify to include influenced tiles
    public void replaceAsset(PlayerAsset oldAsset, PlayerAsset newAsset){
        TileAssociation tileAssociation = searchForTileAssociation(oldAsset);
        removeAssetFromMap(oldAsset, tileAssociation);
        addAssetToMap(newAsset, tileAssociation);
    }
    
    public void addInfluenceRadius(CombatAsset asset, TileAssociation baseTile){
    	// if this asset does not have an influence radius, add it to the map
    	if (!tileInfluence.containsKey(asset)) {
    		RadiusOfInfluenceAssociation roi = new RadiusOfInfluenceAssociation(asset, baseTile);
    		tileInfluence.put(asset, roi);
    	}
    }
    
    public void updateInfluenceRadius(CombatAsset asset){
    	RadiusOfInfluenceAssociation roi = tileInfluence.get(asset);
    	roi.updateInfluencedTiles();
    }
    
	public Vector<TileAssociation> getRadiusOfInfluence(CombatAsset asset) {
		return tileInfluence.get(asset).getInfluencedTiles();
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