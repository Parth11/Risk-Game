/**
 * 
 */
package ca.concordia.app.strategies;

/**
 * @author harvi
 *
 */
public class StrategyFactory {
	
	public static PlayerStrategy getStrategyByName(String name){
		switch(name){
		case "human": return null;
		case "aggressive": return new AggressiveStrategy();
		case "benevolent": return new BenevolentStrategy();
		case "random": return new RandomStrategy();
		case "cheater": return new CheaterStrategy();
		}
		
		return null;
	}

}
