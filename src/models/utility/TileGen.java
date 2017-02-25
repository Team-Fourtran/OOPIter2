package models.utility;

import models.assetOwnership.TileAssociation;
import models.playerAssetNew.PlayerAsset;
import models.playerAssetNew.Structure;
import models.tileInfo.Normal;
import models.tileInfo.Terrain;
import models.tileInfo.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by TK on 2/25/17.
 */
public class TileGen {
    private int length, width, total;
    public TileAssociation[] tiles;
    public TileGen(int l, int w){
        this.length = l;
        this.width = w;
        this.total = length*width;
    }
    public ArrayList<TileAssociation> execute(){
        tiles = new TileAssociation[total];
        Random rand = new Random();
        for (int i = 0; i < total; i++){
            Terrain t = new Normal();
            Tile tile = new Tile(t);
            tiles[i] = new TileAssociation(tile);
        }
        for (int i = 0; i < total; i++) {
            if(i-length >= 0)
                tiles[i].setNeighbor(tiles[i-length]);
            if((i+1)%length != 0 && i-length >= 0)
                tiles[i].setNeighbor(tiles[i-length+1]);
            if((i+1)%length != 0)
                tiles[i].setNeighbor(tiles[i+1]);
            if((i+1)%length != 0 && i+length < total)
                tiles[i].setNeighbor(tiles[i+length+1]);
            if(i+length < total)
                tiles[i].setNeighbor(tiles[i+length]);
            if(i%length != 0 && i+length < total)
                tiles[i].setNeighbor(tiles[i+length-1]);
            if(i%length != 0)
                tiles[i].setNeighbor(tiles[i-1]);
            if(i%length != 0 && i-length >= 0)
                tiles[i].setNeighbor(tiles[i-length-1]);
        }
        return new ArrayList<>(Arrays.asList(tiles));
    }
    public TileAssociation[] returnTileAssoc(){
        return tiles;
    }

}
