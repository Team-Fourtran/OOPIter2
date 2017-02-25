package models.utility;


import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.CTRLCreateArmyCommand;
import models.command.CTRLCreateStructureCommand;
import models.command.MoveRallyPointCommand;
import models.playerAsset.*;
import models.tileInfo.Normal;
import models.tileInfo.Terrain;
import models.tileInfo.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        new testArmyCreationAndMovement().run();
        //new testCapitalCreation().run();
    }
}

class testCapitalCreation{
    public void run() throws InterruptedException {
        Player player = new Player();
        ArmyManager am = player.getArmies();
        UnitManager um = player.getUnits();
        StructureManager sm = player.getStructures();

        TileGen tileGen = new TileGen(5, 5);
        ArrayList<TileAssociation> _tiles = tileGen.execute();

        Unit u0 = um.addNewUnit("colonist");
        Unit u1 = um.addNewUnit("colonist");
        Unit u2 = um.addNewUnit("colonist");
        _tiles.get(4).add(u0);
        _tiles.get(3).add(u1);
        _tiles.get(24).add(u2);

        GameMap map = new GameMap(_tiles, 5, 5);
        System.out.println("INITIAL:");
        map.debugPrint();
        Thread.sleep(1000);


        new CTRLCreateStructureCommand(map, player, u0, "capital").execute();

        System.out.println("CREATED CAPITAL");
        map.debugPrint();
        Thread.sleep(1000);

        player.endTurn();
        player.beginTurn();
        System.out.println("NEW TURN");
        map.debugPrint();
        Thread.sleep(1000);
    }
}

class testArmyCreationAndMovement{
    public void run() throws InterruptedException {
        Player player = new Player();
        ArmyManager am = player.getArmies();
        UnitManager um = player.getUnits();
        StructureManager sm = player.getStructures();

        TileGen tileGen = new TileGen(30, 30);
        ArrayList<TileAssociation> _tiles = tileGen.execute();

        Unit u0 = um.addNewUnit("colonist");
        Unit u1 = um.addNewUnit("colonist");
        Unit u2 = um.addNewUnit("colonist");
        _tiles.get(4).add(u0);
        _tiles.get(3).add(u1);
        _tiles.get(24).add(u2);

        GameMap map = new GameMap(_tiles, 30, 30);

        map.debugPrint();
        Thread.sleep(1000);

        CTRLCreateArmyCommand CTRLCreateArmyCommand = new CTRLCreateArmyCommand(map, player, _tiles.get(20),u0, u1, u2);
        CTRLCreateArmyCommand.execute();

        RallyPoint rallyPoint = am.debugGetRallyPoint();
        Army army = am.debugGetArmy();

        System.out.println("MOVING TOWARDS RALLYPOINT:");
        map.debugPrint();
        Thread.sleep(1000);

        System.out.println("MOVED RALLY POINT:");
        MoveRallyPointCommand mrp = new MoveRallyPointCommand(rallyPoint, _tiles.get(24), map);
        mrp.execute();
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

        player.endTurn();
        player.beginTurn();
        System.out.println("NEW TURN");
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
