package models.playerAsset.Assets;


import models.playerAsset.Iterators.*;
import models.ctrlCommand.*;
import models.visitor.PlayerVisitor;

import java.util.ArrayList;

public class Player {
    //public for debugging
    private final ArmyManager armies;
    private final UnitManager units;
    private final StructureManager structures;

    public Player(){
        armies = new ArmyManager();
        units = new UnitManager();
        structures = new StructureManager();
    }

    //method to do maintenance tasks on player's assets
    public void beginTurn(){
        armies.executeCommands();
        structures.executeCommands();
        units.executeCommands();
    }

    //do end of turn housekeeping like resetting commands
    public void endTurn(){
        armies.resetCommands();
        structures.resetCommands();
        units.resetCommands();
    }

    public void accept(PlayerVisitor v){
        armies.accept(v);
        units.accept(v);
        structures.accept(v);
    }

    private AssetIterator<PlayerAsset, TypeIterator<PlayerAsset, Iterator<PlayerAsset>>> makeAssetIterator(ArrayList<TypeIterator<PlayerAsset, Iterator<PlayerAsset>>> list) {
        return new AssetIterator<PlayerAsset, TypeIterator<PlayerAsset, Iterator<PlayerAsset>>>(){

            private int index = 0;
            private TypeIterator<PlayerAsset, Iterator<PlayerAsset>> current = current();

            public TypeIterator<PlayerAsset, Iterator<PlayerAsset>> first() {
                return list.get(0);
            }

            public void nextMode(){
                index = (index + 1) % list.size();
                current = list.get(index);
            }

            public void prevMode(){
                if (index != 0)
                    index--;
                else
                    index = list.size() - 1;
                current = list.get(index);
            }

            public void nextType() {
                current.nextType();
            }

            public void prevType() {
                current.prevType();
            }

            public void next() {
                current.next();
            }

            public void prev() {
                current.prev();
            }

            public TypeIterator<PlayerAsset, Iterator<PlayerAsset>> current() {
                return list.get(index);
            }


            public PlayerAsset getElement(){
                return current().getElement();
            }
        };
    }

    public UnitManager getUnits() {
        return units;
    }

    public ArmyManager getArmies() {
        return armies;
    }

    public StructureManager getStructures() {
        return structures;
    }

    public AssetIterator<PlayerAsset, TypeIterator<PlayerAsset, Iterator<PlayerAsset>>> getAssetIterator(){
        ArrayList<TypeIterator<PlayerAsset, Iterator<PlayerAsset>>> list = new ArrayList<>();
        list.add(units.getTypeIterator());
        list.add(structures.getTypeIterator());
        //list.add(armies.getTypeIterator());
        return makeAssetIterator(list);
    }

//    public CommandIterator makeCommandIterator(){
//        /* Create 4 ArrayLists of CTRLCommand objects, one list per type of command (unit, army, &c) */
//        ArrayList<CTRLCommand> unitCmds = new ArrayList<>();
//        ArrayList<CTRLCommand> armyCmds = new ArrayList<>();
//        ArrayList<CTRLCommand> rallyPointCmds = new ArrayList<>();
//        ArrayList<CTRLCommand> structureCmds = new ArrayList<>();
//
//        /* Populate each list with blank versions of each of the CTRLCommands that fall under it */
//
//                //CTRLAttackCommand: This can be either an Army command or a Structure command.
//                armyCmds.add(new CTRLAttackCommand());
//                structureCmds.add(new CTRLAttackCommand());
//
//                //CTRLCancelQueuedOrders: This can be an Army command or a Structure command
//                armyCmds.add(new CTRLCancelQueuedOrders());
//                structureCmds.add(new CTRLCancelQueuedOrders());
//
//                //CTRLCreateArmyCommand: This doesn't actually belong here?
//                //TODO: Figure out how to handle CreateArmyCommand
//
//                //CTRLCreateCapitalCommand: I guess this is a Unit command, since only Colonists can do it?
//                unitCmds.add(new CTRLCreateCapitalCommand());
//
//                //CTRLDecommissionCommand: This can be a Unit, Army, or Stucture command
//                unitCmds.add(new CTRLDecommissionCommand());
//                armyCmds.add(new CTRLDecommissionCommand());
//                structureCmds.add(new CTRLDecommissionCommand());
//
//                //CTRLHealCommand: This is a Structure command
//                structureCmds.add(new CTRLHealCommand());
//
//                //CTRLMoveRallyPointCommand: This is an Army command. Unintuitively, it is NOT a RP Command.
//                armyCmds.add(new CTRLMoveRallyPointCommand());
//
//                //CTRLPowerDownCommand: This can be a Unit, Army, or Structure command
//                unitCmds.add(new CTRLPowerDownCommand());
//                armyCmds.add(new CTRLPowerDownCommand());
//                structureCmds.add(new CTRLPowerDownCommand());
//
//                //CTRLPowerUpCommand: This can be a Unit, Army, or Structure command
//                unitCmds.add(new CTRLPowerUpCommand());
//                armyCmds.add(new CTRLPowerUpCommand());
//                structureCmds.add(new CTRLPowerUpCommand());
//
//                //CTRLReinforceArmyCommand: This is a Unit command
//                unitCmds.add(new CTRLReinforceArmyCommand());
//
//        /* Create an ArrayList to hold all of the ArrayLists */
//        ArrayList<ArrayList<CTRLCommand>> listList = new ArrayList<>();
//        listList.add(unitCmds);
//        listList.add(armyCmds);
//        listList.add(rallyPointCmds);
//        listList.add(structureCmds);
//
//        /* Create a CommandIterator using the meta-List we just created */
//        return new CommandIterator(listList);
//    }

}