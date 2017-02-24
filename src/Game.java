package application;

import application.models.commands.CommandGenerator;
import application.models.playerAsset.*;
import application.models.tileState.*;
import application.models.utility.*;
import application.views.*;

import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

	private Player[] players;
	private Map map;
	private Player currentPlayer;
	private final int ROW = 15;
	private final int COL = 15;
	private TileGen T;
	private MainScreen mainScreen;
	
	public Game(){
		
		// Create two players and generate map
		players = new Player[2];
		players[0] = new Player();
		players[1] = new Player();
		currentPlayer = players[0];
		T = new TileGen(ROW, COL);
		map = new Map(T.execute(), ROW, COL);


		// Generate a colonist and 2 explorer units for the two players
		CommandGenerator cGen0 = new CommandGenerator(players[0], map);
        
//		CommandGenerator cGen1 = new CommandGenerator(players[0], map);
//		cGen1.generateCommand("IU_T100_colonist");
//		cGen1.generateCommand("IU_T115_explorer");
//		cGen1.generateCommand("IU_T130_explorer");


		for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                System.out.println(i + " " + j);
                System.out.println(map.getTiles().get(("T"+ String.valueOf((j*15) + i))).getProperties());
            }
        }

        ListIterator unitIterator = currentPlayer.getUnitIterator();
		ListIterator structureIterator = currentPlayer.getStructureIterator();
		ListIterator armyIterator = currentPlayer.getArmyIterator();

		mainScreen = new MainScreen();
		mainScreen.initialize();
		mainScreen.generateMainScreen();
		mainScreen.showMainScreen();

    }

	
	// Change the active player to the other one
	/*public void switchPlayers(){
		currentPlayer = (currentPlayer == players[0]) ? players[1] : players[0];
	}*/
}
