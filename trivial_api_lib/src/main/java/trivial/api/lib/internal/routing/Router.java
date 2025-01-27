package trivial.api.lib.internal.routing;

import trivial.api.lib.exposed.BaseController;
import trivial.api.lib.exposed.exceptions.InvalidRouteException;
import trivial.api.lib.internal.controller.method.adapters.ControllerMethodAdaptersContainer;
import trivial.api.lib.internal.exceptions.RouteNotMatchedException;
import trivial.api.lib.internal.routing.routefragment.ExactRouteFragment;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Router {
    /*
     * Valid route patterns:
     * /
     * /user
     * /user/list
     * /user/{id:int}
     * /user/{name:string}/info
     */
//    private static final Pattern VALID_ROUTE_REGEX = Pattern.compile("((/[a-z]+)(/\\{[a-z]+:(int|string)\\})?)+|/");
//    private static final Pattern INT_ROUTE_PARAM_REGEX = Pattern.compile("\\{([a-z]+):int\\}");
//    private static final Pattern STRING_ROUTE_PARAM_REGEX = Pattern.compile("\\{([a-z]+):string\\}");

    private static final Pattern VALID_ROUTE_PATTERN_REGEX = Pattern.compile("(/[a-z]+)+|/");

    private final RouteTrieElement routeTrieRoot = new RouteTrieElement();

    public void addRoute(String route, Class<? extends BaseController> controller, Method method) {
        if (!isValidRoutePattern(route)) {
            throw new InvalidRouteException(route);
        }

        if (route.equals("/")) {
            routeTrieRoot.addMatchedMethod(controller, method);
            return;
        }

        List<String> routeFragments = Arrays.stream(route.split("/"))
                .filter(String::isBlank)
                .toList();

        RouteTrieElement curTrieElement = this.routeTrieRoot;
        for (var curFragment : routeFragments) {
            if (!curTrieElement.hasChild(curFragment)) {
                curTrieElement.addChild(curFragment);
            }
            curTrieElement = curTrieElement.getChild(curFragment);
        }
        curTrieElement.addMatchedMethod(controller, method);
    }

    public static boolean isValidRoutePattern(String route) {
        return VALID_ROUTE_PATTERN_REGEX.matcher(route).matches();
    }

    /*
     * Actual routes:
     * /
     * /user
     * /user/list
     * /user/123
     * /user/abc/info
     */
    public ControllerMethodAdaptersContainer matchRoute(String route) throws RouteNotMatchedException {
        List<String> routeParts = Arrays.stream(route.split("/"))
                .filter(String::isBlank)
                .toList();

        if (routeParts.isEmpty()) {
            return routeTrieRoot.getMatchedMethods();
        }

        RouteTrieElement curTrieElement = routeTrieRoot;
        for (var curPart : routeParts) {
            if (!curTrieElement.hasChild(curPart)) {
                throw new RouteNotMatchedException(route);
            }
            curTrieElement = curTrieElement.getChild(curPart);
        }

        if (!curTrieElement.isEnding()) {
            throw new RouteNotMatchedException(route);
        }

        return curTrieElement.getMatchedMethods();
    }
}
