/**
 * 
 */
package ca.concordia.app.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Events which helps trigger the Observer Pattern
 * @author harvi
 *
 */
public class GamePlayEvent {

	/**
	 * an enum for the different event types
	 *
	 */
	public enum EventType{
		START_ARMY_ALLOCATION,
		START_COUNTRY,
		START_ARMY,
		REINFORCE_ARMY_ALLOCATION,
		REFINFORCE_COUNTRY,
		ATTACK_COUNTRY,
		ATTACK_CAPTURE,
		FORTIFY_COUNTRY,
		CARD_WIN,
		CARD_EXCHANGE,
		GENERIC_UPDATE,
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
	 * 
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
	 * 
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
