/**
 * 
 */
package ca.concordia.app.test.service;

import static org.junit.Assert.*;

import java.net.URISyntaxException;

import org.junit.Test;

import ca.concordia.app.service.CreateMapService;
import ca.concordia.app.util.MapValidationException;

/**
 * @author harvi
 *
 */
public class MapServiceTest extends CreateMapService {

	/**
	 * Test method for {@link ca.concordia.app.service.CreateMapService#loadMap(java.lang.String)}.
	 * @throws URISyntaxException 
	 * @throws MapValidationException 
	 */
	@Test(expected=MapValidationException.class)
	public void testLoadMapString() throws MapValidationException, URISyntaxException {
		 
		CreateMapService.getInstance().loadMap("a.map");
		
	}

}
