package cn.sky999.common.exception;

public class AppException extends Exception {
	private static final long serialVersionUID = 1L;

	public AppException() {
	}

	public AppException(Throwable cause) {
		super(cause);
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}
}
