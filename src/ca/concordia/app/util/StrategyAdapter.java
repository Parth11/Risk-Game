/**
 * 
 */
package ca.concordia.app.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import ca.concordia.app.strategies.PlayerStrategy;


/**
 * This class implements strategy Adapter
 * @author harvi
 *
 */
public class StrategyAdapter implements JsonSerializer<PlayerStrategy>,JsonDeserializer<PlayerStrategy> {
	
	static final String CLASSNAME = "CLASSNAME";
    static final String DATA = "DATA";

	@Override
	/**
	 * This method serialize JsonElement
	 * @param src typeOfSrc context
	 * @return jsonObject
	 */
	public JsonElement serialize(PlayerStrategy src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, src.getClass().getName());
        jsonObject.add(DATA, context.serialize(src));
        return jsonObject;
	}

	@Override
	/**
	 * This method deserialize the player strategy
	 * @param json typeOfT context
	 * @return context
	 */
	public PlayerStrategy deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();
        Class klass = getObjectClass(className);
        return context.deserialize(jsonObject.get(DATA), klass);
	}
	
	/**
	 * get className 
	 * @param className the class name
	 * @return className
	 */
	public Class getObjectClass(String className) {
         try {
             return Class.forName(className);
             } catch (ClassNotFoundException e) {
                 throw new JsonParseException(e.getMessage());
             }
    }
}
