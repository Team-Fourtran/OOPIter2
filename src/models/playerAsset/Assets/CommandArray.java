package models.playerAsset.Assets;

import models.command.Command;

public class CommandArray {
    private Command[] commands;
    private int max;
    private int numberOfElements;

    CommandArray(){
        this.commands = new Command[1];
        this.numberOfElements = 0;
        this.max = 1;
    }

    public void add(Command c){
        if(numberOfElements == max){
            increaseSize();
        }
        commands[numberOfElements++] = c;
    }

    public void remove(Command c){
        for(int i = 0; i < numberOfElements; i++){
            if (commands[i] == c){
                commands[i] = null;
                return;
            }
        }
    }

    private void consolidate(){
        Command[] newCommands = new Command[max];
        int newIndex = 0;
        int nullCount = 0;
        for(int i = 0; i < numberOfElements; i++){
            if (commands[i] != null){
                newCommands[newIndex] = commands[i];
                newIndex++;
            }
            else{
                nullCount++;
            }
        }
        this.commands = newCommands;
        this.numberOfElements -= nullCount;
    }

    private void increaseSize(){
        this.max = max*2;
        Command[] newCommands = new Command[max];
        for(int i = 0; i < numberOfElements; i++){
            newCommands[i] = this.commands[i];
        }
        this.commands = newCommands;
    }

    public void execute(){
        for(int i = 0; i < numberOfElements; i++){
            commands[i].execute();
        }
        consolidate();
    }
}
