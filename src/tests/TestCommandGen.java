package tests;

import controllers.*;
import models.playerAssetNew.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class TestCommandGen{
    public static void main(String[] args){
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

        AssetIterator assIter = new AssetIterator(lists);
        KeyboardController kbc = new KeyboardController(keySim, assIter);


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