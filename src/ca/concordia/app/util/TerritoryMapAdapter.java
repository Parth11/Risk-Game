package ca.concordia.app.util;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.internal.LinkedTreeMap;

import ca.concordia.app.model.Country;

/**
 * This class implements the territory Map adapter
 * @author harvi
 *
 */
public class TerritoryMapAdapter implements JsonSerializer<HashMap<Country, ArrayList<String>>>,JsonDeserializer<HashMap<Country, ArrayList<String>>> {

	static final String CLASSNAME = "CLASSNAME";
    static final String DATA = "DATA";
    
    /**
     * This method gives the class Name 
     * @param className the class name
     * @return Class the class
     * @throws JsonParseException message
     */
    public Class getObjectClass(String className) {
        try {
            return Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e.getMessage());
            }
    }
    
	@Override
	/**
	 * This method deserialize
	 * @param json typeOfT context
	 * @throws JsonParseException message
	 */
	public HashMap<Country, ArrayList<String>> deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		
		Gson gson = GsonUtil.getGSONInstance();
		
		JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();
        Class klass = getObjectClass(className);
        HashMap<LinkedTreeMap,ArrayList<String>> map = context.deserialize(jsonObject.get(DATA), klass);
        HashMap<Country, ArrayList<String>> returnMap = new HashMap<>();
        for(LinkedTreeMap k : map.keySet()){
        	ArrayList<String> v = map.get(k);
        	Country c = gson.fromJson(gson.toJsonTree(k).getAsJsonObject(), Country.class);
        	returnMap.put(c, v);
        }
        return returnMap;
	}
	
	/**
	 * This methos serialize JsonElement
	 * @param src typeOfSrc context
	 * @return jsonObject
	 */
	@Override
	public JsonElement serialize(HashMap<Country, ArrayList<String>> src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, src.getClass().getName());
        jsonObject.add(DATA, context.serialize(src));
        return jsonObject;
	}

}
