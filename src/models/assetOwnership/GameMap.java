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

    public boolean assetExists(PlayerAsset asset){
        if (searchForTileAssociation(asset) == null){
            return false;
        }
        return true;
    }

    public boolean removeAssetFromMap(PlayerAsset asset){
        TileAssociation loc = searchForTileAssociation(asset);
        return removeAssetFromMap(asset, loc);
    }

    public boolean removeAssetFromMap(PlayerAsset asset, TileAssociation location) {
        boolean removal = false;
        if (location != null){
            removal = location.remove(asset);
        }
    	tileInfluence.remove(asset);
    	return removal;
    }

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
    public PlayerAsset replaceAsset(PlayerAsset oldAsset, PlayerAsset newAsset){
        TileAssociation tileAssociation = searchForTileAssociation(oldAsset);
        removeAssetFromMap(oldAsset, tileAssociation);
        addAssetToMap(newAsset, tileAssociation);
        return newAsset;
    }
    
    public void addInfluenceRadius(CombatAsset asset, TileAssociation baseTile){
    	RadiusOfInfluenceAssociation roi = new RadiusOfInfluenceAssociation(asset, baseTile, this);
    	tileInfluence.put(asset, roi);
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
        if (start != destination) {
	        removeAssetFromMap(asset, start);
	        addAssetToMap(asset, destination);
        }
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