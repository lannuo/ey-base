package cn.sky999.common.exception;

public class LoginException extends Exception {
	private static final long serialVersionUID = 1L;

	public LoginException() {
	}

	public LoginException(Throwable cause) {
		super(cause);
	}

	public LoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginException(String message) {
		super(message);
	}
}
