
package ca.concordia.app.util;


/**
 * The Class MapValidationException to handle exception in Map
 *
 * @author harvi
 */
public final class MapValidationException extends Exception {


	private static final long serialVersionUID = 1721693497631317423L;

	/**
	 * Instantiates a new map validation exception.
	 */
	public MapValidationException() {
		super();
	}

	/**
	 * Instantiates a new map validation exception.
	 *
	 * @param message the message
	 */
	public MapValidationException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new map validation exception.
	 *
	 * @param cause the cause
	 */
	public MapValidationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new map validation exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public MapValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new map validation exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public MapValidationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
