package models.playerAsset.Iterators;

import models.ctrlCommand.CTRLCommand;

import java.util.ArrayList;

public class CommandIterator implements Iterator{
    private ArrayList<CTRLCommand> cmdList;
    CTRLCommand curCmd;
    private int idx;

    public CommandIterator(ArrayList<CTRLCommand> c){
        this.cmdList = c;
        this.first();
    }

    @Override
    public CTRLCommand first() {
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
    public CTRLCommand current() {
        return this.curCmd;
    }
}
