package kalaha.game.model;

/* @Author: Ron Katz
 * 
 *  a Kalaha pit is initialize with zero stones
 */

public class KalahaPit extends Pit {
	
	public KalahaPit(Player player) {
		super(player, 0);
	}
	
	// update new number of stones in case
	// a player puts a sead in an empty pit
	public void addStones(int newStones) {
		setStonesNum(getStonesNum() + newStones);
	}
	
	public boolean isKalaha(){
		return true;
	}
}
