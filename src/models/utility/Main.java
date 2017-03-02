package models.utility;


import application.Game;
import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.ctrlCommand.*;
import models.playerAsset.Assets.*;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;
import models.playerAsset.Iterators.AssetIterator;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //new testArmyCreationAndMovement().run();
        //new testIterator().run();
        //new testCapitalCreation().run();
        //new testAttack().run();
        //new testDecommission().run();
        //new testPowerUpDown().run();
        //new testHeal().run();
        new testReinforceArmy().run();
    }
}

class testReinforceArmy{
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
        _tiles.get(0).add(u0);
        _tiles.get(1).add(u1);
        _tiles.get(2).add(u2);

        Thread.sleep(1000);

        CTRLCreateArmyCommand c = new CTRLCreateArmyCommand();
        c.configure(_tiles.get(73), u0, u1);
        try {c.execute(map, player);} catch(Exception e){System.out.println(e);}
        for (int i = 0; i < 4; i++) {
            player.endTurn();
            player.beginTurn();
            System.out.println("NEW TURN");
            Thread.sleep(1000);
        }

        CTRLReinforceArmyCommand rac = new CTRLReinforceArmyCommand();
        rac.configure(u2, am.debugGetRallyPoint());
        try{rac.execute(map, player);} catch(Exception e){System.out.println(e);}

        for (int i = 0; i < 3; i++) {
            Thread.sleep(1000);
            player.endTurn();
            player.beginTurn();
            System.out.println("NEW TURN");
        }

        CTRLMoveRallyPointCommand mrpc = new CTRLMoveRallyPointCommand();
        mrpc.configure(am.debugGetRallyPoint(), _tiles.get(16));
        try{mrpc.execute(map, player);} catch (Exception e){System.out.println(e);}

        for (int i = 0; i < 6; i++) {
            Thread.sleep(1000);
            player.endTurn();
            player.beginTurn();
            System.out.println("NEW TURN");
        }
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
        Thread.sleep(1000);

        CTRLCreateCapitalCommand ccc = new CTRLCreateCapitalCommand();
        ccc.configure(u0);
        try{ccc.execute(map, player);} catch(Exception e){System.out.println(e);}
        System.out.println("CREATED CAPITAL");
        Thread.sleep(1000);
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
        GameMap map = new GameMap(_tiles, length, length);

        Unit u0 = um.addNewUnit("colonist");
        Unit u1 = um.addNewUnit("colonist");
        Unit u2 = um.addNewUnit("colonist");
        _tiles.get(0).add(u0);
        _tiles.get(1).add(u1);
        _tiles.get(2).add(u2);

        Thread.sleep(1000);

        CTRLCreateArmyCommand CTRLCreateArmyCommand = new CTRLCreateArmyCommand();
        CTRLCreateArmyCommand.configure(_tiles.get(50), u0, u1, u2);
        try{CTRLCreateArmyCommand.execute(map, player);} catch(Exception e){System.out.println(e);}

        RallyPoint rallyPoint = am.debugGetRallyPoint();
        Army army = am.debugGetArmy();

        player.endTurn();
        player.beginTurn();
        System.out.println("NEW TURN");
        Thread.sleep(1000);
        player.endTurn();
        player.beginTurn();
        System.out.println("NEW TURN");
        Thread.sleep(1000);
        player.endTurn();
        player.beginTurn();
        System.out.println("NEW TURN");
        Thread.sleep(1000);
        player.endTurn();
        player.beginTurn();
        System.out.println("NEW TURN");
        Thread.sleep(1000);

        System.out.println("MOVING TOWARDS RALLYPOINT:");
        Thread.sleep(1000);

        System.out.println("MOVED RALLY POINT:");

        CTRLMoveRallyPointCommand mrp = new CTRLMoveRallyPointCommand();

        try{mrp.execute(map, player);} catch(Exception e){System.out.println(e);}
        Thread.sleep(1000);

        for (int i = 0; i < 4; i++) {
            player.endTurn();
            player.beginTurn();
            System.out.println("NEW TURN");
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
        Unit u0 = um.addNewUnit("ranged");
        Unit u1 = um.addNewUnit("colonist");

        Structure s0 = sm.createStructure("capital");

        _tiles.get(2).add(s0);
        _tiles.get(8).add(u0);
        _tiles.get(9).add(u1);

        CTRLCreateArmyCommand ccac = new CTRLCreateArmyCommand();
        ccac.configure(_tiles.get(8), u0, u1);
        try{ccac.execute(map, player);} catch (Exception e){System.out.println(e);}
        for(int i = 0; i < 3; i++){
            Thread.sleep(500);
            player.endTurn();
            player.beginTurn();
        }

        Army s1 = am.debugGetArmy();
        RallyPoint rp = am.debugGetRallyPoint();

        CTRLMoveRallyPointCommand cmrpc = new CTRLMoveRallyPointCommand();
        cmrpc.configure(rp, _tiles.get(4));
        try{cmrpc.execute(map, player);} catch (Exception e){System.out.println(e);}

        CTRLAttackCommand cacmd = new CTRLAttackCommand();
        cacmd.configure(s1, _tiles.get(2), player);
        try{cacmd.execute(map, player);} catch (Exception e){System.out.println(e);}
        try{cacmd.execute(map, player);} catch (Exception e){System.out.println(e);}
        try{cacmd.execute(map, player);} catch (Exception e){System.out.println(e);}
        try{cacmd.execute(map, player);} catch (Exception e){System.out.println(e);}
        try{cacmd.execute(map, player);} catch (Exception e){System.out.println(e);}


        for(int i = 0; i < 6; i++){
            Thread.sleep(500);
            player.endTurn();
            player.beginTurn();
        }
    }
}

class testHeal{
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
        Unit u0 = um.addNewUnit("ranged");
        Unit u1 = um.addNewUnit("colonist");
        Unit u2 = um.addNewUnit("ranged");

        Structure s0 = sm.createStructure("capital");

        _tiles.get(2).add(s0);
        _tiles.get(2).add(u2);
        _tiles.get(8).add(u0);
        _tiles.get(9).add(u1);

        CTRLCreateArmyCommand ccacmd = new CTRLCreateArmyCommand();
        ccacmd.configure(_tiles.get(8), u0, u1);
        try{ccacmd.execute(map, player);} catch (Exception e){System.out.println(e);}

        for(int i = 0; i < 3; i++){
            Thread.sleep(500);
            player.endTurn();
            player.beginTurn();
        }

        Army s1 = am.debugGetArmy();
        RallyPoint rp = am.debugGetRallyPoint();

        CTRLMoveRallyPointCommand cmrpc = new CTRLMoveRallyPointCommand();
        cmrpc.configure(rp, _tiles.get(4));
        try{cmrpc.execute(map, player);} catch(Exception e){System.out.println(e);}
        CTRLAttackCommand catkcmd = new CTRLAttackCommand();
        catkcmd.configure(s1, _tiles.get(2), player);
        try{catkcmd.execute(map, player);} catch(Exception e){System.out.println(e);}

        Thread.sleep(500);
        player.endTurn();
        player.beginTurn();
        Thread.sleep(500);
        player.endTurn();
        player.beginTurn();
        CTRLHealCommand chcmd = new CTRLHealCommand();
        chcmd.configure(s0, _tiles.get(2));
        try{chcmd.execute(map, player);} catch(Exception e){System.out.println(e);}

        catkcmd = new CTRLAttackCommand();
        catkcmd.configure(s1, _tiles.get(2), player);
        try{catkcmd.execute(map, player);} catch(Exception e){System.out.println(e);}

        chcmd = new CTRLHealCommand();
        chcmd.configure(s0, _tiles.get(2));
        try{chcmd.execute(map, player);} catch(Exception e){System.out.println(e);}

        catkcmd = new CTRLAttackCommand();
        catkcmd.configure(s1, _tiles.get(2), player);
        try{catkcmd.execute(map, player);} catch(Exception e){System.out.println(e);}

        chcmd = new CTRLHealCommand();
        chcmd.configure(s0, _tiles.get(2));
        try{chcmd.execute(map, player);} catch(Exception e){System.out.println(e);}


        for(int i = 0; i < 6; i++){
            Thread.sleep(500);
            player.endTurn();
            player.beginTurn();
        }
    }
    }

class testDecommission{
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
        _tiles.get(0).add(u0);
        _tiles.get(1).add(u1);
        _tiles.get(11).add(u2);
        CTRLCreateArmyCommand cacmd = new CTRLCreateArmyCommand();
        cacmd.configure(_tiles.get(0), u0, u1, u2);
        try{cacmd.execute(map, player);} catch(Exception e) {System.out.println(e);}
        Thread.sleep(1000);
        player.endTurn();
        player.beginTurn();
        Thread.sleep(1000);
        CTRLDecommissionCommand cdcmd = new CTRLDecommissionCommand();
        cdcmd.configure(u2);
        try{cdcmd.execute(map, player);} catch (Exception e){System.out.println(e);}
        Thread.sleep(1000);
//        new CTRLDecommissionCommand(u1).execute(map, player);
//        Thread.sleep(1000);
//        new CTRLDecommissionCommand(u2).execute(map, player);
//        Thread.sleep(1000);
    }
}

class testPowerUpDown{
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
        _tiles.get(9).add(u1);
        _tiles.get(10).add(u2);

        CTRLCreateArmyCommand CTRLCreateArmyCommand = new CTRLCreateArmyCommand();
        CTRLCreateArmyCommand.configure(_tiles.get(14), u0, u1, u2);
        try{CTRLCreateArmyCommand.execute(map, player);} catch(Exception e){System.out.println(e);}

        CTRLPowerUpCommand cpupcmd = new CTRLPowerUpCommand();
        cpupcmd.configure(u0);
        try{cpupcmd.execute(map, player);} catch(Exception e){System.out.println(e);}
     //   new CTRLPowerDownCommand(u0).execute(map, player);
        for (int i = 0; i < 4; i++) {
            player.endTurn();
            player.beginTurn();
            System.out.println("NEW TURN");
            Thread.sleep(1000);
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