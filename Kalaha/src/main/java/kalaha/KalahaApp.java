package kalaha;

/* @Author: Ron Katz
 * 
 * created on 2018/9/14
 * 
 * This is the main class of my Kalaha game app solution
 * 
 * The solution is using spring-boot framework
 * and uses spring MVC
 * The model consists of the game elements:
 * Board, Player and abstract Pit which is extended by PitsPair and Kalaha Pair.
 * The board is holding players and pits, the players holds pitsPairs and a Kalaha pair each
 * 
 * when receiving a move from the GUI the controller send notification to the
 * engine through the the KalahaService with the board and the selected Pit
 * The engine compute the new board state and update the board and if the game is ended
 * it announce the user about the result.
 * 
 * A few unit tests were added to verify that functionality is correct.
 * 
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KalahaApp {

	public static void main(String[] args) {
		SpringApplication.run(KalahaApp.class, args);

	}

}
