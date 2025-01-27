package trivial.api.lib.internal.routing;

import lombok.Getter;
import trivial.api.lib.exposed.BaseController;
import trivial.api.lib.internal.controller.method.adapters.ControllerMethodAdaptersContainer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RouteTrieElement {
    @Getter
    private boolean isEnding;
    @Getter
    private final ControllerMethodAdaptersContainer matchedMethods = new ControllerMethodAdaptersContainer();
    private final Map<String, RouteTrieElement> children = new HashMap<>();

    public void addMatchedMethod(Class<? extends BaseController> controllerClass, Method method) {
        this.isEnding = true;
        this.matchedMethods.addMethod(controllerClass, method);
    }

    public boolean hasChild(String routeFragment) {
        return children.containsKey(routeFragment);
    }

    public RouteTrieElement getChild(String routeFragment) {
        return children.get(routeFragment);
    }

    public void addChild(String routeFragment) {
        this.children.put(routeFragment, new RouteTrieElement());
    }
}
