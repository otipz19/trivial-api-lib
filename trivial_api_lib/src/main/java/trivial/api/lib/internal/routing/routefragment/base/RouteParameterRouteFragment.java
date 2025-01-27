package trivial.api.lib.internal.routing.routefragment.base;

public abstract class RouteParameterRouteFragment extends RouteFragment {
    protected final String routeParameterName;

    public RouteParameterRouteFragment(String fragment, String routeParameterName) {
        super(fragment);
        this.routeParameterName = routeParameterName;
    }
}
