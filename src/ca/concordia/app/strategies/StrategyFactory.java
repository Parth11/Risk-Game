/**
 * 
 */
package ca.concordia.app.strategies;

/**
 * @author harvi
 *
 */
public class StrategyFactory {
	
	public static PlayerStrategy getStrategyByName(String name) {
		
		switch (name) 
		{
		case "Human":
			return null;
		case "Aggressive":
			return new AggressiveStrategy();
		case "Benevolent":
			return new BenevolentStrategy();
		case "Random":
			return new RandomStrategy();
		case "Cheater":
			return new CheaterStrategy();
		}

		return null;
	}
	

	public enum Strategy {
	    HUMAN ("Human"),
	    AGGRESSIVE ("Aggressive"),
	    BENEVOLENT ("Benevolent"),
	    RANDOM ("Random"),
	    CHEATER ("Cheater");

	    private String name;       

	    private Strategy(String s) {
	        name = s;
	    }

	    public boolean equalsName(String otherName) {
	        return name.equals(otherName);
	    }

	    public String toString() {
	       return this.name;
	    }
	}
}
