package kalaha.game.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kalaha.game.services.IKalahaService;

/* @Author: Ron Katz
 * 
 */

@RestController
public class KalahaController {

	@Autowired
	private IKalahaService service;

	
	@RequestMapping("/kalaha/play/{pitNumber}")
	public List<Integer> play(@PathVariable int pitNumber) {
		return service.play(pitNumber);
	}
	
	
	@RequestMapping("/startGame")
	public List<Integer> StartNewGame(){
		return service.startGame();
	}
	
	@RequestMapping("/getMessage")
	public String getMessage(){
		return service.getMessage();
	}
}
