package tests;

import application.Game;
import controllers.*;
import models.assetOwnership.GameMap;
import models.playerAsset.Assets.*;
import models.playerAsset.Iterators.CommandIterator;
import models.playerAsset.Iterators.AssetIterator;

import models.assetOwnership.TileAssociation;
import models.utility.TileGen;
import views.MainScreen;


import java.util.ArrayList;
import java.util.HashMap;

public class TestCommandGen{
    private static HashMap<String, Boolean> keySimMap;
    private static KeyPressInformer keySim;

    public static void main(String[] args) throws InterruptedException{
        int length = 10;
        Player player = new Player();
        ArmyManager am = player.getArmies();
        UnitManager um = player.getUnits();
        StructureManager sm = player.getStructures();



        um.addNewUnit("colonist");
        um.addNewUnit("colonist");
        um.addNewUnit("colonist");
        um.addNewUnit("explorer");
        um.addNewUnit("explorer");
        um.addNewUnit("explorer");
        um.addNewUnit("melee");
        um.addNewUnit("melee");
        um.addNewUnit("melee");
        um.addNewUnit("ranged");
        um.addNewUnit("ranged");
        um.addNewUnit("ranged");


        sm.createStructure("fort");
        sm.createStructure("university");
        sm.createStructure("mine");
        sm.createStructure("farm");
        sm.createStructure("university");

        //Make a hashmap for simulating keypresses
        keySimMap = new HashMap<String, Boolean>();
        keySimMap.put("ENTER",false);
        keySimMap.put("CONTROL",false);
        keySimMap.put("UP",false);
        keySimMap.put("DOWN",false);
        keySimMap.put("LEFT",false);
        keySimMap.put("RIGHT",false);

        //Make a KeyPressInformer, which alerts registered listeners of changes in the hashmap
        keySim = new KeyPressInformer(keySimMap);

        AssetIterator assIter = player.makeIterator();

        TileGen tileGen = new TileGen(length, length);
        ArrayList<TileAssociation> _tiles = tileGen.executeFancy();

        Game thisGame = new Game(player, _tiles);
        GameMap map = new GameMap(_tiles, 5, 5);

        /*  Tests  */
        //testModeIteration();
        //testTypeIteration();
        //testInstanceIteration();

    }
//    private static void testModeIteration(){
//        System.out.println("\n\tTESTING CONTROL-UP\n");
//        pressKeys("CONTROL UP");
//        pressKeys("ENTER");
//        pressKeys("CONTROL UP");
//        pressKeys("ENTER");
//        pressKeys("CONTROL UP");
//        pressKeys("ENTER");
//        pressKeys("CONTROL UP");
//        pressKeys("ENTER");
//        System.out.println("\n\tTESTING CONTROL-DOWN\n");
//        pressKeys("CONTROL DOWN");
//        pressKeys("ENTER");
//        pressKeys("CONTROL DOWN");
//        pressKeys("ENTER");
//        pressKeys("CONTROL DOWN");
//        pressKeys("ENTER");
//        pressKeys("CONTROL DOWN");
//        pressKeys("ENTER");
//    }
//    private static void testTypeIteration(){
//        System.out.println("\n\tTESTING CONTROL-LEFT\n");
//        pressKeys("ENTER");
//        pressKeys("CONTROL LEFT");
//        pressKeys("ENTER");
//        pressKeys("CONTROL LEFT");
//        pressKeys("ENTER");
//        pressKeys("CONTROL LEFT");
//        pressKeys("ENTER");
//        pressKeys("CONTROL LEFT");
//        pressKeys("ENTER");
//        pressKeys("CONTROL LEFT");
//        pressKeys("ENTER");
//        pressKeys("CONTROL LEFT");
//        pressKeys("ENTER");
//        System.out.println("\n\tTESTING CONTROL-RIGHT\n");
//        pressKeys("CONTROL RIGHT");
//        pressKeys("ENTER");
//        pressKeys("CONTROL RIGHT");
//        pressKeys("ENTER");
//        pressKeys("CONTROL RIGHT");
//        pressKeys("ENTER");
//        pressKeys("CONTROL RIGHT");
//        pressKeys("ENTER");
//        pressKeys("CONTROL RIGHT");
//        pressKeys("ENTER");
//        pressKeys("CONTROL RIGHT");
//        pressKeys("ENTER");
//        pressKeys("CONTROL RIGHT");
//        pressKeys("ENTER");
//        pressKeys("CONTROL RIGHT");
//        pressKeys("ENTER");
//        System.out.println("\n\tTESTING CONTROL-RIGHT/CONTROL-LEFT TOGGLE\n");
//        pressKeys("CONTROL LEFT");
//        pressKeys("ENTER");
//        pressKeys("CONTROL RIGHT");
//        pressKeys("ENTER");
//        pressKeys("CONTROL LEFT");
//        pressKeys("ENTER");
//        pressKeys("CONTROL RIGHT");
//        pressKeys("ENTER");
//    }
//    private static void testInstanceIteration(){
//        for(int i = 0; i < 5; i++){
//            System.out.print("Press ENTER:\t");
//            keySim.update("ENTER",true);
//            keySim.update("ENTER",false);
//            System.out.print("\n");
//
//            System.out.print("Press LEFT:\t");
//            keySim.update("LEFT",true);
//            keySim.update("LEFT",false);
//            System.out.print("\n");
//
//            System.out.print("Press ENTER:\t");
//            keySim.update("ENTER",true);
//            keySim.update("ENTER",false);
//            System.out.print("\n");
//        }
//        System.out.print("\n");
//        System.out.print("\n");
//        for(int i = 0; i < 5; i++){
//            System.out.print("Press ENTER:\t");
//            keySim.update("ENTER",true);
//            keySim.update("ENTER",false);
//            System.out.print("\n");
//
//            System.out.print("Press RIGHT:\t");
//            keySim.update("RIGHT",true);
//            keySim.update("RIGHT",false);
//            System.out.print("\n");
//
//            System.out.print("Press ENTER:\t");
//            keySim.update("ENTER",true);
//            keySim.update("ENTER",false);
//            System.out.print("\n");
//        }
//        System.out.print("\n");
//        System.out.print("\n");
//        for(int i = 0; i < 5; i++){
//            System.out.print("Press ENTER:\t");
//            keySim.update("ENTER",true);
//            keySim.update("ENTER",false);
//            System.out.print("\n");
//
//            System.out.print("Press LEFT:\t");
//            keySim.update("LEFT",true);
//            keySim.update("LEFT",false);
//            System.out.print("\n");
//
//            System.out.print("Press ENTER:\t");
//            keySim.update("ENTER",true);
//            keySim.update("ENTER",false);
//            System.out.print("\n");
//        }
//    }
//
//    private static void pressKeys(String keyList){
//        System.out.print("Pressing [" + keyList + "]\t\t\t");
//        String[] keys = keyList.split(" ");
//        for(int k = 0; k<keys.length; k++){
//            keySim.update(keys[k],true);
//        }
//        for(int f = 0; f<keys.length; f++){
//            keySim.update(keys[f],false);
//        }
//        System.out.println();
//    }
}