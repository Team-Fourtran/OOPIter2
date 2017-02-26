package models.utility;


import application.Game;
import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.CTRLCreateArmyCommand;
import models.command.CTRLCreateStructureCommand;
import models.command.CTRLMoveRallyPointCommand;
import models.playerAsset.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        new testArmyCreationAndMovement().run();
        //new testCapitalCreation().run();
    }
}

class testCapitalCreation{
    public void run() throws InterruptedException {
        int length = 5;
        Player player = new Player();
        ArmyManager am = player.getArmies();
        UnitManager um = player.getUnits();
        StructureManager sm = player.getStructures();

<<<<<<< HEAD
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

        /*
        TileGen tileGen = new TileGen(5, 5);
=======
        TileGen tileGen = new TileGen(30, 30);
>>>>>>> master
        ArrayList<TileAssociation> _tiles = tileGen.execute();
        new Game(_tiles);

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

        for (int i = 0; i < 10; i++){
            player.endTurn();
            player.beginTurn();
            System.out.println("NEW TURN");
            map.debugPrint();
            Thread.sleep(1000);
        }
    }
}

class testArmyCreationAndMovement{
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

        CTRLCreateArmyCommand CTRLCreateArmyCommand = new CTRLCreateArmyCommand(map, player, _tiles.get(112),u0, u1, u2);
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

        for (int i = 0; i < 10; i++){
            player.endTurn();
            player.beginTurn();
            System.out.println("NEW TURN");
            map.debugPrint();
            Thread.sleep(1000);
        }
    }
<<<<<<< HEAD
        return new ArrayList<>(Arrays.asList(tiles));
}
*/
    }
=======
>>>>>>> master
}
