package kalaha.game.engine;

import kalaha.game.model.KalahaBoard;
import kalaha.game.model.Pit;
import kalaha.game.model.PitsPair;
import kalaha.game.model.Player;

 /* @Author: Ron Katz
  * 
  * Kalaha Engine
  * 
  * The engine of the does not hold a state
  * it receive a board and a pit number and calculate the new board state
  * if needed, it added messages to the board
  * Because it doesn't hold a state, it can be easily support multi games
  * the play method should be synchronized in such case
  * We could hold a pull of engines and each time a board needs to calculate its new state
  * assign it to a free engine.
  *
  */

public class KalahaEngine {

	// change board (and players) state according selected pit
	public void play(KalahaBoard board, int pitNumber) {
		 
		// fetchPit return the relevant pit or null if the action is illegal,
		// in case action is illegal, fetchPit will set a proper message for the user
		Pit pit = fetchPit(board, pitNumber);
		
		// action is not legal
		if (pit == null){
			return;
		}
		
		int pitIndex = pitNumber;
		int stones = pit.takeStones();
		boolean changePlayer = false;
			
		// sow all the stons from the selected pit		
		for (int stonesLeftToSow = stones; stonesLeftToSow > 0; stonesLeftToSow--) {
			// one step forward and take modulo by number of pits on board
			pitIndex++;
			
			// real index of next pit
			int nextPitIndex = pitIndex % (board.getNumberOfPits());
			
			pit = board.getPits().get(nextPitIndex);
			
			
			if (!pit.isKalaha()){
				// if its not a Kalaha then its a pitsPair (thats how the board is built)
				
				if (stonesLeftToSow == 1 && pit.isEmpty() && pit.getPlayer().equals(board.getCurrentPlayer())) {
					
					// in this case we take both sides of the pit and add it to the player's Kalaha
					
					int oppositeNumber = ((PitsPair) pit).getOppositePit().takeStones();
					
					board.setMessageText(board.getCurrentPlayer() + " took " + oppositeNumber + " from the other player");
					board.getCurrentPlayer().getKalahaPit().addStones(1 + oppositeNumber);
					changePlayer = true;
				} else {
					pit.addStone();
					if (!pit.isKalaha())
						changePlayer = true;
				}
			} else if (pit.getPlayer().equals(board.getCurrentPlayer())){
				// pit is a Kalaha - check if belongs to this user or skip
				pit.addStone();
				if (stonesLeftToSow == 1){
					// last pit is a kalaha - another turn for that user
					changePlayer = false;
					board.setMessageText(board.getCurrentPlayer().getName() + " gets another turn");
				} else{
					changePlayer = true;
				}
			} else {
				// that is the other player's kalaha
				stonesLeftToSow++; // skip - so didn't put stone
			}
		}
		if (changePlayer){
			board.forwardTurn();
		}
		
		if ((board.getPlayer1().countStones() == 0) || (board.getPlayer2().countStones() == 0)){
			
			// end of game, pick any stones left in the pits and add them to each 
			// players Kalaha, find the winner notify
			endOfGameBoardClean(board);
			board.gameOver(findWinner(board));
		}
	}
	
	private Pit fetchPit(KalahaBoard board, int pitNumber){
		
		Pit pit = null;
		
		if (board.isGameOver()){
			board.setMessageText("Game is over - please start a new game");
			return null;
		} 

		// modulo just in case although when playing data is accepted by the controller
		// and the controller will not pass illegal pit number
		pit = board.getPits().get(pitNumber % (board.getNumberOfPits()));	
		
		if (!pit.getPlayer().equals(board.getCurrentPlayer())) {
			board.setMessageText("The selected pit does not belong to the current player");
			return null;
		}
		
		if (pit.isEmpty()){
			board.setMessageText("The selected pit is Empty, try another one");
			return null;
		}
		
		return pit;
	}
	
	
	private void endOfGameBoardClean(KalahaBoard board){
		cleanBoard(board.getPlayer1());
		cleanBoard(board.getPlayer2());
	}
	
	private void cleanBoard(Player player){
		for (Pit pit : player.getPitsPair()){
			player.getKalahaPit().addStones(pit.takeStones());
		}
	}
	
	// returns the winner player or null if its a draw
	private Player findWinner(KalahaBoard board){
		
		Player player1 = board.getPlayer1();
		Player player2 = board.getPlayer2();
		
		int player1KalahaVal = player1.getKalahaPit().getStonesNum();
		int player2KalahaVal = player2.getKalahaPit().getStonesNum();
		
		Player winner = null;
		
		if (player1KalahaVal > player2KalahaVal){
			winner = player1;
		} else if (player1KalahaVal < player2KalahaVal){
			winner = player2;
		}

		return winner;
	}

	
}
