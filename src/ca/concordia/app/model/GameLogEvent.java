package ca.concordia.app.model;

public class GameLogEvent {
	
	private Player player;
	
	private GamePlayEvent game_event;
	

	public GameLogEvent(Player player, GamePlayEvent game_event) {
		this.player = player;
		this.game_event = game_event;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public GamePlayEvent getGame_event() {
		return game_event;
	}

	public void setGame_event(GamePlayEvent game_event) {
		this.game_event = game_event;
	}

}
