package dumitru.adrian.myreservationapp.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    private AuthoritiesConstants() {
    }

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String CLIENT = "ROLE_CLIENT";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String OWNER = "ROLE_OWNER";
}
