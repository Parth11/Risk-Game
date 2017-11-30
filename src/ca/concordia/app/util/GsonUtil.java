package ca.concordia.app.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ca.concordia.app.model.Country;
import ca.concordia.app.model.Player;
import ca.concordia.app.strategies.PlayerStrategy;

public class GsonUtil {
	
	public static Gson getGSONInstance(){
		Type type = new TypeToken<HashMap<Country, ArrayList<String>>>(){}.getType();
		Type type1 = new TypeToken<Map<Player, List<Country>>>(){}.getType();
		return new GsonBuilder().enableComplexMapKeySerialization().
						registerTypeAdapter(PlayerStrategy.class, new StrategyAdapter()).
							registerTypeAdapter(type, new TerritoryMapAdapter()).
								registerTypeAdapter(type1, new PlayerMapAdapter()).create();
	}

}
