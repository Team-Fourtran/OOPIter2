package models.assetOwnership;

import models.playerAssetNew.PlayerAsset;
import models.utility.ReverseAStar;
import java.util.ArrayList;

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

//    public void printOut(){
//        tiles.forEach(TileAssociation::print);
//    }

    // Gerneate degrees representing the optimal path to get from start to end
    public ArrayList<TileAssociation> generatePath(PlayerAsset asset, TileAssociation end)  {
        TileAssociation start = getAssociation(asset);
        ReverseAStar path = new ReverseAStar(end, asset);
        return path.execute();
    }
    private TileAssociation getAssociation(PlayerAsset asset){
        for (TileAssociation t : tiles){
            if (t.isAssetOwner(asset)){
                return t;
            }
        }
        return null;
    }

//    public void removeAsset(PlayerAsset asset){
//        for (TileAssociation t : tiles){
//            if (t.isAssetOwner(asset)){
//                t.remove(asset);
//            }
//        }
//    }

    public void generateImmediateMovement(PlayerAsset asset, TileAssociation destination){
        //Precondition -> asset exists on a tile
        TileAssociation start = getAssociation(asset);
        if (start == null){
            System.out.println("Asset has no location?!?!");
            return;
        }
        start.remove(asset);
        destination.add(asset);
    }

    public int getLength() {
    	return length;
    }
    
    public int getWidth() {
    	return width;
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