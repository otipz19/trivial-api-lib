package trivial.api.lib.internal.routing;

import trivial.api.lib.exposed.BaseController;
import trivial.api.lib.internal.controller.method.adapters.ControllerMethodAdaptersContainer;
import trivial.api.lib.internal.exceptions.RouteNotMatchedException;

import java.lang.reflect.Method;
import java.util.HashMap;

public class RoutersCollection {
    public static final String SERVLET_CONTEXT_KEY = "__routers__collection__";

    private final HashMap<HttpMethodType, Router> routersMap = new HashMap<>();

    public RoutersCollection() {
        routersMap.put(HttpMethodType.GET, new Router());
        routersMap.put(HttpMethodType.POST, new Router());
        routersMap.put(HttpMethodType.PUT, new Router());
        routersMap.put(HttpMethodType.DELETE, new Router());
    }

    public void addRoute(HttpMethodType httpMethodType, String route, Class<? extends BaseController> controller, Method method) {
        routersMap.get(httpMethodType).addRoute(route, controller, method);
    }

    public Router getRouter(HttpMethodType httpMethodType) {
        return routersMap.get(httpMethodType);
    }

    public ControllerMethodAdaptersContainer matchRoute(HttpMethodType httpMethodType, String route) throws RouteNotMatchedException {
        return routersMap.get(httpMethodType).matchRoute(route);
    }
}
