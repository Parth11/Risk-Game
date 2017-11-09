/**
 * 
 */
package ca.concordia.app.model;

import java.util.HashMap;

/**
 * @author harvi
 *
 */
public class GamePlayEvent {

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
	
	public GamePlayEvent(EventType eventType, HashMap<String,Object> eventPayload){
		this.event_type = eventType;
		this.event_payload = eventPayload;
	}
	
	private EventType event_type;
	
	private HashMap<String,Object> event_payload;

	public EventType getEvent_type() {
		return event_type;
	}

	public void setEvent_type(EventType event_type) {
		this.event_type = event_type;
	}

	public HashMap<String, Object> getEvent_payload() {
		return event_payload;
	}

	public void setEvent_payload(HashMap<String, Object> event_payload) {
		this.event_payload = event_payload;
	}
	
}
