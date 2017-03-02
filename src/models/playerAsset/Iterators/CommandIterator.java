package models.playerAsset.Iterators;

import models.ctrlCommand.CTRLCommand;

import java.util.ArrayList;

public class CommandIterator implements Iterator{
    //An ArrayList of ArrayLists. Each one contains a list of commands of a category.
    private ArrayList<ArrayList<CTRLCommand>> listList;

    //The current ArrayList of CTRLCommand objects that we iterate through when we call .next() or .previous()
    private ArrayList<CTRLCommand> currentList;
    private int currentListIndex;

    //The current CTRLCommand as selected by calls to .next() or .previous()
    CTRLCommand curCmd;
    private int curCmdIndex;

    //Initialized with an ArrayList of ArrayList<CtrlCommand> objects.
    public CommandIterator(ArrayList<ArrayList<CTRLCommand>> c){
        this.listList = c;
        this.currentListIndex = 0;
        this.currentList = listList.get(currentListIndex);
        this.first();
    }

    //Switch to the next ArrayList of commands.
    public void nextType(){
        currentListIndex = (currentListIndex+1)%listList.size();    //Increment listIndex modularly
        currentList = listList.get(currentListIndex);               //Set the current list to the list in that index
        first();                                                    //Reset the command index
    }

    //Switch to the previous ArrayList of commands.
    public void prevType(){
        //Decrement listIndex modularly
        currentListIndex = (currentListIndex == 0)? listList.size()-1: (currentListIndex-1);
        //Set the current list to the list in that index
        currentList = listList.get(currentListIndex);
        //Reset the current command and index to the first in the current list
        first();
    }

    @Override
    //Resets the current command to the first one in the current list
    public CTRLCommand first() {
        this.curCmdIndex = 0;
        curCmd = currentList.get(curCmdIndex);
        return curCmd;
    }

    @Override
    //Increment the current command index (circularly) and update the current command
    public void next() {
        curCmdIndex = (curCmdIndex+1) % currentList.size();    //Increment curCmdIndex modularly to the size of the list
        curCmd = currentList.get(curCmdIndex);
    }

    @Override
    //Decrement the current command index (circularly) and update the current command
    public void prev() {
        //Decrement curCmdIndex modularly to the size of the list. Can't use mod (%) here, so use a ternary operator.
        curCmdIndex = (curCmdIndex == 0)? currentList.size()-1: (curCmdIndex-1);
        curCmd = currentList.get(curCmdIndex);
    }

    @Override
    //Return the currently selected Command
    public CTRLCommand current() {
        return this.curCmd;
    }
}
