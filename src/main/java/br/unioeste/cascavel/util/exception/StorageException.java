package br.unioeste.cascavel.util.exception;

public class StorageException extends RuntimeException {


	/**
	 *
	 */
	private static final long serialVersionUID = 9057270708903289560L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
