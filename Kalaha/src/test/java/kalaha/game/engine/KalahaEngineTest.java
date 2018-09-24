package kalaha.game.engine;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import kalaha.game.engine.KalahaEngine;
import kalaha.game.model.KalahaBoard;
import kalaha.game.model.Pit;

/* @Author: Ron Katz
 * 
 */

public class KalahaEngineTest {
	
	KalahaBoard board;
	KalahaEngine engine;
	
	@Before
	public void preperObjects(){
		engine = new KalahaEngine();
	}
	
	/* Board according to test:
	 * 
	 * 	 1 1 1
	 * 0       0 --> select pit no 1 (zero index) -->
	 *   1 1 1 
	 *   
	 * 
	 * 	 2 0 1
	 * 0       0 --> select pit no 4 (zero index) -->
	 *   1 1 1   
	 *   
	 *   2 0 1
	 * 0       0 --> select pit no 0 (zero index) -->
	 *   0 2 1  
	 *   
	 *   This should cause player1's Klaha to contain 3 stones
	 *   board configuration after the move:
	 *   
	 *   2 0 0
	 * 3       0
	 *   0 0 1  
	 */
	
	@Test
	public void testEmptyPitSowing() {

		board = new KalahaBoard(3, 1);
		
		engine.play(board, 1);
		engine.play(board, 4);
		engine.play(board, 0);
		
		List<Pit> pitsList = board.getPits();
		assertEquals("pit0", 0, pitsList.get(0).getStonesNum());
		assertEquals("pit1", 0, pitsList.get(1).getStonesNum());
		assertEquals("pit2", 2, pitsList.get(2).getStonesNum());
		assertEquals("pit3", 3, pitsList.get(3).getStonesNum());
		assertEquals("pit4", 0, pitsList.get(4).getStonesNum());
		assertEquals("pit5", 0, pitsList.get(5).getStonesNum());
		assertEquals("pit6", 1, pitsList.get(6).getStonesNum());
		assertEquals("pit7", 0, pitsList.get(7).getStonesNum());
		
		assertEquals("next player", board.getPlayer2(), board.getCurrentPlayer());
	}
	
	
	/*
	 * Since pit 4 belongs to Player2 and first move is of Player1
	 * trying engine with pit 4 should not change the board state
	 * 
	 * 	 1 1 1
	 * 0       0 
	 *   1 1 1 
	*/
	@Test
	public void testTryWrongPit(){
		
		board = new KalahaBoard(3, 1);
		
		engine.play(board, 4);
		
		List<Pit> pitsList = board.getPits();
		assertEquals("pit0", 1, pitsList.get(0).getStonesNum());
		assertEquals("pit1", 1, pitsList.get(1).getStonesNum());
		assertEquals("pit2", 1, pitsList.get(2).getStonesNum());
		assertEquals("pit3", 0, pitsList.get(3).getStonesNum());
		assertEquals("pit4", 1, pitsList.get(4).getStonesNum());
		assertEquals("pit5", 1, pitsList.get(5).getStonesNum());
		assertEquals("pit6", 1, pitsList.get(6).getStonesNum());
		assertEquals("pit7", 0, pitsList.get(7).getStonesNum());
		
		assertEquals("next player", board.getPlayer1(), board.getCurrentPlayer());
	}
	
	
	/*
	 * Selecting pit0 (right upper corner) when initiate each pit with 7 stones
	 * will cause a full sowing cycle
	 * Kalaha pit of player2 (most right pit) should left empty since play1 skips it
	 * Kalaha pit of player1 should contains 10! that is since the last sow is right
	 * in pit0 which is now empty, so player1 takes the last stone and the 8 stones
	 * from the opposite pit of player2 and puts it in his kalaha
	 * together with the 1 ston already sowed there, now there should be 10.
	 * pit0 and the opposite pit should contain now 0 stones (the opposite in this case is pit6)
	 * all other pits should contain 8
	 * 
	 * 	 7 7 7						   8 8 0 	 
	 * 0       0  selecting pit0 --> 10      0
	 *   7 7 7 						   8 8 0 
	 *   
	 *   last stone sowed not in a kalaha so turn belongs now to player2
	*/
	@Test
	public void testFullCyclePlus(){
		
		board = new KalahaBoard(3, 7);
		
		engine.play(board, 0);
		
		List<Pit> pitsList = board.getPits();
		assertEquals("pit0",  0  , pitsList.get(0).getStonesNum());
		assertEquals("pit1",  8  , pitsList.get(1).getStonesNum());
		assertEquals("pit2",  8  , pitsList.get(2).getStonesNum());
		assertEquals("pit3", 10  , pitsList.get(3).getStonesNum()); // player1's Kalaha
		assertEquals("pit4",  8  , pitsList.get(4).getStonesNum());
		assertEquals("pit5",  8  , pitsList.get(5).getStonesNum());
		assertEquals("pit6",  0  , pitsList.get(6).getStonesNum());
		assertEquals("pit7",  0  , pitsList.get(7).getStonesNum()); // players2's Kalaha
		
		assertEquals("next player", board.getPlayer2(), board.getCurrentPlayer());
	}
	
	
	/* game steps:
	 * 
	 * 	 1 1 1            0 1 1            0 0 1          0 0 1           0 0 0 
	 * 0       0  (2)-> 1       0  (3)-> 3      0  (6)-> 3     1  (5)-> 3       3
 	 *   1 1 1            1 1 1            0 1 1          0 1 0           0 0 0
	 */ 
	
	@Test
	public void testFullGame(){
		board = new KalahaBoard(3,1);
		
		assertEquals("endOfGame", false, board.isGameOver());
		engine.play(board, 2); // player1
		assertEquals("endOfGame", false, board.isGameOver());
		engine.play(board, 1); // player1
		assertEquals("endOfGame", false, board.isGameOver());
		engine.play(board, 6); // player2
		assertEquals("endOfGame", false, board.isGameOver());
		engine.play(board, 5); // player2 // end of game, draw 3 : 3 
		assertEquals("endOfGame", true, board.isGameOver());
		assertEquals("winner", null, board.getWinner());
		
		List<Pit> pitsList = board.getPits();
		
		assertEquals("pit0",  0  , pitsList.get(0).getStonesNum());
		assertEquals("pit1",  0  , pitsList.get(1).getStonesNum());
		assertEquals("pit2",  0  , pitsList.get(2).getStonesNum());
		assertEquals("pit3",  3  , pitsList.get(3).getStonesNum()); // player1's Kalaha
		assertEquals("pit4",  0  , pitsList.get(4).getStonesNum());
		assertEquals("pit5",  0  , pitsList.get(5).getStonesNum());
		assertEquals("pit6",  0  , pitsList.get(6).getStonesNum());
		assertEquals("pit7",  3  , pitsList.get(7).getStonesNum()); // players2's Kalaha

 	}
	
	@Test
	public void testManipulateBoardEndOfGame(){
		
		board = new KalahaBoard(3,0);
		List<Pit> pits = board.getPits();
		// sets player1 left most pit to 1
		pits.get(2).setStonesNum(1);
		// sets player1 kalaha to 5
		pits.get(3).setStonesNum(5);
		// sets player2 second pit to 10
		pits.get(5).setStonesNum(10);
		
		List<Pit> pitsList = board.getPits();
		// test board content after manipulation
		assertEquals("pit0",   0  , pitsList.get(0).getStonesNum());
		assertEquals("pit1",   0  , pitsList.get(1).getStonesNum());
		assertEquals("pit2",   1  , pitsList.get(2).getStonesNum());
		assertEquals("pit3",   5  , pitsList.get(3).getStonesNum()); // player1's Kalaha
		assertEquals("pit4",   0  , pitsList.get(4).getStonesNum());
		assertEquals("pit5",  10  , pitsList.get(5).getStonesNum());
		assertEquals("pit6",   0  , pitsList.get(6).getStonesNum());
		assertEquals("pit7",   0  , pitsList.get(7).getStonesNum()); // players2's Kalaha
		
		// play for player1 pit2 --> end of game
		// in player1's kalaha there are now 6 stones
		// in player2's kalaha none (board was initiate with 0)
		// however, when game is finish, the stones in the pit of each player
		// are added to the player's kalaha so in player2's kalaha
		// there should be after game over 10 stones so player2 should win (6 : 10)
		engine.play(board, 2);
		assertEquals("endOfGame", true, board.isGameOver());
		assertEquals("winner", board.getPlayer2(), board.getWinner());
	}
	
}
