package br.unioeste.cascavel.util.exception;

public class StorageFileNotFoundException extends StorageException {

	/**
	 *
	 */
	private static final long serialVersionUID = 2202797432503802707L;

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
