package kalaha.game.model;

/* @Author: Ron Katz
 * 
 * A pit holds 0 or more stones and belongs to a certain player
 *  Pit itself is an abstract class as it does not actually being created
 *  KalahaPit and PitsPair inherent from it
 * 
 */
public abstract class Pit {
	
	private final Player player;
	private int stonesNumber;
	
	public Pit(Player player, int stonesNumber) {
		this.player = player;
		this.stonesNumber = stonesNumber;
	}
	
	// this method helps to avoid costly instanceof call
	// when trying to find out a pit's role: Kalaha or regular pit
	abstract public boolean isKalaha();
	
	// getter and setters
	public int getStonesNum() {
		return stonesNumber;
	}
	
	public void setStonesNum(int num) {
		this.stonesNumber = num;
	}
	
	public Player getPlayer() {
		return player;
	}

	// remove all stones
	// in use when stone is places in an empty opposite pit
	// by the opponent (i.e. not the player that this pit belongs to)
	public int takeStones() {
		int val = stonesNumber;
		stonesNumber = 0;
		return val;
	}

	public void addStone() {
		stonesNumber++;
	}

	public boolean isEmpty() {
		return stonesNumber == 0;
	}

	@Override
	public String toString() {
		return "Pit [player=" + player + ", stonesNumber=" + stonesNumber + 
					 ", kalaha=" + isKalaha()+ "]";
	}
	
	
}
