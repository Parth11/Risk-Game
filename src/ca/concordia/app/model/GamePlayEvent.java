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
		START_COUNTRY,
		START_ARMY,
		REFINFORCE_COUNTRY,
		ATTACK_COUNTRY,
		FORTIFY_COUNTRY;
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
