package models.utility;


import application.Game;
import models.assetOwnership.TileAssociation;
import models.ctrlCommand.*;
import models.playerAsset.Assets.*;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;
import models.playerAsset.Iterators.AssetIterator;
import models.playerAsset.Iterators.CommandIterator;
import models.visitor.CommandListVisitor;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        new modelTest();
    }
}

class modelTest{
    private Player player;
    private Game game;
    private ArrayList<TileAssociation> _tiles;
    private ArmyManager am;
    private UnitManager um;
    private StructureManager sm;

    modelTest() throws InterruptedException {
        configure();
        //testAttack();
        //testCreateUnit();
        //testReinforceArmy();
        //testCapitalCreation();
        //testArmyCreationAndMovement();
        //testHeal();
        //testDecommission();
        //testPowerUpDown();
        //testIterator();
        //testCommandIterator();
        //testPathfinding();
        //testLandMine();
        //testLandMine2();
        testBuild();
    }

    private void configure() throws InterruptedException {
        int length = 15;
        this.player = new Player();
        TileGen tileGen = new TileGen(length, length);
        this._tiles = tileGen.execute();
        this.game = new Game(player, _tiles);
        this.am = player.getArmies();
        this.um = player.getUnits();
        this.sm = player.getStructures();
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
        Structure s0 = sm.createStructure("capital");

        _tiles.get(6).add(s0);
        _tiles.get(11).add(u0);
        _tiles.get(12).add(u1);

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
        cacmd.configure(army, _tiles.get(6), player);
        game.notifyOfCommand(cacmd);
        game.notifyOfCommand(cacmd);
        game.notifyOfCommand(cacmd);
        game.notifyOfCommand(cacmd);
        game.notifyOfCommand(cacmd);

        changeTurn(6);
    }

    private void testCreateUnit() throws InterruptedException{
        Structure s0 = sm.createStructure("capital");
        Unit u0 = um.addNewUnit("melee");
        Unit u1 = um.addNewUnit("colonist");
        _tiles.get(22).add(s0);
        _tiles.get(67).add(u0);
        _tiles.get(67).add(u1);

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
        _tiles.get(0).add(u0);
        _tiles.get(1).add(u1);
        _tiles.get(2).add(u2);

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
        _tiles.get(4).add(u0);
        _tiles.get(3).add(u1);
        _tiles.get(24).add(u2);
        Thread.sleep(1000);

        CTRLCreateCapitalCommand ccc = new CTRLCreateCapitalCommand();
        ccc.configure(u0);
        game.notifyOfCommand(ccc);
        System.out.println("CREATED CAPITAL");
        changeTurn(1);
    }

    private void testArmyCreationAndMovement() throws InterruptedException{
        Unit u0 = um.addNewUnit("colonist");
        Unit u1 = um.addNewUnit("colonist");
        Unit u2 = um.addNewUnit("colonist");
        _tiles.get(8).add(u0);
        _tiles.get(9).add(u1);
        _tiles.get(10).add(u2);

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
    }

    private void testHeal() throws InterruptedException{
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
        game.notifyOfCommand(ccacmd);

        changeTurn(3);

        Army s1 = am.debugGetArmy();
        RallyPoint rp = am.debugGetRallyPoint();

        CTRLMoveRallyPointCommand cmrpc = new CTRLMoveRallyPointCommand();
        cmrpc.configure(rp, _tiles.get(4));
        game.notifyOfCommand(cmrpc);

        CTRLAttackCommand catkcmd = new CTRLAttackCommand();
        catkcmd.configure(s1, _tiles.get(2), player);
        game.notifyOfCommand(catkcmd);

        changeTurn(2);

        CTRLHealCommand chcmd = new CTRLHealCommand();
        chcmd.configure(s0, _tiles.get(2));
        game.notifyOfCommand(chcmd);

        catkcmd = new CTRLAttackCommand();
        catkcmd.configure(s1, _tiles.get(2), player);
        game.notifyOfCommand(catkcmd);

        chcmd = new CTRLHealCommand();
        chcmd.configure(s0, _tiles.get(2));
        game.notifyOfCommand(chcmd);

        catkcmd = new CTRLAttackCommand();
        catkcmd.configure(s1, _tiles.get(2), player);
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
        _tiles.get(0).add(u0);
        _tiles.get(1).add(u1);
        _tiles.get(11).add(u2);

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
        _tiles.get(4).add(u0);
        _tiles.get(9).add(u1);
        _tiles.get(10).add(u2);

        CTRLCreateArmyCommand cac = new CTRLCreateArmyCommand();
        cac.configure(_tiles.get(14), u0, u1, u2);
        game.notifyOfCommand(cac);

        CTRLPowerUpCommand cpupcmd = new CTRLPowerUpCommand();
        cpupcmd.configure(u0);
        game.notifyOfCommand(cpupcmd);

        changeTurn(4);
    }

    private void testIterator() throws InterruptedException{
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

    private void testCommandIterator() throws InterruptedException{
        Unit u0 = um.addNewUnit("colonist");

        CTRLPowerUpCommand cmd = new CTRLPowerUpCommand();
        cmd.configure(u0);
        game.notifyOfCommand(cmd);

        CommandListVisitor v = new CommandListVisitor();
        u0.accept(v);
        CommandIterator iter = v.getIterator();
    }

    private void testPathfinding() throws InterruptedException{
        Unit u0 = um.addNewUnit("colonist");
        _tiles.get(203).add(u0);

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

    private void testLandMine() throws InterruptedException{
        Unit u0 = um.addNewUnit("colonist");
        Unit u1 = um.addNewUnit("colonist");
        _tiles.get(10).add(u0);
        _tiles.get(11).add(u1);

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
        _tiles.get(10).add(u0);
        _tiles.get(11).add(u1);

        changeTurn(1);

        CTRLCreateArmyCommand cac = new CTRLCreateArmyCommand();
        cac.configure(_tiles.get(99), u0, u1);
        game.notifyOfCommand(cac);

        Unit u2 = um.addNewUnit("colonist");
        _tiles.get(157).add(u2);

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
        _tiles.get(4).add(u0);
        _tiles.get(3).add(u1);

        CTRLCreateArmyCommand cac = new CTRLCreateArmyCommand();
        cac.configure(_tiles.get(7), u0, u1);
        game.notifyOfCommand(cac);

        changeTurn(3);

        CTRLBuildCommand cbc = new CTRLBuildCommand();
        cbc.configure(am.debugGetRallyPoint(), "fort", 0);
        game.notifyOfCommand(cbc);
        changeTurn(4);

        CTRLMoveRallyPointCommand cmr = new CTRLMoveRallyPointCommand();
        cmr.configure(am.debugGetRallyPoint(), _tiles.get(13));
        game.notifyOfCommand(cmr);
        changeTurn(4);
        System.out.print("sd");
    }
}

