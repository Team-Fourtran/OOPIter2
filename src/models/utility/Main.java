package models.utility;


import application.Game;
import models.assetOwnership.GameMap;
import models.assetOwnership.PlayerAssetOwnership;
import models.assetOwnership.TileAssociation;
import models.ctrlCommand.*;
import models.playerAsset.Assets.*;
import models.playerAsset.Assets.Structures.*;
import models.playerAsset.Assets.Structures.EnergyHarvestStrategy;
import models.playerAsset.Assets.Structures.FoodHarvestStrategy;
import models.playerAsset.Assets.Structures.OreHarvestStrategy;
import models.playerAsset.Assets.Technology.ArmorTech;
import models.playerAsset.Assets.Technology.FoodProductionTech;
import models.playerAsset.Assets.Technology.Technology;
import models.playerAsset.Assets.Technology.WorkRadiusTech;
import models.playerAsset.Assets.Units.Colonist;
import models.playerAsset.Assets.Units.Unit;
import models.playerAsset.Iterators.AssetIterator;
import models.playerAsset.Iterators.CommandIterator;
import models.visitor.CommandListVisitor;

import java.util.ArrayList;
import java.util.Vector;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        new modelTest();
    }
}

class modelTest{
    private Player player;
    private Player enemyPlayer;
    private Game game;
    private GameMap map;
    private ArrayList<TileAssociation> _tiles;
    private ArmyManager am;
    private UnitManager um;
    private StructureManager sm;
    private ArmyManager amEnemy;
    private UnitManager umEnemy;
    private StructureManager smEnemy;

    modelTest() throws InterruptedException {

        liveTest();
//        configure(); //Comment out KeyboardController in Game
//        testAttack();
//        testCreateUnit();
//        testReinforceArmy();
//        testCapitalCreation();
//        testArmyCreationAndMovement();
//        testHeal();
//        testDecommission();
        //testPowerUpDown();

//        testIterator();
//        testCommandIterator();
//     	  testInfluenceMovement();
//        testInfluenceReaction();
//         testTech();
//        testProduction();


//        testBuild();
//        testHarvest();
//        testPathfinding();
//        testLandMine();
//        testLandMine2();
    }

    private void liveTest() throws InterruptedException {
        int length = 10;
        this.player = new Player("Player 1");
        this.am = player.getArmies();
        this.um = player.getUnits();
        this.sm = player.getStructures();
        this.enemyPlayer = new Player("Player 2");
        this.umEnemy = enemyPlayer.getUnits();
        this.smEnemy = enemyPlayer.getStructures();
        this.amEnemy = enemyPlayer.getArmies();

        TileGen tileGen = new TileGen(length, length);
        this._tiles = tileGen.executeFancy();
        Unit u0 = um.addNewUnit("colonist");
        _tiles.get(23).add(u0);
        Unit u1 = um.addNewUnit("explorer");

        _tiles.get(7).add(u1);
        Unit u2 = um.addNewUnit("melee");
        _tiles.get(8).add(u2);
        Unit u3 = um.addNewUnit("ranged");
        _tiles.get(9).add(u3);

        Unit u4 = umEnemy.addNewUnit("ranged");
        _tiles.get(82).add(u4);
        Unit u5 = umEnemy.addNewUnit("melee");
        _tiles.get(83).add(u5);
        Unit u6 = umEnemy.addNewUnit("ranged");
        _tiles.get(84).add(u6);
        Unit u7 = umEnemy.addNewUnit("melee");
        _tiles.get(85).add(u7);

        Structure ss = sm.createStructure("university", _tiles.get(32));
        _tiles.get(32).add(ss);

        this.game = new Game(player, enemyPlayer, _tiles);
        this.map = game.getMap();

        CTRLCreateArmyCommand cac = new CTRLCreateArmyCommand();
        cac.configure(_tiles.get(16), u1, u2);
        game.notifyOfCommand(cac);
    }

	private void configure() throws InterruptedException {
        int length = 15;
        this.player = new Player("Player 1");
        this.am = player.getArmies();
        this.um = player.getUnits();
        this.sm = player.getStructures();
        this.enemyPlayer = new Player("Player 2");
        this.umEnemy = enemyPlayer.getUnits();
        this.smEnemy = enemyPlayer.getStructures();
        this.amEnemy = enemyPlayer.getArmies();
        TileGen tileGen = new TileGen(length, length);
        this._tiles = tileGen.execute();
        this.game = new Game(player, enemyPlayer, _tiles);
        this.map = game.getMap();
    }

    private void changeTurn(int n) throws InterruptedException {
        for(int i = 0; i < n; i++){
            System.out.println("NewTurn:");
            Thread.sleep(250);
            player.endTurn();
            player.beginTurn();
            Thread.sleep(250);
        }
    }

    private void testAttack() throws InterruptedException {
        Unit u0 = um.addNewUnit("ranged");
        Unit u1 = um.addNewUnit("colonist");
        Structure s1 = sm.createStructure("capital", _tiles.get(4));
        PlayerAssetOwnership.addPlayerAsset(player, u0, u1, s1);

        Structure s0 = smEnemy.createStructure("capital", _tiles.get(6));
        PlayerAssetOwnership.addPlayerAsset(enemyPlayer, s0);

        map.addAssetToMap(s0, _tiles.get(6));
        map.addAssetToMap(s1, _tiles.get(4));
        map.addAssetToMap(u0, _tiles.get(11));
        map.addAssetToMap(u1, _tiles.get(12));

        CTRLCreateArmyCommand ccac = new CTRLCreateArmyCommand();
        ccac.configure(_tiles.get(10), u0, u1);
        game.notifyOfCommand(ccac);

        changeTurn(3);

        Army army = am.debugGetArmy();
        RallyPoint rp = am.debugGetRallyPoint();

        CTRLMoveRallyPointCommand cmrpc = new CTRLMoveRallyPointCommand();
        cmrpc.configure(rp, _tiles.get(8));
        game.notifyOfCommand(cmrpc);

        CTRLAttackCommand cacmd = new CTRLAttackCommand();
        cacmd.configure(army, _tiles.get(6));
        game.notifyOfCommand(cacmd);
        game.notifyOfCommand(cacmd);
        game.notifyOfCommand(cacmd);
        game.notifyOfCommand(cacmd);
        game.notifyOfCommand(cacmd);

        changeTurn(6);
        
        // test attacking friendly force
        CTRLMoveRallyPointCommand cmrpcFriendly = new CTRLMoveRallyPointCommand();
        cmrpcFriendly.configure(rp, _tiles.get(6));
        game.notifyOfCommand(cmrpcFriendly);
        
        CTRLAttackCommand cacmdFriendly = new CTRLAttackCommand();
        cacmdFriendly.configure(army, _tiles.get(4));
        game.notifyOfCommand(cacmdFriendly);
        game.notifyOfCommand(cacmdFriendly);
        game.notifyOfCommand(cacmdFriendly);
        game.notifyOfCommand(cacmdFriendly);
        game.notifyOfCommand(cacmdFriendly);

        changeTurn(6);
    }

    private void testCreateUnit() throws InterruptedException{
        Structure s0 = sm.createStructure("capital", _tiles.get(22));
        Unit u0 = um.addNewUnit("melee");
        Unit u1 = um.addNewUnit("colonist");
        map.addAssetToMap(s0, _tiles.get(22));
        map.addAssetToMap(u0, _tiles.get(67));
        map.addAssetToMap(u1, _tiles.get(67));
        PlayerAssetOwnership.addPlayerAsset(player, s0, u0, u1);

        CTRLCreateArmyCommand c = new CTRLCreateArmyCommand();
        c.configure(_tiles.get(73), u0, u1);

        game.notifyOfCommand(c);

        changeTurn(4);

        CTRLCreateUnitCommand cmd = new CTRLCreateUnitCommand();
        cmd.configure(s0, "melee");

        game.notifyOfCommand(cmd);

        changeTurn(4);
    }

    private void testReinforceArmy() throws InterruptedException{
        Unit u0 = um.addNewUnit("colonist");
        Unit u1 = um.addNewUnit("colonist");
        Unit u2 = um.addNewUnit("colonist");
        map.addAssetToMap(u0, _tiles.get(0));
        map.addAssetToMap(u1, _tiles.get(1));
        map.addAssetToMap(u2, _tiles.get(2));
        PlayerAssetOwnership.addPlayerAsset(player, u0, u1, u2);

        Thread.sleep(1000);

        CTRLCreateArmyCommand c = new CTRLCreateArmyCommand();
        c.configure(_tiles.get(74), u0, u1);
        game.notifyOfCommand(c);

        changeTurn(4);

        CTRLReinforceArmyCommand rac = new CTRLReinforceArmyCommand();
        rac.configure(u2, am.debugGetRallyPoint());
        game.notifyOfCommand(rac);

        changeTurn(2);

        CTRLMoveRallyPointCommand mrpc = new CTRLMoveRallyPointCommand();
        mrpc.configure(am.debugGetRallyPoint(), _tiles.get(223));

        game.notifyOfCommand(mrpc);

        changeTurn(6);
    }

    private void testCapitalCreation() throws InterruptedException{
        Unit u0 = um.addNewUnit("colonist");
        Unit u1 = um.addNewUnit("colonist");
        Unit u2 = um.addNewUnit("colonist");
        map.addAssetToMap(u0, _tiles.get(4));
        map.addAssetToMap(u1, _tiles.get(3));
        map.addAssetToMap(u2, _tiles.get(24));
        PlayerAssetOwnership.addPlayerAsset(player, u0, u1, u2);
        Thread.sleep(1000);

        CTRLCreateCapitalCommand ccc = new CTRLCreateCapitalCommand();
        ccc.configure(u0);
        game.notifyOfCommand(ccc);
        System.out.println("CREATED CAPITAL");
        changeTurn(1);
    }


    private void testArmyCreationAndMovement() throws InterruptedException{
        Unit u0 = um.addNewUnit("ranged");
        Unit u1 = um.addNewUnit("melee");
        Unit u2 = um.addNewUnit("melee");
        // PlayerOwnership.add(player, u0, u1, u2)
        map.addAssetToMap(u0, _tiles.get(8));
        map.addAssetToMap(u1, _tiles.get(9));
        map.addAssetToMap(u2, _tiles.get(10));
        PlayerAssetOwnership.addPlayerAsset(player, u0, u1, u2);

        changeTurn(1);

        CTRLCreateArmyCommand cac = new CTRLCreateArmyCommand();
        cac.configure(_tiles.get(57), u0, u1, u2);
        game.notifyOfCommand(cac);

        RallyPoint rallyPoint = am.debugGetRallyPoint();

        changeTurn(4);

        CTRLMoveRallyPointCommand mrp = new CTRLMoveRallyPointCommand();
        mrp.configure(rallyPoint, _tiles.get(12));
        game.notifyOfCommand(mrp);

        changeTurn(4);
        
        Army a = am.debugGetArmy();
        System.out.println(a.getBattleGroup());
    }


    private void testHeal() throws InterruptedException{
        Unit u0 = um.addNewUnit("ranged");
        Unit u1 = um.addNewUnit("colonist");
        Unit u2 = um.addNewUnit("ranged");
        Structure s0 = smEnemy.createStructure("capital", _tiles.get(2));

        map.addAssetToMap(s0, _tiles.get(2));
        map.addAssetToMap(u2, _tiles.get(2));
        map.addAssetToMap(u0, _tiles.get(8));
        map.addAssetToMap(u1, _tiles.get(9));
        PlayerAssetOwnership.addPlayerAsset(player, u0, u1, u2);
        PlayerAssetOwnership.addPlayerAsset(enemyPlayer, s0);

        CTRLCreateArmyCommand ccacmd = new CTRLCreateArmyCommand();
        ccacmd.configure(_tiles.get(8), u0, u1);
        game.notifyOfCommand(ccacmd);

        changeTurn(3);

        Army a = am.debugGetArmy();
        RallyPoint rp = am.debugGetRallyPoint();

        CTRLMoveRallyPointCommand cmrpc = new CTRLMoveRallyPointCommand();
        cmrpc.configure(rp, _tiles.get(4));
        game.notifyOfCommand(cmrpc);

        CTRLAttackCommand catkcmd = new CTRLAttackCommand();
        catkcmd.configure(a, _tiles.get(2));
        game.notifyOfCommand(catkcmd);

        changeTurn(2);

        CTRLHealCommand chcmd = new CTRLHealCommand();
        chcmd.configure(s0, _tiles.get(2));
        game.notifyOfCommand(chcmd);

        catkcmd = new CTRLAttackCommand();
        catkcmd.configure(a, _tiles.get(2));
        game.notifyOfCommand(catkcmd);

        chcmd = new CTRLHealCommand();
        chcmd.configure(s0, _tiles.get(2));
        game.notifyOfCommand(chcmd);

        catkcmd = new CTRLAttackCommand();
        catkcmd.configure(a, _tiles.get(2));
        game.notifyOfCommand(catkcmd);

        chcmd = new CTRLHealCommand();
        chcmd.configure(s0, _tiles.get(2));
        game.notifyOfCommand(chcmd);

        changeTurn(6);
    }

    private void testDecommission() throws InterruptedException{
        Unit u0 = um.addNewUnit("colonist");
        Unit u1 = um.addNewUnit("colonist");
        Unit u2 = um.addNewUnit("colonist");
        map.addAssetToMap(u0, _tiles.get(0));
        map.addAssetToMap(u1, _tiles.get(1));
        map.addAssetToMap(u2, _tiles.get(11));
        PlayerAssetOwnership.addPlayerAsset(player, u0, u1, u2);

        CTRLCreateArmyCommand cacmd = new CTRLCreateArmyCommand();
        cacmd.configure(_tiles.get(0), u0, u1, u2);
        game.notifyOfCommand(cacmd);
        Army army = am.debugGetArmy();

        changeTurn(1);

        CTRLDecommissionCommand cdcmd = new CTRLDecommissionCommand();
        cdcmd.configure(army);
        game.notifyOfCommand(cdcmd);


        changeTurn(1);
    }

    private void testPowerUpDown() throws InterruptedException{
        Unit u0 = um.addNewUnit("colonist");
        Unit u1 = um.addNewUnit("colonist");
        Unit u2 = um.addNewUnit("colonist");
        map.addAssetToMap(u0, _tiles.get(4));
        map.addAssetToMap(u1, _tiles.get(9));
        map.addAssetToMap(u2, _tiles.get(10));
        PlayerAssetOwnership.addPlayerAsset(player, u0, u1, u2);

        CTRLCreateArmyCommand cac = new CTRLCreateArmyCommand();
        cac.configure(_tiles.get(14), u0, u1, u2);
        game.notifyOfCommand(cac);

        CTRLPowerUpCommand cpupcmd = new CTRLPowerUpCommand();
        cpupcmd.configure(u0);
        game.notifyOfCommand(cpupcmd);

        changeTurn(4);
    }

    private void testIterator() throws InterruptedException{
    	// Need to store references to these created Units, so that they can be assigned to the Player, to work in application as whole
    	// PlayerAssetOwnership.addPlayerAsset(player, u0, ...);
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


        sm.createStructure("capital", null);
        sm.createStructure("capital", null);
        sm.createStructure("capital", null);
        sm.createStructure("capital", null);
        sm.createStructure("capital", null);

        AssetIterator iter = player.makeIterator();

        iter.first();
        printIterator(iter);

        for (int i = 0; i < 4; i++){
            iter.next();
            printIterator(iter);
        }
        for (int i = 0; i < 4; i++){
            iter.prev();
            printIterator(iter);
        }
        for (int i = 0; i < 4; i++){
            iter.nextType();
            printIterator(iter);
        }
        for (int i = 0; i < 4; i++){
            iter.prevType();
            printIterator(iter);
        }
        for (int i = 0; i < 4; i++){
            iter.nextInstance();
            printIterator(iter);
        }
        for (int i = 0; i < 4; i++){
            iter.prevInstance();
            printIterator(iter);
        }
    }

    private void testCommandIterator() throws InterruptedException{
        Unit u0 = um.addNewUnit("colonist");
        Structure s0 = sm.createStructure("fort", null);
        PlayerAssetOwnership.addPlayerAsset(player, u0, s0);

        CTRLPowerUpCommand cmd = new CTRLPowerUpCommand();
        cmd.configure(s0);
        game.notifyOfCommand(cmd);

        CommandListVisitor v = new CommandListVisitor();
        s0.accept(v);
        CommandIterator iter = v.getIterator();
    }

    private void testPathfinding() throws InterruptedException{
        Unit u0 = um.addNewUnit("colonist");
        map.addAssetToMap(u0, _tiles.get(203));
        PlayerAssetOwnership.addPlayerAsset(player, u0);

        changeTurn(1);

        CTRLCreateArmyCommand cac = new CTRLCreateArmyCommand();
        cac.configure(_tiles.get(203), u0);
        game.notifyOfCommand(cac);

        RallyPoint rallyPoint = am.debugGetRallyPoint();

        changeTurn(4);

        CTRLMoveRallyPointCommand mrp = new CTRLMoveRallyPointCommand();
        mrp.configure(rallyPoint, _tiles.get(0));
        game.notifyOfCommand(mrp);

        changeTurn(10);
    }
    
    private void testInfluenceMovement() throws InterruptedException {
        Unit u0 = um.addNewUnit("melee");
        Unit u1 = um.addNewUnit("melee");
        Unit u2 = um.addNewUnit("melee");
        Unit u3 = um.addNewUnit("ranged");
        map.addAssetToMap(u0, _tiles.get(8));
        map.addAssetToMap(u1, _tiles.get(9));
        map.addAssetToMap(u2, _tiles.get(10));
        map.addAssetToMap(u3, _tiles.get(11));
        PlayerAssetOwnership.addPlayerAsset(player, u0, u1, u2, u3);

        changeTurn(1);

        CTRLCreateArmyCommand cac = new CTRLCreateArmyCommand();
        cac.configure(_tiles.get(24), u0, u1, u2);
        game.notifyOfCommand(cac);

        RallyPoint rallyPoint = am.debugGetRallyPoint();

        changeTurn(4);
        Army a = am.debugGetArmy();
        Vector<TileAssociation> v0 = map.getRadiusOfInfluence(a);
        for (int i = 0; i < v0.size(); i++) {
        	v0.get(i).add(u0);
        }
        
        CTRLMoveRallyPointCommand mrp = new CTRLMoveRallyPointCommand();
        mrp.configure(rallyPoint, _tiles.get(100));
        game.notifyOfCommand(mrp);

        for (int i = 0; i < v0.size(); i++) {
        	v0.get(i).remove(u0);
        }       
        
        changeTurn(4);
        
        Vector<TileAssociation> v1 = map.getRadiusOfInfluence(a);
        for (int i = 0; i < v1.size(); i++) {
        	v1.get(i).add(u0);
        }
        
        // add ranged unit as reinforcement
        CTRLReinforceArmyCommand rac = new CTRLReinforceArmyCommand();
        rac.configure(u3, am.debugGetRallyPoint());
        game.notifyOfCommand(rac);

        for (int i = 0; i < v1.size(); i++) {
        	v1.get(i).remove(u0);
        }       
        
        changeTurn(4);
        
        Vector<TileAssociation> v2 = map.getRadiusOfInfluence(a);
        for (int i = 0; i < v2.size(); i++) {
        	v2.get(i).add(u0);
        }
	}
    
	private void testInfluenceReaction() throws InterruptedException {
		// Create a fort
		// Have an army enter the RoI of the fort
		// The fort will receive some notification to attack
		
		// Create fort
		Structure s0 = sm.createStructure("fort", _tiles.get(4));
		map.addAssetToMap(s0, _tiles.get(4));
        PlayerAssetOwnership.addPlayerAsset(player, s0);
		
        // Create enemy army
        Unit u0 = umEnemy.addNewUnit("melee");
        map.addAssetToMap(u0, _tiles.get(8));
        PlayerAssetOwnership.addPlayerAsset(enemyPlayer, u0);
        
        changeTurn(2);
        
        this.player = enemyPlayer;
        game.setCurrentPlayer(enemyPlayer);
        CTRLCreateArmyCommand cac = new CTRLCreateArmyCommand();
        cac.configure(_tiles.get(6), u0);
        game.notifyOfCommand(cac);
        
		changeTurn(1);
		
        this.player = player;
        game.setCurrentPlayer(player);
		
        changeTurn(2);
        
        CTRLDecommissionCommand cdcmd = new CTRLDecommissionCommand();
        cdcmd.configure(s0);
        game.notifyOfCommand(cdcmd);

        changeTurn(1);
	}

    private void testLandMine() throws InterruptedException{
        Unit u0 = um.addNewUnit("colonist");
        Unit u1 = um.addNewUnit("colonist");
        map.addAssetToMap(u0, _tiles.get(10));
        map.addAssetToMap(u1, _tiles.get(11));
        PlayerAssetOwnership.addPlayerAsset(player, u0, u1);

        changeTurn(1);

        CTRLCreateArmyCommand cac = new CTRLCreateArmyCommand();
        cac.configure(_tiles.get(117), u0, u1);
        game.notifyOfCommand(cac);

        RallyPoint rallyPoint = am.debugGetRallyPoint();

        changeTurn(4);

        CTRLMoveRallyPointCommand mrp = new CTRLMoveRallyPointCommand();
        mrp.configure(rallyPoint, _tiles.get(124));
        game.notifyOfCommand(mrp);

        changeTurn(8);
    }

    private void testLandMine2() throws InterruptedException{
        Unit u0 = um.addNewUnit("colonist");
        Unit u1 = um.addNewUnit("colonist");
        map.addAssetToMap(u0, _tiles.get(26));
        map.addAssetToMap(u1, _tiles.get(27));
        PlayerAssetOwnership.addPlayerAsset(player, u0, u1);

        changeTurn(1);

        CTRLCreateArmyCommand cac = new CTRLCreateArmyCommand();
        cac.configure(_tiles.get(99), u0, u1);
        game.notifyOfCommand(cac);

        Unit u2 = um.addNewUnit("colonist");
        map.addAssetToMap(u2, _tiles.get(157));

        changeTurn(4);

        CTRLReinforceArmyCommand rac = new CTRLReinforceArmyCommand();
        rac.configure(u2, am.debugGetRallyPoint());
        game.notifyOfCommand(rac);

        changeTurn(4);

        CTRLMoveRallyPointCommand mrp = new CTRLMoveRallyPointCommand();
        mrp.configure(am.debugGetRallyPoint(), _tiles.get(97));
        game.notifyOfCommand(mrp);

        changeTurn(4);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
    }

    private void testBuild() throws InterruptedException{
        Unit u0 = um.addNewUnit("colonist");
        Unit u1 = um.addNewUnit("colonist");
        Unit u2 = um.addNewUnit("melee");
        map.addAssetToMap(u0, _tiles.get(4));
        map.addAssetToMap(u1, _tiles.get(3));
        map.addAssetToMap(u2, _tiles.get(5));
        PlayerAssetOwnership.addPlayerAsset(player, u0, u1, u2);
        
        CTRLCreateArmyCommand cac = new CTRLCreateArmyCommand();
        cac.configure(_tiles.get(7), u0, u1);
        game.notifyOfCommand(cac);

        changeTurn(3);

        CTRLMoveRallyPointCommand cmr = new CTRLMoveRallyPointCommand();
        cmr.configure(am.debugGetRallyPoint(), _tiles.get(174));
        game.notifyOfCommand(cmr);
        CTRLBuildCommand cbc = new CTRLBuildCommand();
        try{cbc.configure(am.debugGetRallyPoint(), "fort", 0);} catch (Exception e){e.printStackTrace();}
        game.notifyOfCommand(cbc);
        changeTurn(8);
        CTRLReinforceArmyCommand rac = new CTRLReinforceArmyCommand();
        try{rac.configure(u2, am.debugGetRallyPoint());} catch(Exception e){e.printStackTrace();}
        game.notifyOfCommand(rac);

        cmr = new CTRLMoveRallyPointCommand();
        cmr.configure(am.debugGetRallyPoint(), _tiles.get(216));
        game.notifyOfCommand(cmr);
        changeTurn(12);
    }


    private void testTech() {
        Player p1 = new Player("Chaz");
        //StructureManager m = new StructureManager();
        Structure farm = p1.getStructures().createStructure("farm", _tiles.get(4));
//        Structure capital = p1.getStructures().createStructure("capital",_tiles.get(4));
        Structure fort = p1.getStructures().createStructure("fort",_tiles.get(4));

        Farm farm1 = (Farm) farm;
        farm1.printStats();
        System.out.println();
//        Capital c = (Capital) capital;
//        c.printStats();
        System.out.println();

        CTRLResearchTechnologyCommand com1 = new CTRLResearchTechnologyCommand();
        com1.configure("food", "worker", new University());
        com1.execute(map, p1);

        CTRLResearchTechnologyCommand com2 = new CTRLResearchTechnologyCommand();
        com2.configure("radius", "worker", new University());
        com2.execute(map, p1);

        CTRLResearchTechnologyCommand com3 = new CTRLResearchTechnologyCommand();
        com3.configure("energy", "worker", new University());
        com3.execute(map, p1);

        farm1.printStats();
        System.out.println();
//        c.printStats();
        System.out.println();

        Structure capital1 = p1.getStructures().createStructure("capital", _tiles.get(4));
        Structure powerplant = p1.getStructures().createStructure("powerplant", _tiles.get(4));
        Capital c1 = (Capital) capital1;
        PowerPlant power = (PowerPlant) powerplant;

        c1.printStats();
        System.out.println();
        power.printStats();
        System.out.println();

        System.out.println(fort.getMaxHealth());
        System.out.println(fort.getDefDamage(0));

    }

    private void printIterator(AssetIterator iter) {
        System.out.println(iter.getCurrentMode() + "  " + iter.getElement() + "  " + iter.getElement() + "  " + iter.current().toString());
    }
    private void testHarvest() throws InterruptedException {
    	// put a capital on this same tile
    	Structure s1 = sm.createStructure("powerplant", _tiles.get(4)); // add to manager
    	PlayerAssetOwnership.addPlayerAsset(player, s1); // add to player list. Actually this could be done in the managers maybe
    	map.addAssetToMap(s1, _tiles.get(4)); // add to map
    	
    	ResourceStructure t = (ResourceStructure) s1; // need to treat it as a resource structure
    	ArrayList<Worker> worker = new ArrayList<Worker>();
    	worker.add(new Worker());
    	t.addWorkersToIdle(worker);
    	
    	// configure tile with food resource
    	_tiles.get(4).getResourcePackage().setEnergyCount(50);
    	
    	// start to harvest
    	CTRLHarvestCommand chc = new CTRLHarvestCommand();
    	chc.configure(t, _tiles.get(4), 1); // pick any tile. won't perform if not in work radius. have view show work radius properly
    	game.notifyOfCommand(chc);

    	changeTurn(6);

    }

    public void testProduction() throws InterruptedException{
        Structure farm = sm.createStructure("farm", _tiles.get(4));
        Farm f = (Farm)farm;
        f.addWorkers(10);
        f.harvestTest(1000);

        CTRLProduceCommand cmd = new CTRLProduceCommand();
        cmd.configure(f,10,"food");
        game.notifyOfCommand(cmd);

        System.out.println(f.getHarvestCount("food"));
        System.out.println(f.getProductionCount("food"));

        changeTurn(5);

        System.out.println(f.getHarvestCount("food"));
        System.out.println(f.getProductionCount("food"));

        CTRLProduceCommand cmd2 = new CTRLProduceCommand();
        cmd2.configure(f,0,"food");
        game.notifyOfCommand(cmd2);

        changeTurn(5);



        //System.out.println(f.getHarvestCount());
        //f.produce("food");
        //System.out.println(f.getHarvestCount());
        //System.out.println(f.getProduced());
        //f.addWorkers(10000);
        //f.produce("food");
        //System.out.println(f.getHarvestCount());
        //System.out.println(f.getProduced());

    }
}

