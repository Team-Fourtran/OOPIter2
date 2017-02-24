package src.models.playerAsset;

/**
 * Created by Clay on 2/22/2017.
 */
public class Structure extends PlayerAsset{

    int productionRate;     //turns it takes to create a unit

    public Structure(){

        offDamage = 75;
        defDamage = 75;
        armor = 150;
        productionRate = 2;
        maxHealth = currentHealth = 200;
        upkeep = 1;

    }

    //execute next command in queue
    //if multi-turn command, wait appropriate amount of turns
    //trimmed version of PlayerAsset implementation
    public void executeCommand(){

        if (!hasExecutedCommand) {
            commandCount++;
            if (equal(commandQueue.peek().getTurns(), commandCount)) {
                commandQueue.peek().execute();
                commandQueue.remove();
                hasExecutedCommand = true;
                commandCount = 0;
            }
        }

    }
}
