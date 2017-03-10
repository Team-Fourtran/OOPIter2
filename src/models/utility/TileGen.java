package models.utility;

import models.assetOwnership.TileAssociation;

import models.tileInfo.*;

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
        ArrayList<Item> itemList;
        tiles = new TileAssociation[total];
        Terrain t;
        ArrayList<Integer> normalList = new ArrayList<>();
        normalList.add(11*length + 4);
        normalList.add(10*length + 4);
        normalList.add(9*length + 3);
        normalList.add(8*length + 3);
        normalList.add(7*length + 2);
        for (int i = 0; i < total; i++){
            itemList = new ArrayList<>();
            if (normalList.contains(i)){
                t = new Normal();
            }
            else if(i%length == 5 && i < (length-1)*length){
                t = new Impassable();
            }
            else if(i%length == 4 && i < (length-1)*length){
                t = new Slowing();
            }
            else if(i%length == 3 && i < (length-1)*length){
                t = new Slowing();
            }
            else if(i%length == 2 && i < (length-1)*length){
                t = new Slowing();
            }
            else if(i%length == 13){
                t = new Impassable();
            }
            else {
                t = new Normal();
            }
            //Terrain t = new Impassable();

            if(i % 15 == 8){
                itemList.add(new OneShotItem());
            }

            Tile tile = new Tile(t, itemList);
            tiles[i] = new TileAssociation(tile);
        }
        //HEX:
        for (int i = 0; i < total; i++) {
            //EVEN
            if((i/length)%2 == 0){
                //Top Left
                if(i-length >= 0)
                    tiles[i].setNeighbor(Direction.TopLeft, tiles[i-length]); // tiles[i].setNeighbor(tiles[i-length], Direction object?? Yeah
                //Top Right
                if(i%length != 0 && i-length >= 0)
                    tiles[i].setNeighbor(Direction.TopRight, tiles[i-length-1]);
                //Bottom Left
                if(i+length < total)
                    tiles[i].setNeighbor(Direction.BottomLeft, tiles[i+length]);
                //Bottom Right
                if(i%length != 0 && i+length < total)
                    tiles[i].setNeighbor(Direction.BottomRight, tiles[i+length-1]);
            }
            //ODD
            else{
                //Top Right
                if(i-length >= 0)
                    tiles[i].setNeighbor(Direction.TopRight, tiles[i-length]);
                //Top Left
                if((i+1)%length != 0 && i-length >= 0)
                    tiles[i].setNeighbor(Direction.TopLeft, tiles[i-length+1]);
                //Bottom Right
                if(i+length < total)
                    tiles[i].setNeighbor(Direction.BottomRight, tiles[i+length]);
                //Bottom Left
                if((i+1)%length != 0 && i+length < total)
                    tiles[i].setNeighbor(Direction.BottomLeft, tiles[i+length+1]);
            }
            //Left
            if((i+1)%length != 0)
                tiles[i].setNeighbor(Direction.Left, tiles[i+1]);
            //Right
            if(i%length != 0){
                tiles[i].setNeighbor(Direction.Right, tiles[i-1]);
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
