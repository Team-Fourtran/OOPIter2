package models.playerAsset.Iterators;

import models.ctrlCommand.CTRLCommand;

import java.util.ArrayList;

public class CommandIterator implements Iterator{
//The current ArrayList of CTRLCommand objects that we iterate through when we call .next() or .previous()
    private ArrayList<CTRLCommand> cmdList;

    //The current CTRLCommand as selected by calls to .next() or .previous()
    CTRLCommand curCmd;
    private int cmdIdx;

    //Initialized with an ArrayList of ArrayList<CtrlCommand> objects.
    public CommandIterator(ArrayList<CTRLCommand> c){
        this.cmdList = c;
        this.first();
    }

    @Override
    //Resets the current command to the first one in the current list
    public CTRLCommand first() {
        this.cmdIdx = 0;
        curCmd = cmdList.get(cmdIdx);
        return curCmd;
    }

    @Override
    //Increment the current command index (circularly) and update the current command
    public void next() {
        cmdIdx = (cmdIdx+1) % cmdList.size();    //Increment cmdIdx modularly to the size of the list
        curCmd = cmdList.get(cmdIdx);
    }

    @Override
    //Decrement the current command index (circularly) and update the current command
    public void prev() {
        //Decrement cmdIdx modularly to the size of the list. Can't use mod (%) here, so use a ternary operator.
        cmdIdx = (cmdIdx == 0)? cmdList.size()-1: (cmdIdx-1);
        curCmd = cmdList.get(cmdIdx);
    }

    @Override
    //Return the currently selected Command
    public CTRLCommand current() {
        return this.curCmd;
    }
}

