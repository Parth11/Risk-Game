/**
 * 
 */
package ca.concordia.app.util;

/**
 * @author harvi
 *
 */
public final class MapValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1721693497631317423L;

	/**
	 * 
	 */
	public MapValidationException() {
		super();
	}

	/**
	 * @param message
	 */
	public MapValidationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public MapValidationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MapValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MapValidationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
