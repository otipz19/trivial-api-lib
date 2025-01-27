package trivial.api.lib.internal.routing.routefragment;

import trivial.api.lib.internal.routing.routefragment.base.RouteParameterRouteFragment;

public class StringRouteParameterRouteFragment extends RouteParameterRouteFragment {
    public StringRouteParameterRouteFragment(String fragment, String routeParameterName) {
        super(fragment, routeParameterName);
    }

    @Override
    public boolean matches(String fragment) {
        return true;
    }
}
