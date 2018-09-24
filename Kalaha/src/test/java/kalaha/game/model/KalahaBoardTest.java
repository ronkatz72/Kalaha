package kalaha.game.model;

import java.util.List;

import org.junit.Test;

import kalaha.game.model.KalahaBoard;
import kalaha.game.model.Pit;

import static org.junit.Assert.assertEquals;

/* @Author: Ron Katz
 * 
 */

public class KalahaBoardTest {
	
	@Test
	public void buildBoard5ON5TotalPitsTest() {
		KalahaBoard board = new KalahaBoard(5, 4);
		List<Pit> pitsList = board.getPits();
		assertEquals("Total pits", 12, pitsList.size());	
	}
	
	@Test
	public void buildBoard5ON5KalahaPitsTest() {
		KalahaBoard board = new KalahaBoard(5, 4);
		int kalahaPitsCount = 0;
		for (Pit pit : board.getPits()){
			if (pit.isKalaha()){
				kalahaPitsCount++;
			}
		}
		assertEquals("Total Kalaha pits", 2, kalahaPitsCount);	
	}
	
	@Test
	public void buildBoard5ON5InitCountTest() {
		KalahaBoard board = new KalahaBoard(5, 4);
		int stonesCount = 0;
		for (Pit pit : board.getPits()){
			stonesCount += pit.getStonesNum();
		}
		assertEquals("Total stones number", 40, stonesCount);
	}
	
	@Test
	public void buildBoard5ON5InitEmptyKalahaTest1() {
		KalahaBoard board = new KalahaBoard(5, 4);
		int stonesCount = 0;
		for (Pit pit : board.getPits()){
			if (pit.isKalaha()){
				stonesCount += pit.getStonesNum();
			}
		}
		assertEquals("Total Kalaha pits (direct count)", 0, stonesCount);
	}
	
	@Test
	public void buildBoard5ON5InitEmptyKalahaTest2() {
		KalahaBoard board = new KalahaBoard(5, 4);
		int stonesCount = 0;
		for (Pit pit : board.getPits()){
			if (pit.isKalaha() && !pit.isEmpty()){
				stonesCount += pit.getStonesNum();
			}
		}
		assertEquals("Total Kalaha pits (isEmpty method)", 0, stonesCount);
	}
	
	@Test
	public void buildBoard5on5locationCheck(){
		KalahaBoard board = new KalahaBoard(5, 4);		
		List<Pit> pitsList = board.getPits();
		// player1 Kalaha location
		Pit testedPit = pitsList.get(5);
		assertEquals("Player1 Kalaha", true, testedPit.isKalaha() && testedPit.getPlayer().equals(board.getPlayer1()));
		
		testedPit = pitsList.get(11);
		assertEquals("Player1 Kalaha", true, testedPit.isKalaha() && testedPit.getPlayer().equals(board.getPlayer2()));
		
		for (int i=0; i < 5; i++){
			testedPit = pitsList.get(i);
			assertEquals("Player1 Pit " + i, true, !testedPit.isKalaha() && testedPit.getPlayer().equals(board.getPlayer1()));
		}
		
		for (int i=6; i < 10; i++){
			testedPit = pitsList.get(i);
			assertEquals("Player2 Pit " + i, true, !testedPit.isKalaha() && testedPit.getPlayer().equals(board.getPlayer2()));
		}
	}
}
