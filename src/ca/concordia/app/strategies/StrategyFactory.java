/**
 * 
 */
package ca.concordia.app.strategies;

/**
 * This class implements the factory pattern.
 * @author harvi
 *
 */
public class StrategyFactory {
	
	/**
	 * 
	 * @param name the name of the startegy
	 * @return the strategy
	 */
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
	
	/**
	 * enum implementation
	 * @author AbhinavSingh
	 *
	 */
	public enum Strategy {
	    HUMAN ("Human"),
	    AGGRESSIVE ("Aggressive"),
	    BENEVOLENT ("Benevolent"),
	    RANDOM ("Random"),
	    CHEATER ("Cheater");

	    private String name;       
	    
	    /**
	     * passing strategy
	     * @param s the strategy
	     */
	    Strategy(String s) {
	        name = s;
	    }
	    
	    /**
	     * 
	     * @param otherName the other strategy 
	     * @return true  or false
	     */
	    public boolean equalsName(String otherName) {
	        return name.equals(otherName);
	    }
	    
	    /**
	     * gives the name in string format
	     * @return the name 
	     */
	    public String toString() {
	       return this.name;
	    }
	}
}
