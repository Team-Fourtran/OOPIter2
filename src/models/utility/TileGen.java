package models.utility;

import models.assetOwnership.TileAssociation;

import models.tileInfo.Normal;
import models.tileInfo.Terrain;
import models.tileInfo.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
        //HEX:
        for (int i = 0; i < total; i++) {
            //EVEN
            if((i/length)%2 == 0){
                //Top Left
                if(i-length >= 0)
                    tiles[i].setNeighbor(tiles[i-length]);
                //Top Right
                if(i%length != 0 && i-length >= 0)
                    tiles[i].setNeighbor(tiles[i-length-1]);
                //Bottom Left
                if(i+length < total)
                    tiles[i].setNeighbor(tiles[i+length]);
                //Bottom Right
                if(i%length != 0 && i+length < total)
                    tiles[i].setNeighbor(tiles[i+length-1]);
            }
            //ODD
            else{
                //Top Right
                if(i-length >= 0)
                    tiles[i].setNeighbor(tiles[i-length]);
                //Top Left
                if((i+1)%length != 0 && i-length >= 0)
                    tiles[i].setNeighbor(tiles[i-length+1]);
                //Bottom Right
                if(i+length < total)
                    tiles[i].setNeighbor(tiles[i+length]);
                //Bottom Left
                if((i+1)%length != 0 && i+length < total)
                    tiles[i].setNeighbor(tiles[i+length+1]);
            }
            //Left
            if((i+1)%length != 0)
                tiles[i].setNeighbor(tiles[i+1]);
            //Right
            if(i%length != 0){
                tiles[i].setNeighbor(tiles[i-1]);
            }
        }
//      SQUARE:
//        for (int i = 0; i < total; i++) {
//            if(i-length >= 0)
//                tiles[i].setNeighbor(tiles[i-length]);
//            if((i+1)%length != 0 && i-length >= 0)
//                tiles[i].setNeighbor(tiles[i-length+1]);
//            if((i+1)%length != 0)
//                tiles[i].setNeighbor(tiles[i+1]);
//            if((i+1)%length != 0 && i+length < total)
//                tiles[i].setNeighbor(tiles[i+length+1]);
//            if(i+length < total)
//                tiles[i].setNeighbor(tiles[i+length]);
//            if(i%length != 0 && i+length < total)
//                tiles[i].setNeighbor(tiles[i+length-1]);
//            if(i%length != 0)
//                tiles[i].setNeighbor(tiles[i-1]);
//            if(i%length != 0 && i-length >= 0)
//                tiles[i].setNeighbor(tiles[i-length-1]);
//        }


        return new ArrayList<>(Arrays.asList(tiles));
    }
    public TileAssociation[] returnTileAssoc(){
        return tiles;
    }

}
