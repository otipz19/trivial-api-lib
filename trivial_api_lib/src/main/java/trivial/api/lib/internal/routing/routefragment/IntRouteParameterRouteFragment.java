package trivial.api.lib.internal.routing.routefragment;

import trivial.api.lib.internal.routing.routefragment.base.RouteParameterRouteFragment;

public class IntRouteParameterRouteFragment extends RouteParameterRouteFragment {
    public IntRouteParameterRouteFragment(String fragment, String routeParameterName) {
        super(fragment, routeParameterName);
    }

    @Override
    public boolean matches(String fragment) {
        try {
            Integer.parseInt(fragment);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
