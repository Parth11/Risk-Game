/**
 * 
 */
package ca.concordia.app.model;

import java.io.Serializable;

import com.google.gson.Gson;

import ca.concordia.app.service.GamePlayService;

/**
 * @author harvi
 *
 */
public class SavedGame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8451620048455126517L;

	public GameMap game_map;

	public GamePlayService game_play_service;
	
	public void saveThisGame(){
		Gson gson = new Gson();
		gson.toJson(GamePlayService.getInstance());
		gson.toJson(this);
	}
	
}
