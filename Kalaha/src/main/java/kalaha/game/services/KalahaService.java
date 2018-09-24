package kalaha.game.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kalaha.game.engine.KalahaEngine;
import kalaha.game.model.KalahaBoard;
import kalaha.game.model.Pit;

/* @Author: Ron Katz
 * 
 */

@Service
public class KalahaService implements IKalahaService{

	private KalahaBoard board;
	private KalahaEngine engine = new KalahaEngine();
	
	public List<Integer> startGame(){
		board = new KalahaBoard(6, 6);
		return processPitsList(board.getPits());	
	}

	/***
	 * engine rearrange board according to selected pit
	 * returns: the state of the pits so GUI will be able to show update
	 */
	
	@Override
	public List<Integer> play(int pitNumber) {
		// 
		engine.play(board, pitNumber);
		return processPitsList(board.getPits());
	}
	
	public String getMessage(){
		return board.getMessageText();
	}
	
	private List<Integer> processPitsList(List<Pit> pitsList){
		List<Integer> list = new ArrayList<>();

		for (Pit pit : board.getPits()){
			list.add(pit.getStonesNum());
		}
		
		return list;
	}
}
