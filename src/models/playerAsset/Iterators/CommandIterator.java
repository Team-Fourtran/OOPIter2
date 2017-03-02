package models.playerAsset.Iterators;

import models.ctrlCommand.CTRLCommand;

import java.util.ArrayList;

public class CommandIterator implements Iterator{
    //An ArrayList of ArrayLists. Each one contains a list of commands of a category.
    private ArrayList<ArrayList<CTRLCommand>> listList;

    private ArrayList<CTRLCommand> currentList;
    private int currentListIndex;

    CTRLCommand curCmd;
    private int curCmdIndex;

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

    //Switch to the next ArrayList of commands.
    public void prevType(){
        currentListIndex = (currentListIndex == 0)? listList.size()-1: (currentListIndex-1);    //Decrement listIndex modularly
        currentList = listList.get(currentListIndex);               //Set the current list to the list in that index
        first();                                                    //Reset the command index
    }

    @Override
    //Resets the current command to the first one in the current list
    public CTRLCommand first() {
        this.curCmdIndex = 0;
        curCmd = currentList.get(curCmdIndex);
        return curCmd;
    }

    @Override
    public void next() {
        curCmdIndex = (curCmdIndex+1) % currentList.size();    //Increment curCmdIndex modularly
        curCmd = currentList.get(curCmdIndex);
    }

    @Override
    public void prev() {
        curCmdIndex = (curCmdIndex == 0)? currentList.size()-1: (curCmdIndex-1);    //Decrement curCmdIndex modularly
        curCmd = currentList.get(curCmdIndex);
    }

    @Override
    public CTRLCommand current() {
        return this.curCmd;
    }
}
