package tests;

import application.Game;
import controllers.*;
import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.CTRLCreateArmyCommand;
import models.command.MoveRallyPointCommand;
import models.playerAsset.*;
import models.utility.TileGen;

import java.util.ArrayList;
import java.util.HashMap;

public class TestCommandGen{
    public static void main(String[] args) throws InterruptedException{
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





        //Make a hashmap for simulating keypresses
        HashMap<String, Boolean> keySimMap = new HashMap<String, Boolean>();
        keySimMap.put("ENTER",false);
        keySimMap.put("CONTROL",false);
        keySimMap.put("UP",false);
        keySimMap.put("DOWN",false);
        keySimMap.put("LEFT",false);
        keySimMap.put("RIGHT",false);

        //Make a KeyPressInformer, which alerts registered listeners of changes in the hashmap
        KeyPressInformer keySim = new KeyPressInformer(keySimMap);


        ArrayList<ArrayList<PlayerAsset>> lists = new ArrayList<>();
        ArrayList<PlayerAsset> unitList = new ArrayList<>();
        unitList.add(new PlayerAsset("Unit","Explorer",1));
        unitList.add(new PlayerAsset("Unit","Explorer",2));
        unitList.add(new PlayerAsset("Unit","Melee",3));
        unitList.add(new PlayerAsset("Unit","Melee",4));
        unitList.add(new PlayerAsset("Unit","Range",5));
        unitList.add(new PlayerAsset("Unit","Range",6));
        lists.add(unitList);

        ArrayList<PlayerAsset> armyList = new ArrayList<>();
        PlayerAsset army1 = new PlayerAsset("Army","None",1);
        armyList.add(army1);
        PlayerAsset army2 = new PlayerAsset("Army","None",2);
        armyList.add(army2);
        PlayerAsset army3 = new PlayerAsset("Army","None",3);
        armyList.add(army3);
        PlayerAsset army4 = new PlayerAsset("Army","None",4);
        armyList.add(army4);
        lists.add(armyList);

        ArrayList<PlayerAsset> structureList = new ArrayList<>();
        structureList.add(new PlayerAsset("Structure","Farm",1));
        structureList.add(new PlayerAsset("Structure","Mine",2));
        structureList.add(new PlayerAsset("Structure","Capital",3));
        structureList.add(new PlayerAsset("Structure","Fort",4));
        structureList.add(new PlayerAsset("Structure","University",5));
        lists.add(structureList);


        AssetIterator assIter = player.makeAssetIterator();

        KeyboardController kbc = new KeyboardController(keySim, assIter);







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
        MoveRallyPointCommand mrp = new MoveRallyPointCommand(rallyPoint, _tiles.get(24), map);
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

        /* Iterator testing
         *
        for(int x = 0; x < 5; x++){
            System.out.println("==========\tTesting .next()\t==========");
            for(int i = 0; i < 10; i++){
                System.out.printf("%40s\t\t->\t\t", "["+assIter.getCurrent()+"]");
                assIter.next();
                System.out.printf("%s","[" + assIter.getCurrent() + "]\n");
            }

            assIter.previous(); //Set it back for consistency between tests

            System.out.println("\n==========\tTesting .previous()\t==========");
            for(int i = 0; i < 10; i++){
                System.out.printf("%40s\t\t->\t\t", "["+assIter.getCurrent()+"]");
                assIter.previous();
                System.out.printf("%s","[" + assIter.getCurrent() + "]\n");
            }

            System.out.printf("\n\n|||||||||||||||||||||Next mode...|||||||||||||||||||||\n\n");

            assIter.nextMode();
        }

        System.out.printf("\n\n|||||||||||||||||||||Previous mode...|||||||||||||||||||||\n\n");

        assIter.prevMode();

        for(int x = 0; x < 5; x++){
            System.out.println("==========\tTesting .next()\t==========");
            for(int i = 0; i < 10; i++){
                System.out.printf("%40s\t\t->\t\t", "["+assIter.getCurrent()+"]");
                assIter.next();
                System.out.printf("%s","[" + assIter.getCurrent() + "]\n");
            }

            assIter.previous(); //Set it back for consistency between tests

            System.out.println("\n==========\tTesting .previous()\t==========");
            for(int i = 0; i < 10; i++){
                System.out.printf("%40s\t\t->\t\t", "["+assIter.getCurrent()+"]");
                assIter.previous();
                System.out.printf("%s","[" + assIter.getCurrent() + "]\n");
            }

            System.out.printf("\n\n|||||||||||||||||||||Previous mode...|||||||||||||||||||||\n\n");

            assIter.prevMode();
        }

        */

    }

}