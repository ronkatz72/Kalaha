package kalaha.game.services;

import java.util.List;

/* @Author: Ron Katz
 * 
 */

public interface IKalahaService {
	public List<Integer> startGame();
	public List<Integer> play(int pitNumber);
	public String getMessage();
}
