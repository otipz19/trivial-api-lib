package trivial.api.lib.exposed.exceptions;

public class InvalidRouteException extends RuntimeException {
    public InvalidRouteException(String route) {
        super("Invalid route " + route);
    }
}
