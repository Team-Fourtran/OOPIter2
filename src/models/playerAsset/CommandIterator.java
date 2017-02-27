package models.playerAsset;

import models.command.Command;

import java.util.ArrayList;

public class CommandIterator implements Iterator{
    private ArrayList<Command> cmdList;
    Command curCmd;
    private int idx;

    public CommandIterator(ArrayList<Command> c){
        this.cmdList = c;
        this.first();
    }

    @Override
    public Command first() {
        this.idx = 0;
        curCmd = cmdList.get(idx);
        return curCmd;
    }

    @Override
    public void next() {
        this.curCmd = cmdList.get(++idx);
    }

    @Override
    public void prev() {
        this.curCmd = cmdList.get(--idx);
    }

    @Override
    public Command current() {
        return this.curCmd;
    }
}
