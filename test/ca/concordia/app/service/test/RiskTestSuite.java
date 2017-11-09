package ca.concordia.app.service.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.concordia.app.model.test.DiceRollerTest;
import ca.concordia.app.model.test.GameMapTest;
import ca.concordia.app.model.test.PlayerTest;


@RunWith(Suite.class)
@SuiteClasses({ GamePlayServiceTest.class, MapServiceValidationsTest.class, TestCreateServiceMap.class,DiceRollerTest.class,
	GameMapTest.class,PlayerTest.class})
public class RiskTestSuite {

}
