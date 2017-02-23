package models.utility;


import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.MoveRallyPointCommand;
import models.playerAssetNew.*;
import models.tileInfo.Normal;
import models.tileInfo.Terrain;
import models.tileInfo.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TileGen tileGen = new TileGen(5, 5);
        ArrayList<TileAssociation> _tiles = tileGen.execute();

        Unit u1 = new Colonist();
        _tiles.get(4).add(u1);
        Unit u2 = new Explorer();
        _tiles.get(9).add(u2);
        Unit u3 = new Explorer();

        Army a1 = new Army(u3, u1, u2);
        _tiles.get(14).add(a1);

        RallyPoint r = new RallyPoint();
        r.setArmy(a1);
        _tiles.get(14).add(r);

        GameMap map = new GameMap(_tiles, 5, 5);
        map.debugPrint();
        Thread.sleep(1000);
        MoveRallyPointCommand mrp = new MoveRallyPointCommand(
                r,
                _tiles.get(22),
                map
        );
        mrp.execute();

        map.debugPrint();
        Thread.sleep(1000);
    }
}

class TileGen{
    private int length, width, total;
    TileGen(int l, int w){
        this.length = l;
        this.width = w;
        this.total = length*width;
    }
    public ArrayList<TileAssociation> execute(){
        TileAssociation[] tiles = new TileAssociation[total];
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

}
