package net.psybit.exception;

/**
 * @author n3k0
 *
 */
public class MotherFactoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MotherFactoryException(final String message) {
		super(message);
	}

	public MotherFactoryException(final Throwable throwable) {
		super(throwable);
	}

	public MotherFactoryException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
