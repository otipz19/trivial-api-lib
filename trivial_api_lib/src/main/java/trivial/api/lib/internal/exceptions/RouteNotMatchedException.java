package trivial.api.lib.internal.exceptions;

public class RouteNotMatchedException extends Exception {
    public RouteNotMatchedException(String route) {
        super("Route not matched: " + route);
    }
}
