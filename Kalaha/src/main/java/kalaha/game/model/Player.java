package kalaha.game.model;

import java.util.ArrayList;
import java.util.List;

/* @Author: Ron Katz
 * 
 */

public class Player {
	private final String name;
	private final List<PitsPair> pitsPairs = new ArrayList<>();
	private final KalahaPit kalahaPit;
	
	public Player(String name){
		this.name = name;
		kalahaPit = new KalahaPit(this);
	}
	
	/**
	 * Count of all stones in houses
	 */
	public int countStones() {
		
		int total = 0;
		
		for (PitsPair pair : pitsPairs){
			total += pair.getPlayersPit().getStonesNum();
		}
		
		return total;
	}

	public List<PitsPair> getPitsPair() {
		return pitsPairs;
	}

	public void setPitsPair(PitsPair pit){
		pitsPairs.add(pit);
	}
	
	public String getName() {
		return name;
	}

	public KalahaPit getKalahaPit() {
		return kalahaPit;
	}
	
	public String toString(){
		return name;
	}

}
