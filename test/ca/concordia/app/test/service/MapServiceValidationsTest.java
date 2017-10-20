/**
 * 
 */
package ca.concordia.app.test.service;

import java.net.URISyntaxException;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.app.service.MapService;
import ca.concordia.app.util.MapValidationException;

/**
 * @author harvi
 *
 */
public class MapServiceValidationsTest extends MapService {

	/**
	 * Test method for
	 * {@link ca.concordia.app.service.MapService#loadMap(java.lang.String)}.
	 * 
	 * @throws URISyntaxException
	 * @throws MapValidationException
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

	@Test
	public void testConnectedMap() throws MapValidationException, URISyntaxException {

		MapService.getInstance().loadMap("maps/Europe.map");

	}

	@Test(expected = MapValidationException.class)
	public void testConnectedMap1() throws MapValidationException, URISyntaxException {
		try {
			MapService.getInstance().loadMap("maps/Montreal.map");
		} catch (MapValidationException e) {
			System.out.println(this.getClass().getName() + " : " + e.getMessage());
			throw e;
		}

	}

	@Test
	public void testConnectedMap2() throws MapValidationException, URISyntaxException {

		MapService.getInstance().loadMap("maps/USA.map");

	}

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
