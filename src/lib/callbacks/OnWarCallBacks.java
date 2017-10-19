package lib.callbacks;

import lib.Game;
import lib.model.Country;
import lib.model.Player;

public abstract class OnWarCallBacks {
	private Game gameApi;
	private Player attackPlayer, defencePlayer;
	private Country fromCountry, toCountry;
	private int[] attackDiceResult, defenceDiceResult;

	private boolean canReWar = false;
	
	public OnWarCallBacks(Game gameApi, Player attackPlayer, Player defencePlayer, Country fromCountry, Country toCountry) {
		super();
		this.gameApi = gameApi;
		this.attackPlayer = attackPlayer;
		this.defencePlayer = defencePlayer;
		this.fromCountry = fromCountry;
		this.toCountry = toCountry;
	}
	
	public final Player getAttackPlayer() {
		return attackPlayer;
	}
	public final Player getDefencePlayer() {
		return defencePlayer;
	}
	public final Country getFromCountry() {
		return fromCountry;
	}
	public final Country getToCountry() {
		return toCountry;
	}
	public final int[] getAttackDiceResult() {
		return attackDiceResult;
	}
	public final int[] getDefenceDiceResult() {
		return defenceDiceResult;
	}
	public final void setAttackDiceResult(int[] attackDiceResult) {
		this.attackDiceResult = attackDiceResult;
	}
	public final void setDefenceDiceResult(int[] defenceDiceResult) {
		this.defenceDiceResult = defenceDiceResult;
	}
	public final void setCanReWar(boolean value) {
		this.canReWar = value;
	}
	
	public abstract void onWarStartFailure();
	public abstract void beforeWarStart();
	public abstract void onAttackerWin(int roundNumber, int attackDiceResult, int defenceDiceResult);
	public abstract void onDefenderWin(int roundNumber, int attackDiceResult, int defenceDiceResult);
	public abstract void onWarFinished();
	public abstract void onCountryCaptured();
	public abstract void askForReWar();
	
	public final void reWar() {
		if(!canReWar)
			onWarStartFailure();
		else
			gameApi.war(this);
	}
}