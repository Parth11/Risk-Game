/**
 * 
 */
package ca.concordia.app.model;

import java.util.HashMap;

/**
 * The class GamePlay Event has Events which helps trigger the Observer Pattern
 * @author harvi
 *
 */
public class GamePlayEvent {

	/**
	 * Enum GameMode defines the collection of constant
	 */
	public enum GameMode{
		SINGLE_GAME("Single Game"),
		TOURNAMENT("Tournament");
		
		private String mode;       

	    private GameMode(String s) {
	    	mode = s;
	    }
	    /**
	     * Compares the other name
	     * @param otherName
	     * @return mode which equal to the other name 
	     */
	    public boolean equalsName(String otherName) {
	        return mode.equals(otherName);
	    }
	    
	    /**
	     * other name in the string
	     * @return mode
	     */
	    public String toString() {
	       return this.mode;
	    }
	}
	/**
	 * an enum for the different event types
	 *
	 */
	public enum EventType{
		START_ARMY_ALLOCATION,
		START_COUNTRY,
		START_ARMY_COUNTRY,
		REINFORCE_ARMY_ALLOCATION,
		REFINFORCE_COUNTRY,
		ATTACK_COUNTRY,
		ATTACK_CAPTURE,
		FORTIFY_COUNTRY,
		CARD_WIN,
		CARD_EXCHANGE,
		GENERIC_UPDATE,
		PLAYER_DEAD,
		THE_END;
	}
	
	/**
	 * Parameterized constructor
	 * @param eventType
	 * @param eventPayload
	 */
	public GamePlayEvent(EventType eventType, HashMap<String,Object> eventPayload){
		this.event_type = eventType;
		this.event_payload = eventPayload;
	}
	
	private EventType event_type;
	
	private HashMap<String,Object> event_payload;

	/**
	 * gets the event type
	 * @return the event type
	 */
	public EventType getEvent_type() {
		return event_type;
	}

	/**
	 * Sets the event type
	 * @param event_type
	 */
	public void setEvent_type(EventType event_type) {
		this.event_type = event_type;
	}

	/**
	 * hashmap for the get event payload
	 * @return the event_payload
	 */
	public HashMap<String, Object> getEvent_payload() {
		return event_payload;
	}
	
	/**
	 * Sets the event load 
	 * @param event_payload
	 */
	public void setEvent_payload(HashMap<String, Object> event_payload) {
		this.event_payload = event_payload;
	}
	
}
