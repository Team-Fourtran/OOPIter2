package models.utility;


import application.Game;
import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.CTRLAttackCommand;
import models.command.CTRLCreateArmyCommand;
import models.command.CTRLCreateStructureCommand;
import models.command.CTRLMoveRallyPointCommand;
import models.playerAsset.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //new testArmyCreationAndMovement().run();
        //new testIterator().run();
        //new testCapitalCreation().run();
        new testAttack().run();
    }
}

class testCapitalCreation {
    public void run() throws InterruptedException {
        //COPY+PASTE
        //----------------------------
        int length = 15;
        Player player = new Player();
        ArmyManager am = player.getArmies();
        UnitManager um = player.getUnits();
        StructureManager sm = player.getStructures();
        TileGen tileGen = new TileGen(length, length);
        ArrayList<TileAssociation> _tiles = tileGen.execute();
        new Game(_tiles);
        GameMap map = new GameMap(_tiles, 5, 5);
        //----------------------------

        Unit u0 = um.addNewUnit("colonist");
        Unit u1 = um.addNewUnit("colonist");
        Unit u2 = um.addNewUnit("colonist");
        _tiles.get(4).add(u0);
        _tiles.get(3).add(u1);
        _tiles.get(24).add(u2);

        new CTRLCreateStructureCommand(map, player, u0, "base").execute();

        System.out.println("CREATED BASE");
        map.debugPrint();
        Thread.sleep(1000);

        for (int i = 0; i < 10; i++) {
            player.endTurn();
            player.beginTurn();
            System.out.println("NEW TURN");
            map.debugPrint();
            Thread.sleep(1000);
        }
    }
}

class testArmyCreationAndMovement {
    public void run() throws InterruptedException {
        int length = 15;
        Player player = new Player();
        ArmyManager am = player.getArmies();
        UnitManager um = player.getUnits();
        StructureManager sm = player.getStructures();

        TileGen tileGen = new TileGen(length, length);
        ArrayList<TileAssociation> _tiles = tileGen.execute();
        new Game(_tiles);


        Unit u0 = um.addNewUnit("colonist");
        Unit u1 = um.addNewUnit("colonist");
        Unit u2 = um.addNewUnit("colonist");
        _tiles.get(0).add(u0);
        _tiles.get(1).add(u1);
        _tiles.get(2).add(u2);

        GameMap map = new GameMap(_tiles, length, length);

        map.debugPrint();
        Thread.sleep(1000);

        CTRLCreateArmyCommand CTRLCreateArmyCommand = new CTRLCreateArmyCommand(map, player, _tiles.get(50), u0, u1, u2);
        CTRLCreateArmyCommand.execute();

        RallyPoint rallyPoint = am.debugGetRallyPoint();
        Army army = am.debugGetArmy();

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
        player.endTurn();
        player.beginTurn();
        System.out.println("NEW TURN");
        map.debugPrint();
        Thread.sleep(1000);

        System.out.println("MOVING TOWARDS RALLYPOINT:");
        map.debugPrint();
        Thread.sleep(1000);

        System.out.println("MOVED RALLY POINT:");

        CTRLMoveRallyPointCommand mrp = new CTRLMoveRallyPointCommand(rallyPoint, _tiles.get(24), map);

        mrp.execute();
        map.debugPrint();
        Thread.sleep(1000);

        for (int i = 0; i < 10; i++) {
            player.endTurn();
            player.beginTurn();
            System.out.println("NEW TURN");
            map.debugPrint();
            Thread.sleep(1000);
        }
    }
}

class testAttack{
    public void run() throws InterruptedException {
        //COPY+PASTE
        //----------------------------
        int length = 15;
        Player player = new Player();
        ArmyManager am = player.getArmies();
        UnitManager um = player.getUnits();
        StructureManager sm = player.getStructures();
        TileGen tileGen = new TileGen(length, length);
        ArrayList<TileAssociation> _tiles = tileGen.execute();
        new Game(_tiles);
        GameMap map = new GameMap(_tiles, 5, 5);
        //----------------------------
        Unit u0 = um.addNewUnit("melee");
        Unit u1 = um.addNewUnit("colonist");
        Structure s0 = sm.createStructure("base");
        _tiles.get(4).add(u0);
        _tiles.get(3).add(u1);
        _tiles.get(3).add(s0);


        new CTRLAttackCommand(player, map, u0, _tiles.get(3)).execute();
        new CTRLAttackCommand(player, map, u0, _tiles.get(3)).execute();
        new CTRLAttackCommand(player, map, u0, _tiles.get(3)).execute();
        new CTRLAttackCommand(player, map, u0, _tiles.get(3)).execute();
        new CTRLAttackCommand(player, map, u0, _tiles.get(3)).execute();
        new CTRLAttackCommand(player, map, u0, _tiles.get(3)).execute();

        for(int i = 0; i < 6; i++){
            Thread.sleep(500);
            player.endTurn();
            player.beginTurn();
        }
    }
}

class testIterator {

    int length = 15;
    Player player = new Player();
    ArmyManager am = player.getArmies();
    UnitManager um = player.getUnits();
    StructureManager sm = player.getStructures();

    public void run() {

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

        sm.createStructure("base");
        sm.createStructure("base");
        sm.createStructure("base");
        sm.createStructure("base");
        sm.createStructure("base");

        AssetIterator iter = player.getAssetIterator();

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
        iter.nextMode();
        iter.getElement();
        iter.next();
        iter.getElement();
        iter.nextMode();
        iter.getElement();
        iter.prevMode();
        iter.getElement();
    }
}