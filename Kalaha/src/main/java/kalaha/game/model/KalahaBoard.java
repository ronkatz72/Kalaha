package kalaha.game.model;

import java.util.ArrayList;
import java.util.List;


/* @Author: Ron Katz
 * 
 * Board structure (pits number):
 * 
 *   n = number of pits per player
 *   each player has n-1 active pits and 1 kalaha
 *   indexes start at top upper corner with 0 
 *   and ended at player2's kalaha with 2n-1
 *   
 *   schema:
 *   
 * 		        <-- Player1
 * 	 	 n-1  n-2 ...      1     0
 * 	   n                              2n -1
 * 		  n+1 n+2  ...  2n - 3  2n - 2
 * 		        --> Player2
 *         
 *   each player has n pits, pits n and n2 are the kalahas of the players
 *
 */  

public class KalahaBoard {
	
	private final int pitsNumber;
	private final int stonesNumber;
	private final List<Pit> pits = new ArrayList<>();
	
	private Player currentPlayer;
	
	private Player player1;
	private Player player2;
	
	// if at the end of the game winner == null it means a Draw
	private Player winner = null;
	
	private String text;
	
	private boolean gameOver = false;
	
	private int numberOfPits = 1;
	
	public KalahaBoard(int pitsNumber, int stonesNumber) {
		this.pitsNumber = pitsNumber;
		this.stonesNumber = stonesNumber;
		init();
	}
	
	public void forwardTurn() {
		if (currentPlayer.equals(player1)) {
			currentPlayer = player2;
		} else {
			currentPlayer = player1;
		}
	}
	
	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}
	
	public List<Pit> getPits() {
		return pits;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setMessageText(String message) {
		text = message;
	}
	
	public String getMessageText(){
		StringBuilder builder = new StringBuilder();
		if (text != null && !text.isEmpty()){
			builder.append(text);
			builder.append("  |  ");
		}
		if (!isGameOver()){
			builder.append("Next turn: ");
			builder.append(getCurrentPlayer().getName());
		} else {
			builder.append("Press 'Start a new game' to play again!");
		}
		text = "";
		return builder.toString();
	}
	
	public void gameOver(Player winner){
		gameOver = true;
		setWinner(winner);
	}
	
	public boolean isGameOver(){
		return gameOver;
	}
	
	public int getNumberOfPits(){
		return numberOfPits;
	}
	
	public Player getWinner(){
		return winner;
	}
	
	private void setWinner(Player winner){
		this.winner = winner;
		
		if (winner == null){
			setMessageText("End of game! Its a Draw!" );
		} else if (player1.equals(winner)){
			setMessageText("End of game! Winner is: Player1" );
		} else if (player2.equals(winner)) {
			setMessageText("End of game! Winner is: Player2" );
		} else {
			// could not identify player... (should not happen)
			setMessageText("End of game");
		}
		
	}
	
	// init:
	// each player is assigned with pitsNumber of pits pairs
	// each pair contains the players pit and the opposite pit
	
	// the result is as follow (for pitsNumber = 6):
	// pits  0 - 6 belongs to player1 while pit6 is a KalahaPit
	// pits 7 - 13 belongs to player2 while pit13 is a KalahaPit
	
	private void init(){
		
		// clear List of pits from previous game
		pits.clear();
		
		player1 = new Player("Player1");
		player2 = new Player("Player2");
		
		currentPlayer = player1;
		
		// since player1 is being created first
		// it has only its own pit - no opposite yet
		// opposite will be set while player2 will be initiate
		for (int i = 0; i < pitsNumber; i++) {
			PitsPair newPit = new PitsPair(player1, stonesNumber);
			player1.setPitsPair(newPit);
			pits.add(newPit);
		}
		pits.add(player1.getKalahaPit());

		// while initialize player2 - add the opposite pit for both players
		for (int i = 0; i < pitsNumber; i++) {
			PitsPair newPit = new PitsPair(player2, stonesNumber);
			PitsPair player1Pair = player1.getPitsPair().get((pitsNumber - 1) - i);
			player1Pair.setOppositePit(newPit);
			newPit.setOppositePit(player1Pair);
			
			player2.setPitsPair(newPit);
			pits.add(newPit);
		}
		pits.add(player2.getKalahaPit());
		
		gameOver = false;
		numberOfPits = getPits().size();
	}
	
}
