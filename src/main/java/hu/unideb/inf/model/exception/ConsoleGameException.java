package hu.unideb.inf.model.exception;

/**
 * Exception thrown to indicate errors related to the console game.
 */
public class ConsoleGameException extends RuntimeException {
  /**
   * Constructs a new runtime exception with {@code null} as its
   * detail message.  The cause is not initialized, and may subsequently be
   * initialized by a call to {@link #initCause}.
   */
  public ConsoleGameException() {
  }

  /**
   * Constructs a new runtime exception with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   */
  public ConsoleGameException(String message) {
    super(message);
  }
}

