package models.utility;


import application.Game;
import models.assetOwnership.GameMap;
import models.assetOwnership.PlayerAssetOwnership;
import models.assetOwnership.TileAssociation;
import models.ctrlCommand.*;
import models.playerAsset.Assets.*;
import models.playerAsset.Assets.Structures.EnergyHarvestStrategy;
import models.playerAsset.Assets.Structures.FoodHarvestStrategy;
import models.playerAsset.Assets.Structures.OreHarvestStrategy;
import models.playerAsset.Assets.Structures.ResourceStructure;
import models.playerAsset.Assets.Structures.Structure;
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
	private Player currentPlayer;
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
        configure();
//        testAttack();
//        testCreateUnit();
//        testReinforceArmy();
//        testCapitalCreation();
//        testArmyCreationAndMovement();
//        testHeal();
//        testDecommission();
//        testPowerUpDown();
        //testIterator();
//        testCommandIterator();
//     	  testInfluenceMovement();
//        testInfluenceReaction();
//        testBuild();
//          testHarvest();
//        testPathfinding();
//        testLandMine();
        testLandMine2();
    }

	private void configure() throws InterruptedException {
        int length = 15;
        this.player = new Player();
        this.enemyPlayer = new Player();
        this.currentPlayer = player;
        TileGen tileGen = new TileGen(length, length);
        this._tiles = tileGen.executeFancy();
        this.game = new Game(player, _tiles);
        this.map = game.getMap();
        this.am = player.getArmies();
        this.um = player.getUnits();
        this.sm = player.getStructures();
        this.amEnemy = enemyPlayer.getArmies();
        this.umEnemy = enemyPlayer.getUnits();
        this.smEnemy = enemyPlayer.getStructures();
    }

    private void changeTurn(int n) throws InterruptedException {
        for(int i = 0; i < n; i++){
            System.out.println("NewTurn:");
            Thread.sleep(250);
            currentPlayer.endTurn();
            currentPlayer.beginTurn();
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

        changeTurn(3);

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

        AssetIterator iter = player.getAssetIterator();

        iter.first();
        iter.getCurrentMode();
        iter.getCurrentType();
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
        
        this.currentPlayer = enemyPlayer;
        game.setCurrentPlayer(enemyPlayer);
        CTRLCreateArmyCommand cac = new CTRLCreateArmyCommand();
        cac.configure(_tiles.get(6), u0);
        game.notifyOfCommand(cac);
        
		changeTurn(1);
		
        this.currentPlayer = player;
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
        cbc.configure(am.debugGetRallyPoint(), "fort", 0);
        game.notifyOfCommand(cbc);
        changeTurn(8);
        CTRLReinforceArmyCommand rac = new CTRLReinforceArmyCommand();
        rac.configure(u2, am.debugGetRallyPoint());
        game.notifyOfCommand(rac);

        cmr = new CTRLMoveRallyPointCommand();
        cmr.configure(am.debugGetRallyPoint(), _tiles.get(216));
        game.notifyOfCommand(cmr);
        changeTurn(12);
    }
    
    private void testHarvest() throws InterruptedException {
    	// configure tile with food resource
    	_tiles.get(4).getResourcePackage().setFoodCount(5);
    	// put a capital on this same tile
    	Structure s1 = sm.createStructure("capital", _tiles.get(4)); // add to manager
    	// how can we configure the capital's work radius?
    	PlayerAssetOwnership.addPlayerAsset(player, s1); // add to player list. Actually this could be done in the managers maybe
    	map.addAssetToMap(s1, _tiles.get(4)); // add to map
    	ResourceStructure t = (ResourceStructure) s1; // need to treat it as a resource structure
    	t.setHarvestType(new FoodHarvestStrategy(t.getWorkRadius()));
    	t.startHarvest();
    	
    	_tiles.get(4).getResourcePackage().setFoodCount(0);
    	_tiles.get(5).getResourcePackage().setFoodCount(5);
    	t.setRadiusSize(1); // increase radius
    	t.startHarvest();
    	
    	// change strategy to ore harvest
    	_tiles.get(3).getResourcePackage().setOreCount(1);
    	t.setHarvestType(new OreHarvestStrategy(t.getWorkRadius()));
    	t.startHarvest();
    	
    	// change strategy to energy harvest
    	_tiles.get(5).getResourcePackage().setEnergyCount(2);
    	t.setHarvestType(new EnergyHarvestStrategy(t.getWorkRadius()));
    	t.startHarvest();
    	
    	t.setRadiusSize(0);
    	t.startHarvest(); // shouldn't be able to harvest that energy anymore
    }
}

