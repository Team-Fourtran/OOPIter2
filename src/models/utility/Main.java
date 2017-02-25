package models.utility;


import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.CreateArmyCommand;
import models.playerAsset.*;
import models.tileInfo.Normal;
import models.tileInfo.Terrain;
import models.tileInfo.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Player player = new Player();
        ArmyManager am = player.armies;
        UnitManager um = player.units;
        StructureManager sm = player.structures;

        um.addNewUnit("explorer");
        um.addNewUnit("explorer");
        um.addNewUnit("explorer");
        um.addNewUnit("explorer");
        um.addNewUnit("colonist");
        um.addNewUnit("colonist");
        um.addNewUnit("colonist");
        um.addNewUnit("colonist");
        um.addNewUnit("melee");
        um.addNewUnit("melee");
        um.addNewUnit("melee");
        um.addNewUnit("ranged");
        um.addNewUnit("ranged");


        AssetIterator<PlayerAsset, TypeIterator<PlayerAsset, Iterator<PlayerAsset>>> iter = player.getAssetIterator();

        iter.first();
        iter.getElement();
        iter.next();
        iter.getElement();
        iter.prevType();
        iter.getElement();
        iter.next();
        iter.getElement();
        iter.nextType();
        iter.getElement();
        iter.prev();
        iter.getElement();
        iter.prev();
        iter.getElement();
        iter.prev();
        iter.getElement();
        iter.prev();
        iter.getElement();
        iter.prev();
        iter.getElement();

        /*
        TileGen tileGen = new TileGen(5, 5);
        ArrayList<TileAssociation> _tiles = tileGen.execute();

        Unit u0 = um.addNewUnit("colonist");
        Unit u1 = um.addNewUnit("colonist");
        Unit u2 = um.addNewUnit("colonist");
        _tiles.get(4).add(u0);
        _tiles.get(3).add(u1);
        _tiles.get(24).add(u2);

        GameMap map = new GameMap(_tiles, 5, 5);

        map.debugPrint();
        Thread.sleep(1000);

        CreateArmyCommand createArmyCommand = new CreateArmyCommand(map, player, _tiles.get(20),u0, u1, u2);
        createArmyCommand.execute();

        RallyPoint rallyPoint = am.debugGetRallyPoint();
        Army army = am.debugGetArmy();

        System.out.println("MOVING TOWARDS RALLYPOINT:");
        map.debugPrint();
        Thread.sleep(1000);

//        System.out.println("MOVED RALLY POINT:");
//        MoveRallyPointCommand mrp = new MoveRallyPointCommand(rallyPoint, _tiles.get(20), map);
//        mrp.execute();
//        map.debugPrint();
//        Thread.sleep(1000);

        player.endTurn();
        player.beginTurn();
        System.out.println("NEW TURN");
        map.debugPrint();
        Thread.sleep(1000);

        player.endTurn();
        player.beginTurn();
        System.out.println("NEW TURN");
        map.debugPrint();
        Thread.sleep(1000);

        player.endTurn();
        player.beginTurn();
        System.out.println("NEW TURN");
        map.debugPrint();
        Thread.sleep(1000);
    }

    private class newArmySim{
        private ArmyManager am = new ArmyManager();

        newArmySim(Player player, GameMap map, TileAssociation start, Unit ... units){
            RallyPoint rp = am.formRallyPoint(
                    am.formArmy(units)
            );
            new CreateArmyCommand(
                    map,
                    player,
                    start,
                    units
            );

        }
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
*/
    }
}
