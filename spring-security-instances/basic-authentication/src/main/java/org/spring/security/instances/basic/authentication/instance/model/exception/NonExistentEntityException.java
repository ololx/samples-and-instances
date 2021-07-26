package org.spring.security.instances.basic.authentication.instance.model.exception;

/**
 * The type Non existent entity exception.
 *
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:24 <p>
 */
public class NonExistentEntityException extends IllegalArgumentException {

    /**
     * Constructs an {@code EntityIsNotExistsException} with no
     * detail message.
     */
    public NonExistentEntityException() {
        super();
    }

    /**
     * Constructs an {@code EntityIsNotExistsException} with the
     * specified detail message.
     *
     * @param s the detail message.
     */
    public NonExistentEntityException(String s) {
        super(s);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * <p>Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param message the detail message (which is saved for later retrieval         by the {@link Throwable#getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the         {@link Throwable#getCause()} method).  (A {@code null} value         is permitted, and indicates that the cause is nonexistent or         unknown.)
     * @since 1.5
     */
    public NonExistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of {@code (cause==null ? null : cause.toString())} (which
     * typically contains the class and detail message of {@code cause}).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}**).
     *
     * @param cause the cause (which is saved for later retrieval by the         {@link Throwable#getCause()} method).  (A {@code null} value is         permitted, and indicates that the cause is nonexistent or         unknown.)
     * @since 1.5
     */
    public NonExistentEntityException(Throwable cause) {
        super(cause);
    }
}
