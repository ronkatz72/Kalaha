package kalaha.game.model;

/* @Author: Ron Katz
 *  this structure is used in order to be able to look at the opposite 
 * pit of the other player, that way we can verify if its empty
 * in case a single stone was put in an empty pit at the last step
 * in such case all stones, from both pits are moved to KalahaPit of the current player*/

public class PitsPair extends Pit{

	private Pit oppositePit;
	
	public PitsPair(Player player, int stonesNumber){
		super(player, stonesNumber);
	}
	
	public Pit getPlayersPit(){
		return this; 
	}
	
	public void setOppositePit(Pit pit){
		oppositePit = pit;
	}
	
	public Pit getOppositePit(){
		return oppositePit;
	}

	public boolean isKalaha(){
		return false;
	}
}
