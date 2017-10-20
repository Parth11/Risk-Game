package ca.concordia.app.test.service;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.app.model.GameMap;
import ca.concordia.app.service.MapService;
import ca.concordia.app.util.MapValidationException;


public class TestCreateServiceMap extends MapService {
	List<String> lines = new ArrayList<String>();

	private GameMap game_map;
	
	@Before
	public void init() {
		game_map  = GameMap.getInstance();

		
	}
	@Test
	public void removeCountryFromMap() throws MapValidationException, URISyntaxException
	{
	
		game_map = loadMap("maps/Europe.map");
		removeCountryFromMap("Ireland");
		assertEquals(null,game_map.getCountryByName("Ireland"));
        
	}

	@Test(expected=MapValidationException.class)
	public void traversableTest() throws MapValidationException, URISyntaxException{
		game_map = loadMap("maps/a.map");
		try {
			isTraversable();
		} catch (MapValidationException e) {
			System.err.println(this.getClass().getName()+" : "+e.getMessage());
			throw e;
		}
	}

}