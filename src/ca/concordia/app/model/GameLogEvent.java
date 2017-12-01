package ca.concordia.app.model;

/**
 * This class will log the game event
 * @author Harvi
 *
 */
public class GameLogEvent {
	
	private Player player;
	
	private GamePlayEvent game_event;
	
	/**
	 * Constructor of class GameLogEvent
	 * @param player
	 * @param game_event
	 */
	public GameLogEvent(Player player, GamePlayEvent game_event) {
		this.player = player;
		this.game_event = game_event;
	}
	
	/**
	 * get the player
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * set the player
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * gets the game event 
	 * @return game_event
	 */
	public GamePlayEvent getGame_event() {
		return game_event;
	}
	
	/**
	 * set the game event for play
	 * @param game_event
	 */
	public void setGame_event(GamePlayEvent game_event) {
		this.game_event = game_event;
	}

}
