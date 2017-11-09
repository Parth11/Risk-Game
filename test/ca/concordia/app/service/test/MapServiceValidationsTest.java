/**
 * 
 */
package ca.concordia.app.service.test;

import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.app.service.MapService;
import ca.concordia.app.util.MapValidationException;

/**
 * This class will test and validate the Map Service.
 *
 * @author harvi
 */
public class MapServiceValidationsTest extends MapService {

	/**
	 * Test method for
	 * {@link ca.concordia.app.service.MapService#loadMap(java.lang.String)}.
	 *
	 * @throws MapValidationException the map validation exception
	 * @throws URISyntaxException the URI syntax exception
	 */

	@Test(expected = MapValidationException.class)
	public void testDisconnectedMap() throws MapValidationException, URISyntaxException {

		try {
			MapService.getInstance().loadMap("maps/a.map");
		} catch (MapValidationException e) {
			System.out.println(this.getClass().getName() + " : " + e.getMessage());
			throw e;
		}

	}

	/**
	 * Test connected map.
	 *
	 * @throws MapValidationException the map validation exception
	 * @throws URISyntaxException the URI syntax exception
	 */
	@Test
	public void testConnectedMap() throws MapValidationException, URISyntaxException {

		MapService.getInstance().loadMap("maps/Europe.map");

	}

	/**
	 * Test connected map 1.
	 *
	 * @throws MapValidationException the map validation exception
	 * @throws URISyntaxException the URI syntax exception
	 */
	@Test(expected = MapValidationException.class)
	public void testConnectedMap1() throws MapValidationException, URISyntaxException {
		try {
			MapService.getInstance().loadMap("maps/Montreal.map");
		} catch (MapValidationException e) {
			System.out.println(this.getClass().getName() + " : " + e.getMessage());
			throw e;
		}

	}

	/**
	 * Test connected map 2.
	 *
	 * @throws MapValidationException the map validation exception
	 * @throws URISyntaxException the URI syntax exception
	 */
	@Test
	public void testConnectedMap2() throws MapValidationException, URISyntaxException {

		MapService.getInstance().loadMap("maps/USA.map");

	}

	/**
	 * Test connected map 3.
	 *
	 * @throws MapValidationException the map validation exception
	 * @throws URISyntaxException the URI syntax exception
	 */
	@Test(expected = MapValidationException.class)
	public void testConnectedMap3() throws MapValidationException, URISyntaxException {
		try {
			MapService.getInstance().loadMap("maps/USA2.map");
		} catch (MapValidationException e) {
			System.out.println(this.getClass().getName() + " : " + e.getMessage());
			throw e;
		}
	}

}
